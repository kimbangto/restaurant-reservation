package com.sesac.restaurant.repository

import java.io.File
import java.io.FileNotFoundException

object FileManager {

    init {
        initPaths()
        createTxtFile()
    }

    private lateinit var basePath: String
    private lateinit var dataPath: String
    private lateinit var dataClassPath: String

    private fun initPaths() {
        basePath = System.getProperty("user.dir") + "\\src\\main\\kotlin\\com\\sesac\\restaurant\\"
        dataPath = basePath + "data\\"
        dataClassPath = basePath + "model\\"
    }

    private fun getDataFilePath(className: String) = "$dataPath$className.txt"

    /** 데이터 클래스마다 txt 파일이 있는지 확인하고 없으면 빈 txt 파일을 새로 생성 */
    private fun createTxtFile() {
        File(dataClassPath).listFiles()!!.forEach {
            val file = File(dataPath + it.name.replace(".kt", ".txt"))
            if (!file.exists()) file.writeText("")
        }
    }

    /** 데이터 클래스의 저장소 텍스트 파일 내용을 읽어옴 */
    fun readFile(classType: String): String {
        try {
            return File(getDataFilePath(classType.replaceFirstChar { it.uppercaseChar() })).readText()
        } catch (e: FileNotFoundException) {
            println("존재하지 않는 데이터 타입입니다.")
            return ""
        }
    }

    /** 데이터 클래스의 저장소 텍스트 파일 덮어쓰기 */
    fun updateFile(classType: String, updateTxt: String) {
        try {
            File(getDataFilePath(classType.replaceFirstChar { it.uppercaseChar() })).writeText(updateTxt)
        } catch (e: FileNotFoundException) {
            println("존재하지 않는 데이터 타입입니다.")
        }
    }
}