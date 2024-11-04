package com.sesac.restaurant.service

class MenuManagementService {
    private val menuList = mutableMapOf<String, Int>()

    init {
        menuList["돈까스"] = 10000
        menuList["비빔밥"] = 9000
        menuList["김치찌개"] = 9000
        menuList["된장찌개"] = 8000
        menuList["순대국밥"] = 10000
    }

    fun getMenu(): List<Pair<String, Int>> {
        return menuList.toList()
    }

    fun addMenu(name: String, price: Int): Boolean {
        if (menuList.containsKey(name)) {
            return false
        } else {
            menuList[name] = price

            return true
        }
    }

    fun updateMenu(index: Int, newName: String, newPrice: Int): Boolean {
        val indexMenuList = getMenu()

        if (index !in indexMenuList.indices) {
            return false
        } else {
            val oldName = indexMenuList[index].first
            menuList.remove(oldName)
            menuList[newName] = newPrice

            return true
        }
    }

    fun deleteMenu(index: Int): Boolean {
        val indexMenuList = getMenu()

        if (index !in indexMenuList.indices) {
            return false
        } else {
            val oldName = indexMenuList[index].first
            menuList.remove(oldName)

            return true
        }
    }
}