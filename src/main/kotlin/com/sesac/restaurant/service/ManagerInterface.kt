package com.sesac.restaurant.service

interface ManagerInterface<T> {
    abstract fun create()
    abstract fun read()
    abstract fun update()
    abstract fun delete()
}