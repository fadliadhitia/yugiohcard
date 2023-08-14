package com.example.yugiohcard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yugiohcard.data.CardRepository
import com.example.yugiohcard.domain.entitiy.Card
import com.example.yugiohcard.module.ApiResult
import com.example.yugiohcard.module.ErrorResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

// 7/14/2022
// 10119907
// T DONI INDRAPRASTA PRADANA
// IF10K
class HomeViewModel : ViewModel() {
    private val cardRepository = CardRepository()

    private val _cardSharedFlow =
        MutableSharedFlow<ApiResult<Collection<Card>, ErrorResponse>>()
    val cardSharedFlow = _cardSharedFlow.asSharedFlow()

    fun fetch() =
        viewModelScope.launch {
            cardRepository.fetch()
                .catch {
                    _cardSharedFlow.emit(
                        ApiResult.Error(
                            ErrorResponse(
                                throwable = it
                            )
                        )
                    )
                }
                .collect {
                    _cardSharedFlow.emit(it)
                }
        }
}