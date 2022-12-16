package day16.volcano

data class Valve(val label: String, val flow: Int) {
    override fun toString(): String = "($label,$flow)"
}
