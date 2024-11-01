package com.sesac.restaurant.service

import com.sesac.restaurant.model.Guest

class BlackListService {
    private val blackList = mutableMapOf<String, Guest>()

    init {
        blackList["010-1234-2534"] = Guest("햐츄핑", "010-1234-2534", isBlackList = true)
        blackList["010-2374-5353"] = Guest("조아핑", "010-2374-5353", isBlackList = true)
        blackList["010-3572-5235"] = Guest("방글핑", "010-3572-5235")
    }

    /** "블랙리스트를 가져오고 반환해주는 함수" */
    fun getBlackList(): List<Guest> {
        return blackList.values.filter { it.isBlackList }
    }

    /** "제거할 블랙리스트의 인덱스값을 받아와 삭제하고 업데이트해주는 함수" */
    fun updateBlackList(index: Int, status: Boolean): Boolean {
        val blackListGuest = getBlackList()
        return if (index in blackListGuest.indices) {
            val indexGuest = blackListGuest[index]
            blackList[indexGuest.phoneNumber] = indexGuest.copy(isBlackList = status)
            true
        } else {
            false
        }
    }
}