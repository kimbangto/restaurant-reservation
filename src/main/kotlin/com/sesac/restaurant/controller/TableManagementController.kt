package com.sesac.restaurant.controller

import com.sesac.restaurant.common.*
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.service.TableManagementService
import java.time.LocalDate

class TableManagementController() {
    private val tableService = TableManagementService()

    // 고정된 4x3 레이아웃 배열 정의
    private val layout = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(null, null, null, 5),
        arrayOf(7, 8, null, 6)
    )

    suspend fun selectTable(date: LocalDate): Int? {
        val availableTables = tableService.getAvailableTables(date)

        if (availableTables.isEmpty()) {
            println("예약 가능한 테이블이 없습니다.")
            return null
        }

        println("예약할 번호를 입력하세요.")
        val input = ConsoleInput.consoleLine().toInt()

        if (availableTables.containsKey(input)) {
            return input
        } else {
            println("테이블번호 ㄴㄴ")
            return null
        }
    }

    suspend fun makeReservationTable(date: LocalDate, tableNumber: Int, reservationInfo: Reservation) {
        val isState = tableService.saveReservationTable(date, tableNumber, reservationInfo)
        if (isState) {
            println("$tableNumber 번 테이블에 예약 되었습니다.")
        } else {
            println("예약에 실패했습니다. 다시 시도해주세요.")
        }
    }

    // 특정 날짜의 테이블 상태를 출력하는 함수
    suspend fun showTablesByDate(date: LocalDate) {
        val tables = tableService.getTableStatusByDate(date)
        println("날짜: $date 테이블 상태")

        for (row in layout) {
            for (tableNumber in row) {
                if (tableNumber == null) {
                    print("    ")  // 빈 자리 (x 위치)
                } else {
                    val table = tables[tableNumber]
                    if (table?.reservation != null) {
                        // 예약된 테이블은 검정색으로 표시
                        print("$BLACK_BACKGROUND$WHITE_TEXT $tableNumber $RESET ")
                    } else {
                        when (table?.numberOfSeats) {
                            2 -> print("$BLUE_BACKGROUND $tableNumber $RESET ")
                            4 -> print("$YELLOW_BACKGROUND $tableNumber $RESET ")
                            8 -> print("$GREEN_BACKGROUND $tableNumber $RESET ")
                        }
                    }
                }
            }
            println() // 각 행이 끝나면 줄바꿈
            println()
        }
    }
}