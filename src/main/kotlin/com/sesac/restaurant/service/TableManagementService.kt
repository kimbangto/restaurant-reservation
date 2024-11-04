package com.sesac.restaurant.service

import com.sesac.restaurant.model.Table
import com.sesac.restaurant.repository.TableRepository
import java.time.LocalDate

class TableManagementService(private val tableRepository: TableRepository) {
    suspend fun getTableStatusByDate(date: LocalDate): Map<Int, Table> {
        return tableRepository.getTableListByDate(date)
    }
}