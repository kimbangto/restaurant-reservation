package service

import repository.MenuRepository
import repository.PaidTableRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SalesManagementService {
    private val paidTableRepository = PaidTableRepository()
    private val menuRepository = MenuRepository()

    fun getDailySales(date: LocalDate): Int {
        val paidTables = paidTableRepository.getPaidTableMapByDate(date)
        val totalPrice = paidTables.sumOf { paidTable ->
            paidTable.tableOrder.entries.sumOf { (menuName, amount) ->
                val price = menuRepository.findMenuByName(menuName)?.price ?: 0
                price * amount
            }
        }
        return totalPrice
    }

    fun getWeeklySales(): Map<String, Int> {
        val today = LocalDate.now()
        val startDate = today.minusDays(6) // 오늘 포함 최근 7일

        val salesByWeek = mutableMapOf<String, Int>()

        (0..6).forEach { dayOffset ->
            val date = startDate.plusDays(dayOffset.toLong())
            val dailySales = paidTableRepository.getPaidTableMapByDate(date).sumOf { paidTable ->
                paidTable.tableOrder.entries.sumOf { (menuName, quantity) ->
                    val price = menuRepository.findMenuByName(menuName)?.price ?: 0
                    price * quantity
                }
            }

            val weekLabel = getWeekLabel(date)
            salesByWeek[weekLabel] = salesByWeek.getOrDefault(weekLabel, 0) + dailySales
        }

        return salesByWeek
    }

    fun getSalesByMenu(): Map<String, Int> {
        val salesByMenu = mutableMapOf<String, Int>()

        val paidTableMap = paidTableRepository.getPaidTableMap()

        paidTableMap.values.flatten().forEach { paidTable ->
            paidTable.tableOrder.forEach { (menuName, quantity) ->
                val price = menuRepository.findMenuByName(menuName)?.price ?: 0

                salesByMenu[menuName] = salesByMenu.getOrDefault(menuName, 0) + (price * quantity)
            }
        }

        return salesByMenu
    }

    private fun getWeekLabel(date: LocalDate): String {
        val dayOfWeek = date.dayOfWeek.value
        val startOfWeek = date.minusDays((dayOfWeek - 1).toLong())
        val endOfWeek = startOfWeek.plusDays(6 - dayOfWeek.toLong()).coerceAtMost(LocalDate.now())

        return "${startOfWeek.monthValue}/${startOfWeek.dayOfMonth} ~ ${endOfWeek.monthValue}/${endOfWeek.dayOfMonth}"
    }

    fun formatDate(date: LocalDate): String {
        val dateFormatter = DateTimeFormatter.ofPattern("MM/dd")
        return date.format(dateFormatter)
    }
}