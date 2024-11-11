package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import java.io.File
import java.lang.reflect.ParameterizedType

interface RepositoryInterface <K, V> {
    val types: ParameterizedType
    val adapter: JsonAdapter<MutableMap<K, V>>
    val file: File

    fun getMap() = adapter.fromJson(file.readText()) ?: mutableMapOf<K, V>()
    fun overwriteMap(map: MutableMap<K, V>) = file.writeText(adapter.toJson(map))
}