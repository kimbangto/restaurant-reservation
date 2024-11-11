package com.sesac.restaurant.repository

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Moshi {
    fun dataPath(dataName: String) = System.getProperty("user.dir") + "\\src\\main\\kotlin\\com\\sesac\\restaurant\\data\\${dataName.lowercase()}.json"

    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(LocalDateAdapter()).build()
}

class LocalDateAdapter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @ToJson
    fun toJson(date: LocalDate): String {
        return date.format(formatter)
    }

    @FromJson
    fun fromJson(date: String): LocalDate {
        return LocalDate.parse(date, formatter)
    }
}