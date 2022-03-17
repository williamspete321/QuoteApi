package com.quotesapi.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*

@Serializable
data class Quote(val id: Int, val value: String, val url: String)

object Quotes : IntIdTable() {
    val value: Column<String> = varchar("value", 1020)
    val url: Column<String> = varchar("url", 1020)
}