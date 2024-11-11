package controller

import service.OrderPayManagementService
import service.TableService
import view.TableView
import java.time.LocalDate

class OrderPayManagementController {
    private val orderPayService = OrderPayManagementService()
    private val tableView = TableView()
    private val tableService = TableService()

    fun startOderPay() {
        while (true) {
            println("1. 주문하기 | 2. 결제하기 | 0. 돌아가기")

            val input = readln()

            when (input) {
                "1" -> {
                    orderTable()
                }

                "2" -> {
                    payTable()
                }

                "0" -> {
                    return
                }

                else -> {}
            }
        }
    }

    private fun orderTable() {
        val date = LocalDate.now()
        tableView.showTablesByDate(tableService.getTablesByDate(date), date)

        println("주문할 테이블 번호를 입력하세요.")
        val tableNumber = readln().toInt()

        println("주문할 메뉴와 수량을 입력하세요. (예: 비빔밥-1, 제육볶음-2)")
        val orderInput = readln()
        val orderDetails = parseOrderInput(orderInput)

        if (orderPayService.saveOrder(date, tableNumber, orderDetails)) {
            println("$tableNumber 에 주문이 성공적으로 추가 되었습니다.")
        } else {
            println("주문 추가에 실패했습니다. 다시 시도해주세요.")
        }
    }

    private fun payTable() {
        val date = LocalDate.now()
        tableView.showTablesByDate(tableService.getTablesByDate(date), date)

        println("결제할 테이블 번호를 입력하세요.")
        val tableNumber = readln().toInt()

        val totalPrice = orderPayService.getTotalPrice(date, tableNumber)
        println("총 가격은 $totalPrice 원 입니다.")

        val isSuccess = orderPayService.payOrder(date, tableNumber)
        println("결제중...")

        if (isSuccess) {
            println("$totalPrice 원이 결제되었습니다. 감사합니다.")
        } else {
            println("결제에 실패하였습니다. 다시시도해주세요.")
        }
    }

    private fun parseOrderInput(input: String): Map<String, Int> {
        val orderMap = mutableMapOf<String, Int>()

        input.split(",").forEach { item ->
            val parts = item.split("-")
            if (parts.size == 2) {
                val menuName = parts[0].trim()
                val count = parts[1].trim().toIntOrNull()
                if (count != null) {
                    orderMap[menuName] = count
                } else {
                    println("잘못된 수량 형식입니다: $item")
                }
            } else {
                println("잘못된 주문 형식입니다: $item")
            }
        }
        return orderMap
    }
}