package utils

import Input

fun split(input: Input, test: (String) -> Boolean): Sequence<List<String>> = sequence {
    var buffer: MutableList<String> = mutableListOf()
    input.lines().forEach { line ->
        if (test(line)) {
            yield(buffer)
            buffer = mutableListOf()
        } else buffer.add(line)
    }
    if (buffer.isNotEmpty()) yield(buffer)
}

fun split(input: Input) = split(input, String::isBlank)
