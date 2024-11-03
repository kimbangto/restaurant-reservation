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

    override suspend fun getMap(): MenuMap = fileIO.parser.stringToMenuMap((fileIO.readFile(className)))

    suspend fun saveMenu(menuName: String, price: Int) = fileOverwrite({ list -> list[menuName] = Menu(menuName, price)}, { list -> fileIO.parser.menuMapToString(list)})
}