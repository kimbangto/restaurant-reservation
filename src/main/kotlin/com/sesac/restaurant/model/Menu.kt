package model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Menu(val name: String, var price: Int)