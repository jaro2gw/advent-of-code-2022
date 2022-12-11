package utils

import Input
import java.util.LinkedList

fun split(input: Input, test: (String) -> Boolean): Sequence<List<String>> = sequence {
    var buffer = LinkedList<String>()
    input.lines().forEach { line ->
        if (test(line)) {
            yield(buffer)
            buffer = LinkedList()
        } else buffer.add(line)
    }
    if (buffer.isNotEmpty()) yield(buffer)
}

fun split(input: Input) = split(input, String::isBlank)
