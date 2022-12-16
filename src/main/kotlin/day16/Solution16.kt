package day16;

import Input
import Presenter
import Solution
import day16.volcano.Valve
import day16.volcano.Volcano
import utils.NUMBER_PATTERN

fun main() = Presenter.present(Solution16)

object Solution16 : Solution {
    private const val VALVE_PATTERN = "([A-Z]{2})"
    private const val VALVE_TUNNELS_SEPARATOR = ", "
    private val VALVE_REGEX = Regex(
        "Valve $VALVE_PATTERN has flow rate=$NUMBER_PATTERN; tunnels? leads? to valves? ($VALVE_PATTERN($VALVE_TUNNELS_SEPARATOR$VALVE_PATTERN)*)"
    )

    private fun volcano(input: Input): Volcano {
        val tunnels = mutableMapOf<String, List<String>>()
        val valves = mutableMapOf<String, Valve>()

        input.lines().mapNotNull { VALVE_REGEX.find(it) }
            .map { it.destructured }
            .forEach { (label, flow, labels) ->
                valves[label] = Valve(label = label, flow = flow.toInt())
                tunnels[label] = labels.split(VALVE_TUNNELS_SEPARATOR)
            }

        val map: Map<Valve, List<Valve>> = tunnels.entries
            .associate { (label, labels) ->
                valves[label]!! to labels.map { valves[it]!! }
            }

        return Volcano(
            initial = Valve("AA", 0),
            tunnels = map,
        )
    }

    override fun part1(input: Input): String = volcano(input)
        .pressure(minutes = 30)
        .toString()

    override fun part2(input: Input): String = volcano(input)
        .pressure(minutes = 26, workers = 2)
        .toString()
}
