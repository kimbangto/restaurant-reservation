package com.sesac.restaurant.common

import com.sesac.restaurant.model.*
import java.time.LocalDate

typealias GuestMap = MutableMap<String, Guest>
typealias MenuMap = MutableMap<String, Menu>
typealias TableOrderMap = MutableMap<Menu, Int> // 주문내용(메뉴, 갯수)
typealias ReservationMap = MutableMap<Int, Reservation>
typealias TableMap = MutableMap<LocalDate, Map<Int, Table>> // 날짜별 테이블
typealias BlackListMap = MutableMap<String, Guest>
typealias PaidTableMap = MutableMap<LocalDate, TableOrderMap>