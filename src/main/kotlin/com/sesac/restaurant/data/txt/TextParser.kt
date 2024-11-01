package com.sesac.restaurant.data.txt

import com.sesac.restaurant.common.*
import com.sesac.restaurant.data.Parser
import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Menu

object TextParser: Parser {

    /** 개행문자(\n)을 구분자로 사용해
     * key1=value1, key2=value2
     * key3=value3, key4=value4
     * ...
     * 와 같은 형태의 문자열에서 [{key1=value1, key2=value2}, {key3=value3, key5=value4}, ...] 을 반환하는 함수
     * */
    private fun getListFromListString(listString: String) = listString.split("\n")

    /** "key1=value1, key2=value2, key3=value3, ..."와 같은 형태의 문자열에서 [value1, value2, value3, ...] 을 반환하는 함수 */
    private fun getValuesFromString(listString: String): List<String> {
        val list = listString.replace("\n", "").replace("\r", "").split(",")
        if(list[0] === "") return emptyList()
        return list.map { it.split("=")[1] }
    }

    override fun guestMapToString(guestMap: GuestMap): String {
        var string = ""
        guestMap.entries.forEach {
            val guest = it.value
            string += "name:${guest.name},phoneNumber:${guest.phoneNumber},isVIP:${guest.isVIP},isBlackList=${guest.isBlackList}" + "\n"
        }
        return string
    }

    override fun stringToGuestMap(string: String): GuestMap {
        val list = getListFromListString(string)
        val map: GuestMap = mutableMapOf()
        if(list[0] == "") return map
        list.forEach{
            val values = getValuesFromString(it)
            val guest = Guest(values[0], values[1], values[2].toBooleanStrict(), values[3].toBooleanStrict())
            map[guest.phoneNumber] = guest
        }
        return map
    }

    override fun menuMapToString(menuMap: MenuMap): String {
        var string = ""
        menuMap.entries.forEach {
            val menu = it.value
            string += "name:${menu.name},price:${menu.price}}" + "\n"
        }
        return string
    }

    override fun stringToMenuMap(string: String): MenuMap {
        val list = getListFromListString(string)
        val map: MenuMap = mutableMapOf()
        if(list[0] == "") return map
        list.forEach{
            val values = getValuesFromString(it)
            val menu = Menu(values[0], values[1].toInt())
            map[menu.name] = menu
        }
        return map
    }

    override fun orderMapToString(orderMap: OrderMap): String {
        TODO("Not yet implemented")
    }

    override fun stringToOrderMap(string: String): OrderMap {
        TODO("Not yet implemented")
    }

    override fun reservationMapToString(reservationMap: ReservationMap): String {
        TODO("Not yet implemented")
    }

    override fun stringToReservationMap(string: String): ReservationMap {
        TODO("Not yet implemented")
    }

    override fun tableMapToString(tableMap: TableMap): String {
        TODO("Not yet implemented")
    }

    override fun stringToTableMap(string: String): TableMap {
        TODO("Not yet implemented")
    }
}