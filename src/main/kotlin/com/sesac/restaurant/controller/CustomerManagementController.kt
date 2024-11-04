package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.repository.GuestRepository
import com.sesac.restaurant.service.BlackListService
import com.sesac.restaurant.service.VipListService

class CustomerManagementController() {
    private val blackListController = BlackListController()
    private val vipListService = VipListService()

    // 이 타이밍에 비동기적으로 데이터를 불러와 놔야하는 것인가 블랙리스트, VIP
    suspend fun startCustomerManagement() {
        println("1. 블랙리스트 | 2. VIP 리스트")
        val input = ConsoleInput.consoleLine()
        when (input) {
            "1" -> {
                blackListController.startBlackList()
            }
            "2" -> {
                showVipList()
            }
            "0" -> {
                // Todo 메인화면이동
            }
            else -> {
                // Todo Exception 후 재귀 or 앱종료
            }
        }
    }

    /** "VIP 리스트를 가져오고 보여주는 함수" */
    private suspend fun showVipList() {
        val vipList = vipListService.getVipList()

        if (vipList.isEmpty()) {
            println("VIP에 등록된 손님이 없습니다.")
        } else {
            println("VIP 리스트")
            vipList.forEachIndexed { index, guest ->
                println("${index + 1}. 이름: ${guest.name}, 전화번호: ${guest.phoneNumber}")
            }
        }

        return startCustomerManagement()
    }
}