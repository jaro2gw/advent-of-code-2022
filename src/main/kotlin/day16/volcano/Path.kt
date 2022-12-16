package day16.volcano

data class Path(
    val workers: List<Worker>,
    val available: Set<Valve>,
    val total: Int = 0,
)
