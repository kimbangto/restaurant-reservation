package com.sesac.restaurant.service

import com.sesac.restaurant.model.*
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class SalesManagementService {
    private val paidTables: Map<Int, PaidTable>

    init {
        // 더미 데이터 초기화
        val guest1 = Guest("하츄핑", "010-1234-5678")
        val guest2 = Guest("조아핑", "010-8765-4321")
        val guest3 = Guest("방글핑", "010-4241-3723")

        val menu1 = Menu("돈까스", 12000)
        val menu2 = Menu("김치찌개",10000)
        val menu3 = Menu("비빔밥", 9000)

        val reservation1 = Reservation(guest1, LocalDate.now().plusDays(1), 2)
        val reservation2 = Reservation(guest2, LocalDate.now(), 4)
        val reservation3 = Reservation(guest3, LocalDate.now().minusDays(1), 4)

        val table1 = Table(1, 4, reservation1, isPaid = true)
        val table2 = Table(2, 2, reservation2, isPaid = true)
        val table3 = Table(3, 4, reservation3, isPaid = true)

        val order1 = Order(table1, mutableMapOf(menu1 to 2, menu2 to 1))
        val order2 = Order(table2, mutableMapOf(menu1 to 1, menu3 to 2))
        val order3 = Order(table3, mutableMapOf(menu2 to 2, menu3 to 3))

        paidTables = mapOf(
            1 to PaidTable(table1, order1, reservation1),
            2 to PaidTable(table2, order2, reservation2),
            3 to PaidTable(table3, order3, reservation3),
        )
    }

    /**"결제된 테이블을 가져와서 일별 매출 계산 함수"*/
    fun getDailySales(): Map<LocalDate, Int> {
        val todaySales = paidTables.values
            .groupBy { it.reservation.date }
            .mapValues { (_, order) ->
                order.sumOf { it.order.order.entries.sumOf { (menu, amount) -> menu.price * amount } }
            }

        return todaySales
    }

    // Todo 두개의 주가 나오니 주별로 출력되게 새로 map해야할듯
    /**"결제된 테이블을 가져와서 주별 매출 계산 함수"*/
    fun getWeeklySales(): Int {
        val today = LocalDate.now()
        val weekField = WeekFields.of(Locale.getDefault())
        val currentWeek = today.get(weekField.weekOfWeekBasedYear())

        val weeklySales = paidTables.values
            .filter {
                val orderDate = it.reservation.date
                val orderWeek = orderDate.get(weekField.weekOfWeekBasedYear())

                orderWeek == currentWeek
            }
            .sumOf { it.order.order.entries.sumOf { (menu, amount) -> menu.price * amount } }

        return weeklySales
    }

    /**"결제된 테이블을 가져와서 메뉴별 매출 계산 함수"*/
    fun getMenuSales(): Map<Menu, Int> {
        val menuSales = paidTables.values
            .flatMap { it. order.order.entries }
            .groupBy( { it.key }, { it.value * it.key.price } )
            .mapValues { (_, sales) -> sales.sum() }

        return menuSales
    }
}