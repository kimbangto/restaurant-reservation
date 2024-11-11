package service

import model.Order
import model.Reservation
import model.Table
import com.sesac.restaurant.repository.TableRepository
import java.time.LocalDate

class TableService(private val repository: TableRepository = TableRepository()) {

    /** 날짜별 테이블 현황 반환 */
    fun getTablesByDate(date: LocalDate) = repository.getTableMapByDate(date)

    /** 테이블 선택 */
    fun selectTable(date: LocalDate, numberOfPerson: Int, tableNumber: Int): Boolean {
        val table = repository.findByDateAndTableNumber(date, tableNumber)
        if(table?.reservation !== null) {
            println("이미 예약된 테이블입니다. 다시 선택해주세요.")
            return false
        }

        // 예약 인원이 선택한 테이블의 좌석보다 많으면 예약 되지 않게
        if (repository.findByDateAndTableNumber(date, tableNumber).numberOfSeats < numberOfPerson) {
            println("좌석보다 인원이 많습니다. 다시 선택해주세요.")
            return false
        }
        return true
    }

    /** 테이블에 예약 할당 */
    fun makeReservationTable(date: LocalDate, tableNumber: Int, reservationInfo: Reservation, tableOrder: Order?) {
        val isState = repository.setReservationAtTable(reservationInfo, tableNumber, null)
    }

    /** 테이블 상태 출력 */
    fun getTableMapByDate(date: LocalDate): Map<Int, Table> {
        return repository.getTableMapByDate(date)
    }

    /** 예약이 없는 테이블 필터링 */
    private fun getAvailableTables(date: LocalDate): Map<Int, Table> {
        val tables = repository.getTableMapByDate(date)
        val availableTables = tables.filter { it.value.reservation == null }
        return availableTables
    }

    /** 테이블에 할당된 예약 삭제 */
    fun deleteReservationFromTable(reservation: Reservation) {
        repository.deleteReservationFromTable(reservation)
    }
}