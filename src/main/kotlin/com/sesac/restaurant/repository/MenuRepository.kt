package repository

import com.squareup.moshi.Types
import model.Menu
import java.io.File

class MenuRepository {
    private val types = Types.newParameterizedType(Map::class.java, String::class.java, Menu::class.java)
    private val adapter = Moshi.moshi.adapter<MutableMap<String, Menu>>(types).indent("  ")
    private val file = File(Moshi.dataPath("menu"))

    fun getMenuMap() = adapter.fromJson(file.readText()) ?: mutableMapOf<String, Menu>()
    private fun overwriteMenuMap(menuMap: MutableMap<String, Menu>) = file.writeText(adapter.toJson(menuMap))

    /** 이름으로 메뉴 찾기 */
    fun findMenuByName(name: String) = getMenuMap()[name]

    /** 메뉴 추가 */
    fun saveMenu(name: String, price: Int) {
        val map = getMenuMap()
        map[name] = Menu(name, price)
        overwriteMenuMap(map)
    }

    /** 메뉴 삭제 */
    fun deleteMenuByName(name: String) {
        val map = getMenuMap()
        map.remove(name)
        overwriteMenuMap(map)
    }
}