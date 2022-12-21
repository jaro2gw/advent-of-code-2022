package day19.blueprint

import day19.resource.ResourceArray

data class RobotFactory(
    val id: Int,
    val blueprints: ResourceArray<RobotBlueprint>,
)
