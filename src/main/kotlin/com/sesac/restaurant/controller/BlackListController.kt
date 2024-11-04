package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.service.BlackListService

class BlackListController(private val blackListService: BlackListService) {
    suspend fun startBlackList() {
        showBlackList()
        println("1. 삭제 | 0. 메인")

        val input = ConsoleInput.consoleLine()
        when (input) {
            "1" -> {
                removeBlackList()
            }
            "0" -> {
                // 메인콘솔 리턴
            }
            else -> {
                // Exception 재귀
            }
        }
    }
    /** "블랙리스트를 블랙리스트서비스를 통해 가져오고 출력해주는 함수" */
    private suspend fun showBlackList() {
        val blackList = blackListService.getBlackList()

        if (blackList.isEmpty()) {
            println("블랙리스트에 등록된 손님이 없습니다.")
        } else {
            println("블랙리스트")
            blackList.forEachIndexed { index, guest ->
                println("${index + 1}. 이름: ${guest.name}, 전화번호: ${guest.phoneNumber}")
            }
        }
    }

    /** "블랙리스트서비스를 통해 삭제하는 함수"*/
    private suspend fun removeBlackList() {
        showBlackList()

        println("블랙리스트 삭제할 손님의 번호를 입력하세요.")

        val indexInput = ConsoleInput.consoleLine().toIntOrNull()

        if (indexInput == null || indexInput <= 0) {
            println("유효한 번호를 입력하세요.")
            return
        }

        val isUpdate = blackListService.updateBlackList(indexInput - 1, false)

        if (isUpdate) {
            println("블랙리스트에서 삭제되었습니다.")
        } else {
            println("잘못된 번호입니다. 다시 입력해주세요.")
            removeBlackList()
        }
        startBlackList()
    }
}