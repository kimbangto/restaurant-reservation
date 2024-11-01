package com.sesac.restaurant.common

import com.sesac.restaurant.model.*

typealias GuestMap = MutableMap<String, Guest>
typealias MenuMap = MutableMap<String, Menu>
/** 테이블의 주문 */
typealias OrderMap = MutableMap<Int, Order>
/** 주문내용 */
typealias OrderListMap = MutableMap<Menu, Int>
typealias ReservationMap = MutableMap<Long, Reservation>
typealias TableMap = MutableMap<Int, Table>
typealias BlackListMap = MutableMap<String, Guest>