package com.sesac.restaurant.model

data class Guest(val name: String, val phoneNumber: String, var isVIP: Boolean = false, var isBlackList: Boolean = false) {}