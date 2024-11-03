package com.sesac.restaurant.data.txt

import com.sesac.restaurant.common.GuestMap
import com.sesac.restaurant.data.Parser
import com.sesac.restaurant.model.Guest

class TextParser: Parser {

    companion object {
        private var instance: TextParser? = null

        fun getInstance(): TextParser {
            return instance ?: synchronized(this) {
                instance ?: TextParser().also { instance = it }
            }
        }
    }

    private fun objectStringToMap(objectString: String): Map<String, String> {
        return objectString.split(",").associate {
            val (key, value) = it.split("=")
            key to value
        }
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
        if(fileOutput != null) {
            fileOutput.split("\n").forEach {
                if(it.isBlank()) return@forEach

                val guestInfoMap = objectStringToMap(it)

                map[guestInfoMap["phoneNumber"]!!] = Guest(guestInfoMap["name"]!!, guestInfoMap["phoneNumber"]!!, guestInfoMap["isVIP"]!!.toBooleanStrict(), guestInfoMap["isBlackList"]!!.toBooleanStrict())
            }
        } else {
            return map
        }

        return map
    }
}