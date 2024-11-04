package com.sesac.restaurant.common

import com.sesac.restaurant.model.*
import java.time.LocalDate

typealias GuestMap = MutableMap<String, Guest>
typealias MenuMap = MutableMap<String, Menu>
typealias OrderMap = MutableMap<Int, Order> // 테이블의 주문
typealias TableOrderMap = MutableMap<Menu, Int> // 주문내용
typealias ReservationMap = MutableMap<Int, Reservation>
typealias TableMap = MutableMap<LocalDate, Map<Int, Table>> // 날짜별 테이블
typealias BlackListMap = MutableMap<String, Guest>