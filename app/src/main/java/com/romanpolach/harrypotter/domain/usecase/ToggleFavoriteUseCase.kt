package com.romanpolach.harrypotter.domain.usecase

import com.romanpolach.harrypotter.domain.repository.CharacterRepository

/**
 * Use case for toggling a character's favorite status.
 */
class ToggleFavoriteUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: String) {
        repository.toggleFavorite(id)
    }
}
