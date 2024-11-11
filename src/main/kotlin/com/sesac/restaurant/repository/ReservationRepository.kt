package com.sesac.restaurant.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import model.Reservation
import java.io.File
import java.lang.reflect.ParameterizedType
import java.time.LocalDate

class ReservationRepository: RepositoryInterface<Int, Reservation> {
    override val types: ParameterizedType = Types.newParameterizedType(Map::class.java, Integer::class.java, Reservation::class.java)
    override val adapter: JsonAdapter<MutableMap<Int, Reservation>> = Moshi.moshi.adapter<MutableMap<Int, Reservation>>(types).indent("  ")
    override val file = File(Moshi.dataPath("reservation"))

    /** 예약번호로 예약 반환 */
    fun findReservationByReservationNumber(reservationNumber: Int) = getMap()[reservationNumber]

    /** 예약 저장 */
    fun saveReservation(reservation: Reservation) {
        val map = getMap()
        map[reservation.reservationNumber] = reservation
        overwriteMap(map)
    }

    /** 예약 취소 */
    fun deleteByReservationNumber(reservationNumber: Int) {
        val map = getMap()
        map.remove(reservationNumber)
        overwriteMap(map)
    }

    /** 예약 변경 */
    fun updateByReservationNumber(reservationNumber: Int, date: LocalDate, numberOfPeople: Int) {
        val map = getMap()
        map[reservationNumber]?.visitDate = date
        map[reservationNumber]?.numberOfPeople = numberOfPeople
        overwriteMap(map)
    }
}