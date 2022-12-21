package day19

import Input
import Presenter
import Solution
import day19.blueprint.RobotBlueprint
import day19.blueprint.RobotFactory
import day19.excavation.Excavation
import day19.resource.Resource
import day19.resource.Resource.clay
import day19.resource.Resource.obsidian
import day19.resource.Resource.ore
import day19.resource.ResourceArray
import utils.NUMBER_PATTERN

fun main() = Presenter.present(Solution19)

object Solution19 : Solution {
    private val ROBOT_FACTORY_BLUEPRINT_ID_REGEX = Regex("Blueprint $NUMBER_PATTERN:")
    private val ROBOT_BLUEPRINT_REGEX = Regex(
        "Each (ore|clay|obsidian|geode) robot costs $NUMBER_PATTERN ore( and $NUMBER_PATTERN clay)?( and $NUMBER_PATTERN obsidian)?\\."
    )

    private fun factories(input: Input): Sequence<RobotFactory> = input.lines()
        .asSequence()
        .map { line ->
            val id = ROBOT_FACTORY_BLUEPRINT_ID_REGEX.find(line)!!.groupValues[1].toInt()
            val tmp = ResourceArray.of<RobotBlueprint?> { null }
            ROBOT_BLUEPRINT_REGEX.findAll(line).forEach {
                val resource = Resource.valueOf(it.groupValues[1])
                val required = ResourceArray.of { 0 }
                required[ore] = it.groupValues[2].toInt()
                required[clay] = it.groupValues[4].toIntOrNull() ?: 0
                required[obsidian] = it.groupValues[6].toIntOrNull() ?: 0
                tmp[resource] = RobotBlueprint(required)
            }
            val blueprints = ResourceArray.of { tmp[it]!! }
            RobotFactory(id, blueprints)
        }

    override fun part1(input: Input): String {
        val excavation = Excavation()
        return factories(input)
            .sumOf { it.id * excavation.run(it, 24) }
            .toString()
    }

    override fun part2(input: Input): String {
        val excavation = Excavation()
        return factories(input)
            .take(3)
            .map { excavation.run(it, 32) }
            .reduce(Int::times)
            .toString()
    }
}
