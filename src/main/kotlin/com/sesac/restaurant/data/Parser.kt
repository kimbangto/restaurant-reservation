package com.sesac.restaurant.data

import com.sesac.restaurant.common.*

interface Parser {
    fun guestMapToString(guestMap: GuestMap): String
    fun stringToGuestMap(fileOutput: String?): GuestMap
}