package view

import common.*
import model.Table
import java.time.LocalDate

class TableView {
    val select_table_number = "예약할 번호를 입력하세요."
    val not_available_table = "예약 가능한 테이블이 없습니다."
    val table_status = "%s 테이블 상태"

    private fun printTableColorInfo() {
        print("$BLUE_BACKGROUND 2인석 $RESET ")
        print("$YELLOW_BACKGROUND 4인석 $RESET ")
        print("$GREEN_BACKGROUND 8인석 $RESET ")
        print("$BLACK_BACKGROUND$WHITE_TEXT 예약불가 $RESET ")
        println()
    }

    // 테이블 모양 출력
    private fun printTable(table: Table?) {

        fun printColoredTable(backgroundColorCode: String) {
            print(" $backgroundColorCode ${table?.tableNumber} $RESET ")
        }

        if(table?.reservation != null) {
            printColoredTable(BLACK_BACKGROUND)
        } else {
            when (table?.numberOfSeats) {
                // 2인석은 파랑, 4인석은 노랑, 8인석은 초록
                2 -> printColoredTable(BLUE_BACKGROUND)
                4 -> printColoredTable(YELLOW_BACKGROUND)
                8 -> printColoredTable(GREEN_BACKGROUND)
            }
        }
    }

    /** 테이블 출력용 배열
     * null -> 테이블 x 숫자 -> 테이블 번호
     */
    private val tableLayout = arrayOf(
        arrayOf(1, 2, 3, 4),
        arrayOf(null, null, null, 5),
        arrayOf(7, 8, null, 6)
    )

    /** 특정 날짜의 테이블 상태를 출력하는 함수 */
    fun showTablesByDate(tables: Map<Int, Table>, date: LocalDate) {
        printOutput(table_status, date)
        printTableColorInfo()
        println("-------------------------------")

        for (row in tableLayout) {
            for (tableNumber in row) {
                if (tableNumber == null) {
                    print("     ")  // 빈 자리 (x 위치)
                } else {
                    val table = tables[tableNumber] ?: Table(tableNumber, date, null)
                    printTable(table)
                }
            }
            println()
            println()
        }
    }

}