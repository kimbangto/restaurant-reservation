package com.sesac.restaurant.common

import com.sesac.restaurant.model.*

typealias GuestMap = MutableMap<String, Guest>
typealias MenuMap = MutableMap<String, Menu>
typealias OrderMap = MutableMap<Int, Order> // 테이블의 주문
typealias TableOrderMap = MutableMap<Menu, Int> // 주문내용
typealias ReservationMap = MutableMap<Long, Reservation>
typealias TableMap = MutableMap<Int, Table>
typealias BlackListMap = MutableMap<String, Guest>