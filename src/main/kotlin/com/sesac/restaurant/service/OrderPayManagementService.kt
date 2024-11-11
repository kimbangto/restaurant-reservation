package service

import com.sesac.restaurant.repository.MenuRepository
import com.sesac.restaurant.repository.PaidTableRepository
import com.sesac.restaurant.repository.TableRepository
import java.time.LocalDate

class OrderPayManagementService {
    private val tableRepository = TableRepository()
    private val menuRepository = MenuRepository()
    private val paidTableRepository = PaidTableRepository()

    fun saveOrder(date: LocalDate, tableNumber: Int, orderDetails: Map<String, Int>): Boolean {
        val menuMap = menuRepository.getMap()
        val table = tableRepository.findByDateAndTableNumber(date, tableNumber)
        val reservation = table.reservation ?: return false
        val tableOrderMap: MutableMap<String, Int> = table.tableOrder?.toMutableMap() ?: mutableMapOf()

        orderDetails.forEach { (menuName, amount) ->
            val menu = menuMap[menuName]
            if (menu != null) {
                tableOrderMap[menu.name] = tableOrderMap.getOrDefault(menu.name, 0) + amount
            } else {
                return false
            }
        }

        table.tableOrder = tableOrderMap
        tableRepository.setReservationAtTable(reservation, tableNumber, tableOrderMap)

        return true
    }

    fun payOrder(date: LocalDate, tableNumber: Int): Boolean {
        val table = tableRepository.findByDateAndTableNumber(date, tableNumber)
        val tableOrder = table.tableOrder

        if (tableOrder.isNullOrEmpty()) {
            return false
        }

        val totalPrice = tableOrder.entries.sumOf { (menuName, amount) ->
            val price = menuRepository.findMenuByName(menuName)?.price ?: 0
            price * amount
        }

        println("총 결제 금액: $totalPrice 원")

        paidTableRepository.setPaidTableForDate(date, tableOrder)

        tableRepository.deleteTableByDateAndTableNumber(date, tableNumber)

        return true
    }

    fun getTotalPrice(date: LocalDate, tableNumber: Int): Int {
        val table = tableRepository.findByDateAndTableNumber(date, tableNumber)
        val tableOrder = table.tableOrder

        val totalPrice = tableOrder?.entries?.sumOf { (menuName, amount) ->
            val price = menuRepository.findMenuByName(menuName)?.price ?: 0
            price * amount
        } ?: 0

        return totalPrice
    }
}