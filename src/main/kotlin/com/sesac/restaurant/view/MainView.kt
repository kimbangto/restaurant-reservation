package com.sesac.restaurant.view

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.controller.CustomerManagementController
import com.sesac.restaurant.controller.MenuManagementController
import com.sesac.restaurant.controller.ReservationController
import com.sesac.restaurant.controller.SalesManagementController

class MainView {

    val reservationController = ReservationController()
    val customerManagementController = CustomerManagementController()
    val salesManagementController = SalesManagementController()
    val menuManagementController = MenuManagementController()

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
            println("1.고객 관리 | 2.매출 관리 | 3.좌석 관리 | 4.메뉴 관리 | 0.종료")
            val input = ConsoleInput.consoleLine()
            when (input) {
                "1" -> customerManagementController.startCustomerManagement() // 고객관리
                "2" -> salesManagementController.startSalesManagement() // 매출관리
                "3" -> 3 // 좌석관리
                "4" -> menuManagementController.startMenuManagement() // 메뉴관리
                "0" -> break
                else -> println("잘못된 입력입니다")
            }
        }
    }

}