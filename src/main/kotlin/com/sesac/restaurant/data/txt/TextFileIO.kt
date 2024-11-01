package com.sesac.restaurant.data.txt

import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.data.Parser

class TextFileIO(override val fileExtension: String = "txt", override val parser: Parser = TextParser) : FileIO {
    init {
        println("TextFileIOInit")
        createFile()
    }
}
