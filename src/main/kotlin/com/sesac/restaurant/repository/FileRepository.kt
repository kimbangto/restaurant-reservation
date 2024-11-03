package com.sesac.restaurant.repository

import com.sesac.restaurant.data.FileIO

interface FileRepository<K, V> {
    val className: String
    val fileIO: FileIO

    suspend fun fileOverwrite(overwrite: (MutableMap<K, V>) -> Unit, mapParser: (MutableMap<K, V>) -> String) {
        val list = getMap()
        overwrite(list)
        val listString = mapParser(list)
        fileIO.updateFile(className, listString)
    }

    suspend fun getMap(): MutableMap<K, V>
}