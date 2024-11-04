package com.sesac.restaurant.controller

import com.sesac.restaurant.common.*
import com.sesac.restaurant.service.TableManagementService
import java.time.LocalDate

class TableManagementController(private val tableService: TableManagementService) {

    // 고정된 4x3 레이아웃 배열 정의
    private val layout = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(null, null, null, 5),
        arrayOf(6, 7, null, 8)
    )

    // 특정 날짜의 테이블 상태를 출력하는 함수
    suspend fun showTablesByDate(date: LocalDate) {
        val tables = tableService.getTableStatusByDate(date)
        println("날짜: $date 테이블 상태")

        for (row in layout) {
            for (tableNumber in row) {
                if (tableNumber == null) {
                    print("  ")  // 빈 자리 (x 위치)
                } else {
                    val table = tables[tableNumber]
                    if (table?.reservation != null) {
                        // 예약된 테이블은 검정색으로 표시
                        print("$BLACK_TEXT■$RESET ")
                    } else {
                        // 예약되지 않은 테이블은 파란색으로 표시
                        print("$CYAN_TEXT■$RESET ")
                    }
                }
            }
            println() // 각 행이 끝나면 줄바꿈
        }
    }
}