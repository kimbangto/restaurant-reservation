package com.sesac.restaurant.data.txt

import com.sesac.restaurant.common.GuestMap
import com.sesac.restaurant.common.MenuMap
import com.sesac.restaurant.common.ReservationMap
import com.sesac.restaurant.common.TableMap
import com.sesac.restaurant.data.Parser
import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Menu
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.model.Table
import java.time.LocalDate

class TextParser: Parser {

    companion object {
        private var instance: TextParser? = null

        fun getInstance(): TextParser {
            return instance ?: synchronized(this) {
                instance ?: TextParser().also { instance = it }
            }
        }
    }

    private fun objectStringToValueList(objectString: String): List<String> {
        return objectString.split(",").map { it.split("=")[1] }
    }

    /** GuestMap을 문자열로 */
    override fun guestMapToString(guestMap: GuestMap): String {
        var string = ""

        guestMap.entries.forEach{
            val guest = it.value
            string += "name=${guest.name},phoneNumber=${guest.phoneNumber},isVIP=${guest.isVIP},isBlackList=${guest.isBlackList}" + "\n"
        }

        return string.trim()
    }

    /** 문자열을 GuestMap으로 */
    override fun stringToGuestMap(fileOutput: String?): GuestMap {
        val map:GuestMap = mutableMapOf()

        if(fileOutput !== null) {
            fileOutput.split("\n").forEach {
                if(it.isBlank()) return@forEach

                val guestInfoList = objectStringToValueList(it)

                map[guestInfoList[1]] = Guest(guestInfoList[0], guestInfoList[1], guestInfoList[2].toBooleanStrict(), guestInfoList[3].toBooleanStrict())
            }
        }

        return map
    }

    /** ReservationMap을 문자열로 */
    override fun reservationMapToString(reservationMap: ReservationMap): String {
        var string = ""
        reservationMap.entries.forEach {
            val reservation = it.value
            val guest = reservation.guest
            string += "reservationNumber=${it.key},name=${guest.name},phoneNumber=${guest.phoneNumber},isVIP=${guest.isVIP},isBlackList=${guest.isBlackList},date=${reservation.date},numberOfPerson=${reservation.numberOfPerson},isVisit=${reservation.isVisit}" + "\n"
        }

        return string.trim()
    }

    override fun stringToReservationMap(fileOutput: String?): ReservationMap {
        val map: ReservationMap = mutableMapOf()

        if(fileOutput !== null) {
            fileOutput.split("\n").forEach {
                if(it.isBlank()) return@forEach

                val reservationInfoList = objectStringToValueList(it)

                map[reservationInfoList[0].toInt()] = Reservation(Guest(reservationInfoList[1], reservationInfoList[2], reservationInfoList[3].toBooleanStrict(), reservationInfoList[4].toBooleanStrict()), LocalDate.parse(reservationInfoList[5]), reservationInfoList[6].toInt(), reservationInfoList[7].toBooleanStrict())
            }
        }

        return map
    }

    /** MenuMap을 문자열로 */
    override fun menuMapToString(menuMap: MenuMap): String {
        var string = ""
        menuMap.entries.forEach {
            val menu = it.value
            string += "name=${menu.name},price=${menu.price}" + "/n"
        }

        return string.trim()
    }

    /** 문자열을 menuMap으로 */
    override fun stringToMenuMap(fileOutput: String?): MenuMap {
        val map: MenuMap = mutableMapOf()

        if(fileOutput !== null) {
            fileOutput.split("\n").forEach {
                if(it.isBlank()) return@forEach

                val menuInfoMap = objectStringToValueList(it)

                map[menuInfoMap[0]] = Menu(menuInfoMap[0], menuInfoMap[1].toInt())
            }
        }

        return map
    }

    override fun tableMapToString(tableMap: TableMap): String {
        var string = ""
        tableMap.forEach { (date, tables) ->
            string += "date=${date},tables=["
            tables.forEach { (date, table) ->
                val reservation = table.reservation
                val guest = reservation!!.guest
                string += "{tableNumber=${table.tableNumber},numberOfSeats=${table.numberOfSeats},name=${guest.name},phoneNumber=${guest.phoneNumber},isVIP=${guest.isVIP},isBlackList=${guest.isBlackList},date=${reservation.date},numberOfPerson=${reservation.numberOfPerson},isVisit=${reservation.isVisit}},"
            }
            string.dropLast(1).plus("]").plus("\n")
        }
        return string.trim()
    }

    override fun stringToTableMap(fileOutput: String?): TableMap {
        val map: TableMap = mutableMapOf()
        if(fileOutput !== null) {
            fileOutput.split("\n").forEach { eachLine ->
                if(eachLine.isBlank()) return@forEach

                val dateRegex = """date=([\d-]+)""".toRegex()
                val date = dateRegex.find(eachLine)?.groupValues?.get(1)?.let { LocalDate.parse(it) }

                // tables 부분 추출
                val tableListRegex = """tables=\[([^]]*)]""".toRegex()
                val tables = tableListRegex.find(eachLine)?.groupValues?.get(1)

                if (tables != null) {
                    // 각 테이블 정보를 추출
                    val tablePattern = """\{([^}]*)}""".toRegex()
                    val tableListString = tablePattern.findAll(tables).map { it.groupValues[1] }.toList()

                    val tableListMap: MutableMap<Int, Table> = mutableMapOf()

                    tableListString.forEach { tableString ->
                        val tableInfoMap = objectStringToValueList(tableString)
                        tableListMap[tableInfoMap[0].toInt()] = (Table(tableInfoMap[0].toInt(), tableInfoMap[1].toInt(), Reservation(Guest(tableInfoMap[2], tableInfoMap[3], tableInfoMap[4].toBooleanStrict(), tableInfoMap[5].toBooleanStrict()), date!!, tableInfoMap[7].toInt(), tableInfoMap[8].toBooleanStrict())))
                    }

                    map[date!!] = tableListMap
                }
            }
        }
        return map
    }
}