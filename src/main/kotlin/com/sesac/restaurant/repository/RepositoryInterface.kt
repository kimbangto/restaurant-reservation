package com.sesac.restaurant.repository

interface RepositoryInterface<T> {
    abstract fun create()
    abstract fun read(): T
    abstract fun update()
    abstract fun delete()
}