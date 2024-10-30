package com.sesac.restaurant.model

data class Order(val table: Table, var order: MutableList<Menu>, var amount: Int)