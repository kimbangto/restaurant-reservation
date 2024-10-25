package com.sesac.restaurant.model

data class Guest(val name: String, val phoneNumber: String, val isVIP: Boolean = false, val isBlackList: Boolean = false) {}