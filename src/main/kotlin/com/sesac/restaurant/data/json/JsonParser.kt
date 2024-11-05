package com.sesac.restaurant.data.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sesac.restaurant.common.*
import com.sesac.restaurant.data.Parser
import java.time.LocalDate

class JsonParser: Parser {

    companion object {
        private var instance: JsonParser? = null
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())  // 커스텀 어댑터 등록
            .create()

        fun getInstance(): JsonParser {
            return instance ?: synchronized(this) {
                instance ?: JsonParser().also { instance = it }
            }
        }
    }


    override fun guestMapToString(guestMap: GuestMap): String  = gson.toJson(guestMap)

    override fun stringToGuestMap(fileOutput: String?): GuestMap {
        val typeToken = object : TypeToken<GuestMap>() {}.type
        return gson.fromJson(fileOutput, typeToken) ?: mutableMapOf()
    }

    override fun reservationMapToString(reservationMap: ReservationMap): String  = gson.toJson(reservationMap)

    override fun stringToReservationMap(fileOutput: String?): ReservationMap {
        val typeToken = object : TypeToken<ReservationMap>() {}.type
        return gson.fromJson(fileOutput, typeToken) ?: mutableMapOf()
    }

    override fun menuMapToString(menuMap: MenuMap): String  = gson.toJson(menuMap)

    override fun stringToMenuMap(fileOutput: String?): MenuMap {
        val typeToken = object : TypeToken<MenuMap>() {}.type
        return gson.fromJson(fileOutput, typeToken) ?: mutableMapOf()
    }

    override fun tableMapToString(tableMap: TableMap): String = gson.toJson(tableMap)

    override fun stringToTableMap(fileOutput: String?): TableMap {
        val typeToken = object : TypeToken<TableMap>() {}.type
        return gson.fromJson(fileOutput, typeToken) ?: mutableMapOf()
    }

    override fun paidTableMapToString(paidTableMap: PaidTableMap): String = gson.toJson(paidTableMap)

    override fun stringToPaidTableMap(fileOutput: String?): PaidTableMap {
        val typeToken = object : TypeToken<PaidTableMap>() {}.type
        return gson.fromJson(fileOutput, typeToken) ?: mutableMapOf()
    }
}