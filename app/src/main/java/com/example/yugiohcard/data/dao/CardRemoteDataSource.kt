package com.example.yugiohcard.data.dao

import com.example.yugiohcard.domain.dto.CardResponse
import retrofit2.Response
import retrofit2.http.GET


interface CardRemoteDataSource {
    @GET("/api/v7/cardinfo.php?type=normal%20monster")
    suspend fun fetch(): Response<CardResponse>
}