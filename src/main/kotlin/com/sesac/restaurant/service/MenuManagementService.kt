package com.sesac.restaurant.service

import com.sesac.restaurant.repository.MenuRepository

class MenuManagementService(private val menuRepository: MenuRepository) {

    suspend fun getMenu(): List<Pair<String, Int>> {
        return menuRepository.getMap().map { it.key to it.value.price }
    }

    suspend fun addMenu(name: String, price: Int): Boolean {
        val menuMap = menuRepository.getMap()

        if (menuMap.containsKey(name)) {
            return false
        } else {
            menuRepository.saveMenu(name, price)

            return true
        }
    }

    suspend fun updateMenu(index: Int, newName: String, newPrice: Int): Boolean {
        val menuList = getMenu()

        if (index !in menuList.indices) {
            return false
        } else {
            val oldName = menuList[index].first
            menuRepository.deleteMenu(oldName)
            menuRepository.saveMenu(newName, newPrice)

            return true
        }
    }

    suspend fun deleteMenu(index: Int): Boolean {
        val menuList = getMenu()

        if (index !in menuList.indices) {
            return false
        } else {
            val oldName = menuList[index].first
            menuRepository.deleteMenu(oldName)

            return true
        }
    }
}