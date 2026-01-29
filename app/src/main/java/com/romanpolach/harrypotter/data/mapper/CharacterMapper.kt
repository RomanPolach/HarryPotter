package com.romanpolach.harrypotter.data.mapper

import com.romanpolach.harrypotter.data.local.entity.CharacterEntity
import com.romanpolach.harrypotter.data.remote.dto.CharacterDto
import com.romanpolach.harrypotter.domain.model.Character

/**
 * Maps CharacterDto from API to CharacterEntity for local storage.
 * Preserves favorite status if existing entity is provided.
 */
fun CharacterDto.toEntity(existingEntity: CharacterEntity? = null): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        actor = actor,
        imageUrl = image,
        house = house,
        species = species,
        isFavorite = existingEntity?.isFavorite ?: false
    )
}

/**
 * Maps CharacterEntity from local storage to domain Character model.
 */
fun CharacterEntity.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        actor = actor,
        imageUrl = imageUrl,
        house = house,
        species = species,
        isFavorite = isFavorite
    )
}

/**
 * Maps a list of CharacterEntity to domain Character models.
 */
fun List<CharacterEntity>.toDomain(): List<Character> {
    return map { it.toDomain() }
}
