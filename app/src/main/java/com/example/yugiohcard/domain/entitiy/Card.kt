package com.example.yugiohcard.domain.entitiy


data class Card(
    val attack: Int,
    val attribute: String,
    val imageUrl: String,
    val defence: Int,
    val desc: String,
    val level: Int,
    val name: String,
    val race: String,
)