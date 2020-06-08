package com.raywenderlich.numberizer.datalayer.service

import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import retrofit2.Response
import retrofit2.http.*

interface NumbersApiService {

    @GET("{number}/trivia")
    suspend fun getNumberFactAsync(@Path("number") number: String): Response<NumberFactResponse>

}