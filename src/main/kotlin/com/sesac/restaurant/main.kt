package com.sesac.restaurant

import com.sesac.restaurant.controller.ReservationController
import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.view.MainView

suspend fun main() {
    val textFileIO = TextFileIO.getInstance() // 파일 생성용 임시 코드(추후에 삭제됩니다)

    val mainView = MainView()
    mainView.startMain()
}