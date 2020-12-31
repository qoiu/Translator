package com.qoiu.repository

import com.qoiu.model.SearchResultsDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<SearchResultsDto>>
}
