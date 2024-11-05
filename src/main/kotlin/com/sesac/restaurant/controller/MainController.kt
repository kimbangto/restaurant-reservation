package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput

class MainController {

    val reservationController = ReservationController()
    val customerManagementController = CustomerManagementController()
    val salesManagementController = SalesManagementController()
    val menuManagementController = MenuManagementController()
    val orderPayManagementController = OrderPayManagementController()

    /** main 화면 */
    suspend fun startMain() {
        while (true) {
            println("1.예약 관리 | 2.매장 관리 | 0.종료")
            val input = ConsoleInput.consoleLine()
            when (input) {
                "1" -> reservationController.startReservationManager()
                "2" -> startRestaurant() // 매장관리 시작
                "0" -> break
                else -> println("잘못된 입력입니다")
            }
        }
    }

    suspend fun startRestaurant() {
        while (true) {
            println("1.고객 관리 | 2.매출 관리 | 3. 결제 주문 | 4.메뉴 관리 | 0.종료")
            val input = ConsoleInput.consoleLine()
            when (input) {
                "1" -> customerManagementController.startCustomerManagement() // 고객관리
                "2" -> salesManagementController.startSalesManagement() // 매출관리
                "3" -> orderPayManagementController.startOrderPay()
                "4" -> menuManagementController.startMenuManagement() // 메뉴관리
                "0" -> break
                else -> println("잘못된 입력입니다")
            }
        }
    }

}