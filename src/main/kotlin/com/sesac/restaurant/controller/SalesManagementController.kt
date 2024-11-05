package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.service.SalesManagementService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

class SalesManagementController {
    private val salesService = SalesManagementService()

    suspend fun startSalesManagement() {
        println("1. 결제기능 | 2. 일별매출 | 3. 주별매출 | 4. 메뉴별매출")

        val input = ConsoleInput.consoleLine()
        when (input) {
            "1" -> {
                // Todo 결제기능
            }
            "2" -> {
                showDailySales()
            }
            "3" -> {
                showWeeklySales()
            }
            "4" -> {
                showMenuSales()
            }
            else -> {}
        }
    }

    /**"SalesService를 통해 가져온 일별 매출을 출력"*/
    private suspend fun showDailySales() {
        println("일별 매출")
        val dailySales = salesService.getDailySales()
        dailySales.forEach { (date, sales) ->
            println("${date.monthValue}/${date.dayOfMonth}: $sales 원")
        }

        return startSalesManagement()
    }

    /**SalesService를 통해 가져온 주별 매출을 출력*/
    private suspend fun showWeeklySales() {
        println("금주매출")
        val today = LocalDate.now()
        val weekField = WeekFields.of(Locale.getDefault())
        val formatter = DateTimeFormatter.ofPattern("MM/dd")

        val startOfWeek = today.with(weekField.dayOfWeek(), 1).format(formatter)
        val endOfWeek = today.with(weekField.dayOfWeek(), 7).format(formatter)

        val weeklySales = salesService.getWeeklySales()
        println("주별 매출 $startOfWeek ~ $endOfWeek : $weeklySales 원")

        return startSalesManagement()
    }

    /**SalesService를 통해 가져온 메뉴별 매출을 출력*/
    private suspend fun showMenuSales() {
        val menuSales = salesService.getMenuSales()
        println("메뉴별 매출")
        menuSales.forEach { (menu, sales) ->
            println("${menu.name}: $sales 원")
        }

        return startSalesManagement()
    }
}