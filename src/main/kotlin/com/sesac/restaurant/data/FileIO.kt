package com.sesac.restaurant.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

interface FileIO {

    val fileExtension: String
    val parser: Parser

    val basePath: String
        get() = System.getProperty("user.dir") + "\\src\\main\\kotlin\\com\\sesac\\restaurant\\"
    val dbFolderPath: String
        get() = basePath + "data\\$fileExtension\\db\\"
    val modelPath: String
        get() = basePath + "model\\"

    fun getDataFilePath(className: String) = "$dbFolderPath\\$className.$fileExtension"

    // 맨 마지막에 코루틴 추가하기
    /** 데이터 클래스마다 맞는 txt 파일이 db 폴더에 있는지 확인하고 없으면 빈 txt 파일을 새로 생성 */
    fun createFile() {
        File(modelPath).listFiles()!!.forEach {
            val file = File(dbFolderPath + it.name.replace("kt", fileExtension))
            if (!file.exists()) file.writeText("")
        }
    }

    /** 데이터 클래스의 저장소 파일 내용을 읽어옴 */
    suspend fun readFile(classType: String): String? = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            File(getDataFilePath(classType.replaceFirstChar { it.uppercaseChar() })).readText()
        }.onFailure { e -> println(e) }.getOrNull()
    }

    /** 데이터 클래스의 저장소 텍스트 파일 덮어쓰기 */
    suspend fun updateFile(classType: String, updateTxt: String) = withContext(Dispatchers.IO) {
        runCatching {
            File(getDataFilePath(classType.replaceFirstChar { it.uppercaseChar() })).writeText(updateTxt)
        }.onFailure {
            println("존재하지 않는 데이터 타입입니다.")
        }
    }
}