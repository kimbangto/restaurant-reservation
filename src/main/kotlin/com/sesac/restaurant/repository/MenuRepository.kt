package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import model.Menu
import java.io.File
import java.lang.reflect.ParameterizedType

class MenuRepository: RepositoryInterface<String, Menu> {
    override val types: ParameterizedType = Types.newParameterizedType(Map::class.java, String::class.java, Menu::class.java)
    override val adapter: JsonAdapter<MutableMap<String, Menu>> = Moshi.moshi.adapter<MutableMap<String, Menu>>(types).indent("  ")
    override val file = File(Moshi.dataPath("menu"))

    /** 이름으로 메뉴 찾기 */
    fun findMenuByName(name: String) = getMap()[name]

    /** 메뉴 추가 */
    fun saveMenu(name: String, price: Int) {
        val map = getMap()
        map[name] = Menu(name, price)
        overwriteMap(map)
    }

    /** 메뉴 삭제 */
    fun deleteMenuByName(name: String) {
        val map = getMap()
        map.remove(name)
        overwriteMap(map)
    }
}