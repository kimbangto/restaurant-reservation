package model

import java.time.LocalDate

data class PaidTable(val date: LocalDate, val tableOrder: Map<String, Int>)