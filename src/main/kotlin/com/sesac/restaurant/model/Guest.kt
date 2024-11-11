package model

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Guest(val name: String, val phoneNumber: String, var isVIP: Boolean = false, var isBlackList: Boolean = false)