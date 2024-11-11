package controller

import service.SalesManagementService
import java.time.LocalDate

class SalesManagementController {
    private val salesService = SalesManagementService()

    fun startSalesManagement() {
        while (true) {
            println("1. 일별매출 | 2. 주별매출 | 3. 메뉴별매출 | 0. 돌아가기")

            val input = readln()

            when (input) {
                "1" -> {
                    showDailySales()
                }

                "2" -> {
                    showWeeklySales()
                }

                "3" -> {
                    showSalesByMenu()
                }

                "0" -> {
                    return
                }

                else -> {}
            }
        }
    }
    private fun showDailySales() {
        val date = LocalDate.now()
        val dailySales = salesService.getDailySales(date)

        print(salesService.formatDate(date))
        println(" 매출")
        println("$dailySales 원")
    }

    /** 주별 매출 출력 */
    private fun showWeeklySales() {
        val weeklySales = salesService.getWeeklySales()

        println("주별 매출:")
        weeklySales.forEach { (week, sales) ->
            println("$week 매출: $sales 원")
        }
    }

    /** 메뉴별 매출 출력 */
    private fun showSalesByMenu() {
        val salesByMenu = salesService.getSalesByMenu()

        println("메뉴별 매출")
        salesByMenu.forEach { (menuName, totalSales) ->
            println("$menuName = $totalSales 원")
        }
    }
}