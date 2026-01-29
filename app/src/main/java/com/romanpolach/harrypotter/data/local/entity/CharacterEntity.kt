package com.romanpolach.harrypotter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing character data locally.
 */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val actor: String,
    val imageUrl: String,
    val house: String,
    val species: String,
    val isFavorite: Boolean = false
)
