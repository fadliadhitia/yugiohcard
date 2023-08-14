package com.example.yugiohcard.data

import com.example.yugiohcard.data.dao.CardRemoteDataSource
import com.example.yugiohcard.domain.entitiy.Card
import com.example.yugiohcard.domain.dto.CardResponse
import com.example.yugiohcard.module.ErrorResponse
import com.example.yugiohcard.module.FlowApiBuilder
import com.example.yugiohcard.module.Repository
import com.example.yugiohcard.module.RetrofitBuilder


class CardRepository : Repository() {

    private val cardRemoteDataSource = RetrofitBuilder.buildService(CardRemoteDataSource::class.java)

    fun fetch() =
        FlowApiBuilder<Collection<Card>, ErrorResponse, CardResponse>()
            .setApiCall { cardRemoteDataSource.fetch() }
            .setDefaultErrorResponseInstance(ErrorResponse())
            .setManageApiSuccessResponse {
                it.toCards()
            }
            .setManageApiErrorResponse { onApiResponseError(it) }
            .setCoroutineContext(defaultDispatcher).build()
}