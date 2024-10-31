package com.sesac.restaurant.common

import java.util.Scanner

class ConsoleInput {
    companion object {
        private val scanner = Scanner(System.`in`)

        fun consoleLine() = scanner.nextLine()
    }
}