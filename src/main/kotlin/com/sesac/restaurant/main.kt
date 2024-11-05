package com.sesac.restaurant

import com.sesac.restaurant.controller.MainController

suspend fun main() {
//    val textFileIO = TextFileIO.getInstance() // 파일 생성용 임시 코드(추후에 삭제됩니다)

    val mainController = MainController()
    mainController.startMain()
}