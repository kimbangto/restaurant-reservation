package view

import java.time.LocalDate

// input
private fun read() = readln()

fun promptForInput(message: String): String {
    println(message)
    return read()
}

fun printCommonErr() = println("다시 입력해주세요.")

// output
fun printOutput(message: String) = println(String.format(message))
fun printOutput(message: String, value: Int) = println(String.format(message, value))
fun printOutput(message: String, string: String) = println(String.format(message, string))
fun printOutput(message: String, value1: Int, value2: Int) = println(String.format(message, value1, value2))
fun printOutput(message: String, value1: String, value2: Int) = println(String.format(message, value1, value2))
fun printOutput(message: String, value: LocalDate) = println(String.format(message, value))