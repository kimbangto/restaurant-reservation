package com.sesac.restaurant.data

import com.sesac.restaurant.common.*

interface Parser {
    fun guestMapToString(guestMap: GuestMap): String
    fun stringToGuestMap(fileOutput: String?): GuestMap
    fun reservationMapToString(reservationMap: ReservationMap): String
    fun stringToReservationMap(fileOutput: String?): ReservationMap
    fun menuMapToString(menuMap: MenuMap): String
    fun stringToMenuMap(fileOutput: String?): MenuMap
    fun tableMapToString(tableMap: TableMap): String
    fun stringToTableMap(fileOutput: String?): TableMap
    fun paidTableMapToString(paidTableMap: PaidTableMap): String
    fun stringToPaidTableMap(fileOutput: String?): PaidTableMap
}