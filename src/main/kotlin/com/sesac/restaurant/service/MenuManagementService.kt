package com.sesac.restaurant.service

import com.sesac.restaurant.repository.MenuRepository

class MenuManagementService {
    private val menuRepository = MenuRepository()

    fun getMenu(): List<Pair<String, Int>> {
        val menu = menuRepository.getMap().values.sortedBy { it.price }.map { it.name to it.price }
        return menu
    }

    fun addMenu(name: String, price: Int): Boolean {
        if (menuRepository.findMenuByName(name) == null) {
            menuRepository.saveMenu(name, price)
            return true
        } else {
            return false
        }
    }

    fun deleteMenu(index: Int): Boolean {
        val menuList = getMenu()

        if (index !in menuList.indices) {
            return false
        } else {
            val oldName = menuList[index].first
            menuRepository.deleteMenuByName(oldName)
            return true
        }
    }
}