package com.sesac.restaurant.data.json

import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.data.Parser

class JsonFileIO private constructor(override val fileExtension: String = "json", override val parser: Parser = JsonParser.getInstance()) :
    FileIO {
    init {
        createFile()
    }

    companion object {
        private var instance: JsonFileIO? = null

        fun getInstance(): JsonFileIO {
            return instance ?: synchronized(this) {
                instance ?: JsonFileIO().also { instance = it }
            }
        }
    }
}
