package com.sesac.restaurant.service

import com.sesac.restaurant.data.json.JsonFileIO
import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.model.Table
import com.sesac.restaurant.repository.TableRepository
import java.time.LocalDate

class TableManagementService() {
    private val tableRepository = TableRepository.getInstance(JsonFileIO.getInstance())

    suspend fun getTableStatusByDate(date: LocalDate): Map<Int, Table> {
        return tableRepository.getTableListByDate(date)
    }

    suspend fun getAvailableTables(date: LocalDate): Map<Int, Table> {
        val tables = getTableStatusByDate(date)
        val availableTables = tables.filter { it.value.reservation == null }
        return availableTables
    }

    suspend fun saveReservationTable(date: LocalDate, tableNumber: Int, reservationInfo: Reservation): Boolean {
        try {
            tableRepository.reservationTable(date, tableNumber, reservationInfo)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}