package controller

import service.CustomerManagementService

class CustomerManagementController {
    private val customerService = CustomerManagementService()

    fun startCustomerManagement() {
        while (true) {
            println("1. 블랙리스트 | 2. VIP | 0. 돌아가기")

            val input = readln()

            when (input) {
                "1" -> {
                    showBlackList()
                }
                "2" -> {
                    showVipList()
                }
                "0" -> {
                    return
                }
                else -> {}
            }
        }
    }

    private fun showVipList() {
        val vipList = customerService.getVipList()

        if (vipList.isEmpty()) {
            println("VIP에 등록된 게스트가 없습니다.")
        } else {
            println("VIP 목록")
            vipList.forEachIndexed { index, guest ->
                println("${index + 1}. 이름: ${guest.name}, 전화번호: ${guest.phoneNumber}")
            }
        }
    }

    private fun showBlackList() {
        val blackListedGuests = customerService.getBlackList()

        if (blackListedGuests.isEmpty()) {
            println("블랙리스트에 등록된 게스트가 없습니다.")
        } else {
            println("블랙리스트 게스트 목록")
            blackListedGuests.forEachIndexed { index, guest ->
                println("${index + 1}. 이름: ${guest.name}, 전화번호: ${guest.phoneNumber}")
            }
        }

        return choiceBlackList()
    }

    private fun choiceBlackList() {
        println()
        println("삭제할 블랙리스트의 번호를 골라주세요. (돌아가기: 0 )")
        val input = readln().toInt()

        when (input) {
            0 -> {
                return
            }
            else -> {
                deleteBlackList(input - 1)
            }
        }
    }

    private fun deleteBlackList(index: Int) {
        val isSuccess = customerService.deleteBlackList(index)

        if (isSuccess) {
            println("해당 고객은 블랙리스트에서 제거되었습니다.")
        } else {
            println("해당 번호의 블랙리스트가 없습니다. 다시 시도해주세요.")
            return showBlackList()
        }
    }
}