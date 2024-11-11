package repository

import com.squareup.moshi.Types
import model.Reservation
import java.io.File
import java.time.LocalDate

class ReservationRepository {
    private val types = Types.newParameterizedType(Map::class.java, Integer::class.java, Reservation::class.java)
    private val adapter = Moshi.moshi.adapter<MutableMap<Int, Reservation>>(types).indent("  ")
    private val file = File(Moshi.dataPath("reservation"))

    fun getReservationMap() = adapter.fromJson(file.readText()) ?: mutableMapOf<Int, Reservation>()

    private fun overwriteReservationMap(reservationMap: MutableMap<Int, Reservation>) = file.writeText(adapter.toJson(reservationMap))

    /** 예약번호로 예약 반환 */
    fun findReservationByReservationNumber(reservationNumber: Int) = getReservationMap()[reservationNumber]

    /** 예약 저장 */
    fun saveReservation(reservation: Reservation) {
        val map = getReservationMap()
        map[reservation.reservationNumber] = reservation
        overwriteReservationMap(map)
    }

    /** 예약 취소 */
    fun deleteByReservationNumber(reservationNumber: Int) {
        val map = getReservationMap()
        map.remove(reservationNumber)
        overwriteReservationMap(map)
    }

    /** 예약 변경 */
    fun updateByReservationNumber(reservationNumber: Int, date: LocalDate, numberOfPeople: Int) {
        val map = getReservationMap()
        map[reservationNumber]?.visitDate = date
        map[reservationNumber]?.numberOfPeople = numberOfPeople
        overwriteReservationMap(map)
    }
}