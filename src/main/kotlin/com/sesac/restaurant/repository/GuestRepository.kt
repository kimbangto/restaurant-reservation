package com.sesac.restaurant.repository

import com.sesac.restaurant.common.GuestMap
import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.model.Guest

class GuestRepository private constructor(override val fileIO: FileIO, override val className: String = "Guest") : FileRepository<String, Guest> {

    companion object {
        private var instance: GuestRepository? = null

        fun getInstance(fileIO: FileIO): GuestRepository {
            return instance ?: synchronized(this) {
                instance ?: GuestRepository(fileIO).also { instance = it }
            }
        }
    }

    override suspend fun getMap(): GuestMap = fileIO.parser.stringToGuestMap((fileIO.readFile(className)))
    
    suspend fun saveGuest(guest: Guest) =  fileOverwrite({list -> list[guest.phoneNumber] = guest}, { list -> fileIO.parser.guestMapToString(list) })

    suspend fun findByPhoneNumber(phoneNumber: String) = getMap()[phoneNumber]

    suspend fun makeVIPByPhoneNumber(phoneNumber: String) = fileOverwrite({ list -> list[phoneNumber]?.isVIP = true}, { list -> fileIO.parser.guestMapToString(list)})

    suspend fun makeBlackListByPhoneNumber(phoneNumber: String) = fileOverwrite({ list -> list[phoneNumber]?.isBlackList = true}, { list -> fileIO.parser.guestMapToString(list) })

    suspend fun deleteBlackListByPhoneNumber(phoneNumber: String) = fileOverwrite({ list -> list[phoneNumber]?.isBlackList = false}, {list -> fileIO.parser.guestMapToString(list)})

    suspend fun getBlackListMap() = getMap().filter { it.value.isBlackList }

    suspend fun getVIPListMap() = getMap().filter { it.value.isVIP }
}