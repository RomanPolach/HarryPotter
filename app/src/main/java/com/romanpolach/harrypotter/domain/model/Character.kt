package com.romanpolach.harrypotter.domain.model

/**
 * Domain model representing a Harry Potter character.
 * This is the core business model used throughout the app.
 */
data class Character(
    val id: String,
    val name: String,
    val actor: String,
    val imageUrl: String,
    val house: String,
    val species: String,
    val isFavorite: Boolean = false
)
