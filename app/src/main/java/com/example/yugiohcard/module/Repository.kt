package com.example.yugiohcard.module

import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody


abstract class Repository {
    protected val defaultDispatcher = Dispatchers.IO

    protected fun onApiResponseError(responseBody: ResponseBody) =
        GsonMapperHelper.mapToDto(
            responseBody.charStream(),
            ErrorResponse::class.java
        )
}