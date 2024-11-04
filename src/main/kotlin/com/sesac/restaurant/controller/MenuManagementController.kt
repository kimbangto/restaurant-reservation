package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.service.MenuManagementService

class MenuManagementController {
    private val menuService = MenuManagementService()

    fun startMenuManagement() {
        println("1. 메뉴보기 | 2. 메뉴추가 | 3. 메뉴수정 | 4. 메뉴삭제 | 0. 메인콘솔")
        val input = ConsoleInput.consoleLine()

        when (input) {
            "1" -> {
                showMenuList()
                return startMenuManagement()
            }
            "2" -> {
                addMenu()
            }
            "3" -> {
                updateMenu()
            }
            "4" -> {
                deleteMenu()
            }
            "0" -> {}
            else -> {}
        }
    }

    private fun showMenuList() {
        val menuList = menuService.getMenu()

        if (menuList.isEmpty()) {
            println("등록된 메뉴가 없습니다.")
        } else {
            println("메뉴목록")
            menuList.forEachIndexed { index, (name, price) ->
                println("${index + 1}. $name: $price 원")
            }
        }
    }

    private fun addMenu() {
        println("추가할 메뉴 이름을 입력해주세요.")
        val name = ConsoleInput.consoleLine()

        println("메뉴 가격을 입력하세요.")
        val price = ConsoleInput.consoleLine().toInt()
        // Todo 유효성 검사

        if (menuService.addMenu(name, price)) {
            println("메뉴가 추가되었습니다.")
        } else {
            println("이미 존재하는 메뉴입니다.")
        }

        return startMenuManagement()
    }

    private fun updateMenu() {
        showMenuList()

        println("수정할 메뉴의 번호를 입력하세요.")
        val index = ConsoleInput.consoleLine().toInt().minus(1)

        println("수정할 메뉴이름을 입력하세요.")
        val newName = ConsoleInput.consoleLine()

        println("수정할 메뉴가격을 입력하세요.")
        val newPrice = ConsoleInput.consoleLine().toInt()

        if (menuService.updateMenu(index, newName, newPrice)) {
            println("메뉴가 수정되었습니다.")
        } else {
            println("유효하지 않은 번호입니다.")
        }

        return startMenuManagement()
    }

    private fun deleteMenu() {
        showMenuList()
        println("삭제할 메뉴의 번호를 입력하세요.")
        val index = ConsoleInput.consoleLine().toInt().minus(1)

        if (menuService.deleteMenu(index)) {
            println("메뉴가 삭제되었습니다.")
        } else {
            println("유효하지 않은 번호입니다.")
        }

        return startMenuManagement()
    }
}