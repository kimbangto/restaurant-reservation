package com.sesac.restaurant.model

import com.sesac.restaurant.common.TableOrderMap

data class Table(val tableNumber: Int, val numberOfSeats: Int, var reservation: Reservation? = null, var tableOrderMap: TableOrderMap? = null) {}