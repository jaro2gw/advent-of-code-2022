package utils

fun StringBuilder.flush(): String = toString().also { clear() }

fun StringBuilder.number(): Int? = flush().toIntOrNull()
