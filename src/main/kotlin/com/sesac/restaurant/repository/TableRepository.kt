package com.sesac.restaurant.repository

import com.sesac.restaurant.common.TableMap
import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.model.Table
import java.time.LocalDate

class TableRepository private constructor(override val fileIO: FileIO, override val className: String = "Table") : FileRepository<LocalDate, Map<Int, Table>> {

    // key: tableNumber, value: numberOfSeats
    private val initTables = mapOf(
        // 2인석
        1 to Table(1, 2), 2 to Table(2, 2), 3 to Table(3, 2),
        // 4인석
        4 to Table(4, 4), 5 to Table(5, 4), 6 to Table(6, 4),
        // 8인석
        7 to Table(7, 8), 8 to Table(8, 8)
    )

    companion object {
        private var instance: TableRepository? = null

        fun getInstance(fileIO: FileIO): TableRepository {
            return instance ?: synchronized(this) {
                instance ?: TableRepository(fileIO).also { instance = it }
            }
        }
    }

    override suspend fun getMap(): TableMap = fileIO.parser.stringToTableMap(fileIO.readFile(className))

    suspend fun getTableListByDate(date: LocalDate) = getMap()[date] ?: initTables

    suspend fun reservationTable(date: LocalDate, tableNumber: Int, reservationInfo: Reservation) = fileOverwrite({ list ->
        if(list[date] == null) list[date] = initTables
        list[date]?.get(tableNumber)?.reservation = reservationInfo }, { list -> fileIO.parser.tableMapToString(list)})

    suspend fun deleteTableReservation(date: LocalDate, tableNumber: Int) = fileOverwrite({ list -> list[date]?.get(tableNumber)?.reservation = null }, { list -> fileIO.parser.tableMapToString(list)})
}