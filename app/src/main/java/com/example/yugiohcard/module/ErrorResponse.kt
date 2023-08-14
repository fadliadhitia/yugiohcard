package com.example.yugiohcard.module


data class ErrorResponse(
    val code: Int? = null,
    val message: String? = null,
    val throwable: Throwable? = null,
)