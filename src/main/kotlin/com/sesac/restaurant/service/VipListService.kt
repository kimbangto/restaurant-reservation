package com.sesac.restaurant.service

import com.sesac.restaurant.data.json.JsonFileIO
import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.repository.GuestRepository

class VipListService {
    private val guestRepository = GuestRepository.getInstance(JsonFileIO.getInstance())
    /** "VIP 리스트를 가져오고 반환해주는 함수" */
    suspend fun getVipList(): List<Guest> {
        return guestRepository.getVIPListMap().values.toList()
    }
}