package com.sesac.restaurant.service

import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.model.*
import com.sesac.restaurant.repository.PaidTableRepository
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class SalesManagementService {
    private val paidTableRepository = PaidTableRepository.getInstance(TextFileIO.getInstance())

    /**"결제된 테이블을 가져와서 일별 매출 계산 함수"*/
    suspend fun getDailySales(): Map<LocalDate, Int> {
        val paidTables = paidTableRepository.getMap()
        val todaySales = paidTables.mapValues { (_, tableOrderMap) ->
            tableOrderMap.entries.sumOf { (menu, amount) -> menu.price * amount }
        }

        return todaySales
    }

    // Todo 두개의 주가 나오니 주별로 출력되게 새로 map해야할듯
    /**"결제된 테이블을 가져와서 주별 매출 계산 함수"*/
    suspend fun getWeeklySales(): Int {
        val today = LocalDate.now()
        val weekField = WeekFields.of(Locale.getDefault())
        val currentWeek = today.get(weekField.weekOfWeekBasedYear())
        val paidTables = paidTableRepository.getMap()

        val weeklySales = paidTables.filter { (date, _) ->
            date.get(weekField.weekOfWeekBasedYear()) == currentWeek
        }.values.sumOf { tableOrderMap ->
            tableOrderMap.entries.sumOf { (menu, amount) -> menu.price * amount }
        }

        return weeklySales
    }

    /**"결제된 테이블을 가져와서 메뉴별 매출 계산 함수"*/
    suspend fun getMenuSales(): Map<Menu, Int> {
        val paidTables = paidTableRepository.getMap()
        val menuSales = paidTables.values
            .flatMap { it.entries.map { entry -> entry.key to entry.value } }
            .groupBy { it.first }
            .mapValues { (_, pairs) -> pairs.sumOf { (menu, amount) -> menu.price * amount } }

        return menuSales
    }
}