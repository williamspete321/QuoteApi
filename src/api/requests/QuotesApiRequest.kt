package com.quotesapi.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class QuotesApiRequest(val value: String, val url: String)