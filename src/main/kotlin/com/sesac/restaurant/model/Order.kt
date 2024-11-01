package com.sesac.restaurant.model

data class Order(val table: Table, var order: MutableMap<Menu, Int>)