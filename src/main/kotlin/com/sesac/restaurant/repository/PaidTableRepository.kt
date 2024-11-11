package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import model.PaidTable
import java.io.File
import java.lang.reflect.ParameterizedType
import java.time.LocalDate

class PaidTableRepository: RepositoryInterface<LocalDate, MutableList<PaidTable>> {
    override val types: ParameterizedType = Types.newParameterizedType(MutableMap::class.java, LocalDate::class.java, MutableList::class.java, PaidTable::class.java)
    override val adapter: JsonAdapter<MutableMap<LocalDate, MutableList<PaidTable>>> = Moshi.moshi.adapter<MutableMap<LocalDate, MutableList<PaidTable>>>(types).indent("  ")
    override val file = File(Moshi.dataPath("paid"))

    /** 해당 날짜의 결제된 테이블 목록 반환 */
    fun getPaidTableMapByDate(date: LocalDate): List<PaidTable> {
        return getMap()[date] ?: emptyList()
    }

    /** 특정 날짜에 결제 내역 추가 */
    fun setPaidTableForDate(date: LocalDate, newTableOrder: Map<String, Int>) {
        val map = getMap()
        val paidTableList = map.getOrPut(date) { mutableListOf() }

        if (paidTableList.isNotEmpty()) {
            val existingPaidTable = paidTableList[0]
            val mergedTableOrder = existingPaidTable.tableOrder.toMutableMap()

            newTableOrder.forEach { (menuName, amount) ->
                mergedTableOrder[menuName] = mergedTableOrder.getOrDefault(menuName, 0) + amount
            }

            paidTableList[0] = PaidTable(date, mergedTableOrder)
        } else {
            val paidTable = PaidTable(date, newTableOrder)
            paidTableList.add(paidTable)
        }

        overwriteMap(map)
    }
}