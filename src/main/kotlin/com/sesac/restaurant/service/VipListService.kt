package com.sesac.restaurant.service

import com.sesac.restaurant.model.Guest

class VipListService {
    private val vipList = mutableMapOf<String, Guest>()

    init {
        vipList["010-1234-2534"] = Guest("햐츄핑", "010-1234-2534", isVIP = true)
        vipList["010-2374-5353"] = Guest("조아핑", "010-2374-5353", isVIP = true)
        vipList["010-3572-5235"] = Guest("방글핑", "010-3572-5235")
    }

    /** "VIP 리스트를 가져오고 반환해주는 함수" */
    fun getVipList(): List<Guest> {
        return vipList.values.filter { it.isVIP }
    }
}