package com.sesac.restaurant.repository

import com.sesac.restaurant.common.MenuMap
import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.model.Menu

class MenuRepository private constructor(override val fileIO: FileIO, override val className: String = "Menu") : FileRepository<String, Menu> {

    companion object {
        private var instance: MenuRepository? = null

        fun getInstance(fileIO: FileIO): MenuRepository {
            return instance ?: synchronized(this) {
                instance ?: MenuRepository(fileIO).also { instance = it }
            }
        }
    }

    override suspend fun getMap(): MenuMap {
        val menuMap: MenuMap = mutableMapOf(
            "백반" to Menu("백반", 8000),
            "김치찌개" to Menu("김치찌개", 9000),
            "된장찌개" to Menu("된장찌개", 9000),
            "비빔밥" to Menu("비빔밥", 10000),
            "칼국수" to Menu("칼국수", 10000),
            "오삼불고기" to Menu("오삼불고기", 12000),
            "제육볶음" to Menu("제육볶음", 12000),
            "삼계탕" to Menu("삼계탕", 14000)
        )
        return menuMap
    }

    suspend fun saveMenu(menuName: String, price: Int) = fileOverwrite({ list -> list[menuName] = Menu(menuName, price)}, { list -> fileIO.parser.menuMapToString(list)})

    suspend fun deleteMenu(menuName: String) = fileOverwrite({ list -> list.remove(menuName) }, { list -> fileIO.parser.menuMapToString(list)})
}