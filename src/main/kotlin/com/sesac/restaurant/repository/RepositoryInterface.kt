package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.runBlocking
import java.io.File
import java.lang.reflect.ParameterizedType

interface RepositoryInterface <K, V> {
    val types: ParameterizedType
    val adapter: JsonAdapter<MutableMap<K, V>>
    val file: File

    fun getMap() = runBlocking { adapter.fromJson(file.readText()) ?: mutableMapOf<K, V>() }
    fun overwriteMap(map: MutableMap<K, V>) = runBlocking { file.writeText(adapter.toJson(map)) }
}