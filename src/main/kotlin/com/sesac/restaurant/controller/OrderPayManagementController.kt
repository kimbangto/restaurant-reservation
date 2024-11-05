package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.service.OrderPayManagementService
import java.time.LocalDate

class OrderPayManagementController() {
    private val orderPayService = OrderPayManagementService()
    private val tableController = TableManagementController()

    suspend fun startOrderPay() {
        println("1. 주문하기 | 2. 결제하기 | 0. 메인콘솔")
        val input = ConsoleInput.consoleLine()

        when (input) {
            "1" -> {
                orderTable()
            }
            "2" -> {
                startPayment()
            }
            "0" -> {}
            else -> {}
        }
    }

    private suspend fun orderTable() {
        val date = LocalDate.now()
        tableController.showTablesByDate(date)

        println("주문할 테이블 번호를 입력하세요.")
        val tableNumber = ConsoleInput.consoleLine().toInt()

        println("주문할 메뉴와 수량을 입력하세요. (예: 비빔밥-1, 제육볶음-2)")
        val orderInput = ConsoleInput.consoleLine()
        val orderDetails = parseOrderInput(orderInput)

        if (orderPayService.addOrder(date, tableNumber, orderDetails)) {
            println("$tableNumber 에 주문이 성공적으로 추가 되었습니다.")
        } else {
            println("주문 추가에 실패했습니다. 다시 시도해주세요.")
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

    private suspend fun showOrderTables() {
        val date = LocalDate.now()
        val orderTables = orderPayService.getOrderTables(date)

        if (orderTables.isEmpty()) {
            println("결제 가능한 테이블이 없습니다.")
        }

        println("결제 테이블 목록")
        orderTables.forEach { (tableNumber, tablePrice) ->
            println("테이블번호: $tableNumber, 총 주문금액: $tablePrice 원")
        }
    }

    private suspend fun startPayment() {
        showOrderTables()

        println("결제할 테이블 번호를 입력하세요.")
        val tableNumber = ConsoleInput.consoleLine().toInt()

        val date = LocalDate.now()
        if (orderPayService.processPayment(date, tableNumber)) {
            println("$tableNumber 번 테이블 결제가 완료되었습니다.")
        } else {
            println("결제에 실패하였습니다. 다시 시도해주세요.")
        }
    }

}