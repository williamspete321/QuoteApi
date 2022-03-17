package com.quotesapi.repository

import com.quotesapi.model.*
import com.quotesapi.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Random

class QuotesRepository : Repository {
    override suspend fun add(quoteValue: String, quoteUrl: String) =
        dbQuery {
            val insertStatement = Quotes.insert {
                it[value] = quoteValue
                it[url] = quoteUrl
            }
            val result = insertStatement.resultedValues?.get(0)
            if (result != null) {
                toQuote(result)
            } else {
                null
            }
        }


    override suspend fun quotes(): List<Quote> = dbQuery {
        Quotes.selectAll().map { toQuote(it) }
    }

    override suspend fun quoteById(id: Int): Quote? = dbQuery {
        Quotes.select { (Quotes.id eq id) }
            .mapNotNull { toQuote(it) }
            .singleOrNull()
    }

    override suspend fun quoteById(id: String): Quote? {
        return quoteById(id.toInt())
    }

    override suspend fun quoteByRandom(): Quote? = dbQuery {
        Quotes.selectAll()
            .orderBy(Random(), SortOrder.ASC)
            .limit(1)
            .mapNotNull { toQuote(it) }
            .singleOrNull()
    }

    private fun toQuote(row: ResultRow): Quote =
        Quote(
            id = row[Quotes.id].value,
            value = row[Quotes.value],
            url = row[Quotes.url]
        )
}