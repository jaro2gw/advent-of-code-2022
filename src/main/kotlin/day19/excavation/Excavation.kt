package day19.excavation

import day19.blueprint.RobotFactory
import day19.resource.Resource
import day19.resource.Resource.geode
import day19.resource.Resource.ore
import day19.resource.ResourceArray
import kotlin.math.max

class Excavation {
    data class State(
        val pieces: ResourceArray<Int>,
        val robots: ResourceArray<Int>,
    ) {
        companion object {
            fun initial() = State(
                pieces = ResourceArray { 0 },
                robots = ResourceArray { 0 },
            ).apply {
                robots[ore] = 1
            }
        }
    }

    data class StateExtended(
        val state: State,
        val ignored: Set<Resource> = emptySet(),
    )

    private fun buildable(state: State, factory: RobotFactory): Set<Resource> = Resource.values()
        .filter {
            factory.blueprints[it]
                .required
                .all { (resource, required) ->
                    state.pieces[resource] >= required
                }
        }
        .toSet()

    private fun build(state: State, factory: RobotFactory, resource: Resource) = State(
        pieces = ResourceArray { state.pieces[it] - factory.blueprints[resource].required[it] },
        robots = ResourceArray { state.robots[it] },
    ).apply {
        robots[resource] += 1
    }

    private fun next(state: State) = State(
        pieces = ResourceArray { state.robots[it] + state.pieces[it] },
        robots = ResourceArray { state.robots[it] },
    )

    fun run(factory: RobotFactory, minutes: Int): Int {
        val bound = ResourceArray { resource ->
            factory.blueprints.maxOf { (_, blueprint) ->
                blueprint.required[resource]
            }
        }
        bound[geode] = Int.MAX_VALUE

        var currentBestEstimation = 0
        val visited = mutableSetOf(State.initial())
        var states = visited.map { StateExtended(it) }
        repeat(minutes) { minute ->

            states = states.flatMap { (state, ignored) ->
                val next = next(state)
                val buildable = buildable(state, factory)

                val options = if (geode in buildable) listOf(geode) // heuristic: if geode robot can be built, build it.
                else buildable
                    .minus(ignored) // heuristic: do not build resources that could've been built previously but weren't
                    .filter { state.robots[it] < bound[it] } // heuristic: do not build robots if we have enough to satisfy all the requirements

                val remaining = minutes - minute

                // (low-ball) how many geodes can be cracked open until the time runs out
                currentBestEstimation = max(
                    currentBestEstimation,
                    state.pieces[geode] + remaining * state.robots[geode]
                )
                options.map { build(next, factory, it) }
                    .map { StateExtended(it) }
                    .plus(StateExtended(next, buildable))
                    .filter { visited.add(it.state) }
                    .filter { (state) ->
                        // (high-ball) how many geodes cracked open already + how many geodes will also be cracked if we managed to build a geode robot each turn
                        val bestEstimation = state.pieces[geode] + (2 * state.robots[geode] + remaining) * remaining / 2
                        // heuristic: do not consider branches that in the best case scenario will produce a worse outcome than a currently estimated best outcome
                        return@filter bestEstimation >= currentBestEstimation
                    }
            }
        }

        return states.maxOf { it.state.pieces[geode] }
    }
}
