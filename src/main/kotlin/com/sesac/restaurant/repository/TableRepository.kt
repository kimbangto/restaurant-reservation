package com.sesac.restaurant.repository

import com.sesac.restaurant.common.TableMap
import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.model.Table
import java.time.LocalDate

class TableRepository private constructor(override val fileIO: FileIO, override val className: String = "Table") : FileRepository<LocalDate, Map<Int, Table>> {

    // key: tableNumber, value: numberOfSeats
    private val initTables = mapOf(
        // 2인석
        1 to Table(1, 2), 2 to Table(2, 2),
        // 4인석
        3 to Table(3, 4), 4 to Table(4, 4), 5 to Table(5, 4),
        // 8인석
        6 to Table(6, 8), 7 to Table(7, 8)
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

    suspend fun getTableListByDate(localDate: LocalDate) = getMap()[localDate] ?: initTables
}