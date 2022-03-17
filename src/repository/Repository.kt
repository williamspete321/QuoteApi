package com.quotesapi.repository

import com.quotesapi.model.*

interface Repository {
    suspend fun add(value: String, url: String): Quote?
    suspend fun quotes(): List<Quote>
    suspend fun quoteById(id: Int): Quote?
    suspend fun quoteById(id: String): Quote?
    suspend fun quoteByRandom(): Quote?
}