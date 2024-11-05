package com.sesac.restaurant.repository

import com.sesac.restaurant.common.PaidTableMap
import com.sesac.restaurant.common.TableOrderMap
import com.sesac.restaurant.data.FileIO
import java.time.LocalDate

class PaidTableRepository private constructor(override val fileIO: FileIO, override val className: String = "PaidTable") : FileRepository<LocalDate, TableOrderMap> {

    companion object {
        private var instance: PaidTableRepository? = null

        fun getInstance(fileIO: FileIO): PaidTableRepository {
            return instance ?: synchronized(this) {
                instance ?: PaidTableRepository(fileIO).also { instance = it }
            }
        }
    }

    override suspend fun getMap(): PaidTableMap = fileIO.parser.stringToPaidTableMap(fileIO.readFile(className))

    suspend fun savePaidTable(date: LocalDate, tableOrderMap: TableOrderMap) = fileOverwrite({ list ->
        list[date] = tableOrderMap }, { list -> fileIO.parser.paidTableMapToString(list)} )
}