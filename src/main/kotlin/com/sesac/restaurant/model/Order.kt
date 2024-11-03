package com.sesac.restaurant.model

import com.sesac.restaurant.common.TableOrderMap

data class Order(val table: Table, var tableOrder: TableOrderMap) {}