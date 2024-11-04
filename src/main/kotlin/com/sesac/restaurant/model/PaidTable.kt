package com.sesac.restaurant.model

import com.sesac.restaurant.common.TableOrderMap
import java.time.LocalDate

data class PaidTable(val date: LocalDate, val tableOrder: TableOrderMap)