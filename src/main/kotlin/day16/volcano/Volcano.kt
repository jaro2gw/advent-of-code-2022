package day16.volcano

import java.util.LinkedList

class Volcano(
    private val initial: Valve,
    private val tunnels: Map<Valve, List<Valve>>
) {
    private val valves: Set<Valve> = tunnels.keys
    private val openable: Set<Valve> = valves.filter { it.flow > 0 }.toSet()
    private val distance: Map<Pair<Valve, Valve>, Int>

    init {
        val distance: MutableMap<Pair<Valve, Valve>, Int> = mutableMapOf()

        openable.plus(initial).forEach { origin ->
            distance[origin to origin] = 0

            val visited = mutableSetOf(origin)
            val queue = LinkedList(visited)

            while (queue.isNotEmpty()) {
                val source = queue.remove()
                val dist = distance[origin to source]!! + 1

                tunnels[source]?.minus(visited)?.forEach { target ->
                    distance[origin to target] = dist
                    queue += target
                    visited += target
                }
            }
        }

        this.distance = distance.filterKeys { (source, target) -> source != target && target.flow > 0 }
    }

    private fun moves(path: Path, worker: Worker): Sequence<Path> = path.available
        .asSequence()
        .map { target -> target to distance[worker.valve to target]!! }
        .filter { (_, dist) -> worker.remaining > dist }
        .flatMap { (target, dist) ->
            val minutes = worker.remaining - dist - 1
            val next = Path(
                workers = path.workers - worker + Worker(target, minutes),
                available = path.available - target,
                total = path.total + minutes * target.flow
            )
            paths(next)
        }

    private fun paths(path: Path): Sequence<Path> = path.workers
        .maxBy { it.remaining }
        .let { moves(path, it) }
        .ifEmpty { sequenceOf(path) }

    fun pressure(minutes: Int, workers: Int = 1): Int {
        val path = Path(
            workers = List(workers) { Worker(initial, minutes) },
            available = openable,
        )
        return paths(path).maxOf { it.total }
    }
}
