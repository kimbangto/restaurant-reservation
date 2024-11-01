package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput

class CustomerManagementController {
    private val blackListController = BlackListController()

    // 이 타이밍에 비동기적으로 데이터를 불러와 놔야하는 것인가 블랙리스트, VIP
    fun startCustomerManagement() {
        val input = ConsoleInput.consoleLine()
        when (input) {
            "1" -> {
                blackListController.startBlackList()
            }
            "2" -> {
                // TOdo VIP 컨트롤러 연결?
                // Vip 띄어주고 아무키나 누르면 메인가는걸로 간단하게 해둘까?
            }
            "0" -> {
                // Todo 메인화면이동
            }
            else -> {
                // Todo Exception 후 재귀 or 앱종료
            }
        }
    }
}