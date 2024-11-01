package com.sesac.restaurant.data

import com.sesac.restaurant.common.*

interface Parser {
    fun guestMapToString(guestMap: GuestMap): String
    fun stringToGuestMap(string: String): GuestMap

    fun menuMapToString(menuMap: MenuMap): String
    fun stringToMenuMap(string: String): MenuMap

    fun orderMapToString(orderMap: OrderMap): String
    fun stringToOrderMap(string: String): OrderMap

    fun reservationMapToString(reservationMap: ReservationMap): String
    fun stringToReservationMap(string: String): ReservationMap

    fun tableMapToString(tableMap: TableMap): String
    fun stringToTableMap(string: String): TableMap
}