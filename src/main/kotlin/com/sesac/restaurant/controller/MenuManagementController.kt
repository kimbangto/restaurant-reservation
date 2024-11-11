package controller

import com.sesac.restaurant.service.MenuManagementService

class MenuManagementController {
    private val menuService = MenuManagementService()


    fun startMenuManagement() {
        while (true) {
            println("1. 메뉴보기 | 2. 메뉴추가 | 3. 삭제하기 | 0. 돌아가기")

            val input = readln()

            when (input) {
                "1" -> {
                    showAllMenu()
                    startMenuManagement()
                }
                "2" -> {
                    saveMenu()
                }
                "3" -> {
                    deleteMenu()
                }
                "0" -> {
                    return
                }
                else -> {}
            }
        }
    }

    private fun showAllMenu() {
        val menuList = menuService.getMenu()

        menuList.forEachIndexed { index, (name, price) ->
            println("${index + 1}. $name: $price")
        }
    }

    private fun saveMenu() {
        println("메뉴이름")
        val name = readln()

        println("메뉴 가격")
        val price = readln().toInt()

        val isSuccess = menuService.addMenu(name, price)

        if (isSuccess) {
            println("$name 메뉴가 추가됨")
        } else {
            println("실패")
        }
    }

    private fun deleteMenu() {
        showAllMenu()
        println("삭제할 번호를 입력하세요.")

        val input = readln().toInt().minus(1)

        val isSuccess = menuService.deleteMenu(input)

        if (isSuccess) {
            println("메뉴가 삭제되었습니다.")
        } else {
            println("유효하지 않은 번호입니다.")
        }
    }
}