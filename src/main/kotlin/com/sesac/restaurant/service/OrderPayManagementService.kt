package com.sesac.restaurant.service

import com.sesac.restaurant.common.TableOrderMap
import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.repository.MenuRepository
import com.sesac.restaurant.repository.PaidTableRepository
import com.sesac.restaurant.repository.TableRepository
import java.time.LocalDate

class OrderPayManagementService {
    private val tableRepository = TableRepository.getInstance(TextFileIO.getInstance())
    private val menuRepository = MenuRepository.getInstance(TextFileIO.getInstance())
    private val paidRepository = PaidTableRepository.getInstance(TextFileIO.getInstance())

    suspend fun addOrder(date: LocalDate, tableNumber: Int, orderDetails: Map<String, Int>): Boolean {
        val menuMap = menuRepository.getMap()
        val tableOrderMap: TableOrderMap = mutableMapOf()

        orderDetails.forEach {(menuName, amount) ->
            val menu = menuMap[menuName]
            if (menu != null) {
                tableOrderMap[menu] = amount
            } else {
                println("존재하지 않는 메뉴: $menuName")
                return false
            }
        }

        if (tableOrderMap.isNotEmpty()) {
            tableRepository.orderTable(date, tableNumber, tableOrderMap)
            return true
        } else {
            return false
        }
    }

    suspend fun getOrderTables(date: LocalDate): Map<Int, Int> {
        val tables = tableRepository.getTableListByDate(date)
        val orderTables = tables.filter { it.value.tableOrderMap != null }
            .mapValues { (_, table) ->
                table.tableOrderMap!!.entries.sumOf { (menu, amount) -> menu.price * amount}
            }

        return orderTables
    }

    suspend fun processPayment(date: LocalDate, tableNumber: Int): Boolean {
        val tables = tableRepository.getTableListByDate(date)
        val table = tables[tableNumber]

        if (table?.tableOrderMap != null) {
            // 결제할 데이터 준비
            val tableOrderMap = table.tableOrderMap!!

            // 결제 정보 저장
            paidRepository.savePaidTable(date, tableOrderMap)

            // 예약 정보 삭제
            tableRepository.deleteTableReservation(date, tableNumber)
            return true
        } else {
            return false
        }
    }
}