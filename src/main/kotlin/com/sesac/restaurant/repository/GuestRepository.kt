package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import model.Guest
import java.io.File
import java.lang.reflect.ParameterizedType

class GuestRepository: RepositoryInterface<String, Guest> {
    override val types: ParameterizedType = Types.newParameterizedType(Map::class.java, String::class.java, Guest::class.java)
    override val adapter: JsonAdapter<MutableMap<String, Guest>> = Moshi.moshi.adapter<MutableMap<String, Guest>>(types).indent("  ")
    override val file = File(Moshi.dataPath("guest"))

    /** 핸드폰 번호로 예약자 찾기 */
    fun findGuestByPhoneNumber(phoneNumber: String) = getMap()[phoneNumber]

    /** 예약자 정보 저장 */
    fun saveGuest(guest: Guest) {
        val map = getMap()
        map[guest.phoneNumber] = guest
        overwriteMap(map)
    }

    /** 블랙리스트에서 제거 */
    fun removeBlackList(phoneNumber: String) {
        val map = getMap()
        val guest = map[phoneNumber]

        guest?.isBlackList = false
        overwriteMap(map)
    }
}