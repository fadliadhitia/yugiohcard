package com.example.yugiohcard.domain.dto

import com.example.yugiohcard.domain.entitiy.Card


data class CardResponse(
    val data: Collection<Data>
) {
    data class Data(
        val atk: Int,
        val attribute: String,
        val card_images: Collection<CardImage>,
        val def: Int,
        val desc: String,
        val level: Int,
        val name: String,
        val race: String,
    )

    data class CardImage(
        val image_url: String,
    )

    fun toCards(): Collection<Card> =
        data.map {
            Card(
                it.atk,
                it.attribute,
                it.card_images.first().image_url,
                it.def,
                it.desc,
                it.level,
                it.name,
                it.race,
            )
        }
}