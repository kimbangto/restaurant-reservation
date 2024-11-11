package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import model.Reservation
import model.Table
import java.io.File
import java.lang.reflect.ParameterizedType
import java.time.LocalDate

class TableRepository: RepositoryInterface <LocalDate, MutableMap<Int, Table>> {
    override val types: ParameterizedType = Types.newParameterizedType(MutableMap::class.java, LocalDate::class.java, Types.newParameterizedType(MutableMap::class.java, Integer::class.java, Table::class.java))
    override val adapter: JsonAdapter<MutableMap<LocalDate, MutableMap<Int, Table>>> = Moshi.moshi.adapter<MutableMap<LocalDate, MutableMap<Int, Table>>>(types).indent("  ")
    override val file = File(Moshi.dataPath("table"))

    /** 해당 날짜에 예약된 테이블 목록 반환 */
    fun getTableMapByDate(date: LocalDate) = getMap()[date] ?: mutableMapOf<Int, Table>()

    /** 해당 날짜에 해당 테이블이 예약되어있다면 반환*/
    fun findByDateAndTableNumber(date: LocalDate, tableNumber: Int) = getTableMapByDate(date)[tableNumber] ?: Table(tableNumber, date, null)

    /** 테이블에 예약 배정하기 */
    fun setReservationAtTable(reservation: Reservation, tableNumber: Int, tableOrder: MutableMap<String, Int>?) {
        val map = getMap()
        val innerMap = map.getOrPut(reservation.visitDate) { mutableMapOf() }
        innerMap[tableNumber] = Table(tableNumber, reservation.visitDate, reservation, tableOrder)
        overwriteMap(map)
    }
    /** 특정 날짜와 테이블 번호로 테이블 삭제하기 */
    fun deleteTableByDateAndTableNumber(date: LocalDate, tableNumber: Int) {
        val map = getMap()
        val innerMap = map[date]

        innerMap?.remove(tableNumber)
        overwriteMap(map)
    }

    /** 테이블에 예약 삭제하기 */
    fun deleteReservationFromTable(reservation: Reservation) {
        val map = getMap()
        val tableNumber = map[reservation.visitDate]?.values?.find { it.reservation?.reservationNumber == reservation.reservationNumber }?.tableNumber
        map[reservation.visitDate]?.remove(tableNumber)
        overwriteMap(map)
    }
}