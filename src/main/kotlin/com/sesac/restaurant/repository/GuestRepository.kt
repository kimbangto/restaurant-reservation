package repository

import com.squareup.moshi.Types
import model.Guest
import java.io.File

class GuestRepository {
    private val types = Types.newParameterizedType(Map::class.java, String::class.java, Guest::class.java)
    private val adapter = Moshi.moshi.adapter<MutableMap<String, Guest>>(types).indent("  ")
    private val file = File(Moshi.dataPath("guest"))

    fun getGuestMap() = adapter.fromJson(file.readText()) ?: mutableMapOf<String, Guest>()
    private fun overwriteGuestMap(guestMap: MutableMap<String, Guest>) = file.writeText(adapter.toJson(guestMap))

    /** 핸드폰 번호로 예약자 찾기 */
    fun findGuestByPhoneNumber(phoneNumber: String) = getGuestMap()[phoneNumber]

    /** 예약자 정보 저장 */
    fun saveGuest(guest: Guest) {
        val map = getGuestMap()
        map[guest.phoneNumber] = guest
        overwriteGuestMap(map)
    }

    /** 블랙리스트에서 제거 */
    fun removeBlackList(phoneNumber: String) {
        val map = getGuestMap()
        val guest = map[phoneNumber]

        guest?.isBlackList = false
        overwriteGuestMap(map)
    }
}