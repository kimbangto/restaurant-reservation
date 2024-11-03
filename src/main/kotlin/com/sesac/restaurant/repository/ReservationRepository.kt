package com.sesac.restaurant.repository

import com.sesac.restaurant.common.ReservationMap
import com.sesac.restaurant.data.FileIO
import com.sesac.restaurant.model.Reservation
import java.time.LocalDate

class ReservationRepository private constructor(override val fileIO: FileIO, override val className: String = "Reservation") : FileRepository<Int, Reservation> {

    companion object {
        private var instance: ReservationRepository? = null

        fun getInstance(fileIO: FileIO): ReservationRepository {
            return instance ?: synchronized(this) {
                instance ?: ReservationRepository(fileIO).also { instance = it }
            }
        }
    }

    override suspend fun getMap(): ReservationMap = fileIO.parser.stringToReservationMap((fileIO.readFile(className)))

    suspend fun saveReservation(reservationNumber: Int, reservation: Reservation) = fileOverwrite({list -> list[reservationNumber] = reservation}, { list -> fileIO.parser.reservationMapToString(list)})

    suspend fun deleteReservation(reservationNumber: Int) = fileOverwrite({list -> list.remove(reservationNumber)}, {list -> fileIO.parser.reservationMapToString(list)})

    suspend fun updateReservation(reservationNumber: Int, date: LocalDate) =  fileOverwrite({list -> list[reservationNumber]!!.date = date}, { list -> fileIO.parser.reservationMapToString(list)})
}