package com.sesac.restaurant.data.txt

import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.data.Parser

class TextFileIO private constructor(override val fileExtension: String = "txt", override val parser: Parser = TextParser.getInstance()) : FileIO {
    init {
        createFile()
    }

    companion object {
        private var instance: TextFileIO? = null

        fun getInstance(): TextFileIO {
            return instance ?: synchronized(this) {
                instance ?: TextFileIO().also { instance = it }
            }
        }
    }
}
