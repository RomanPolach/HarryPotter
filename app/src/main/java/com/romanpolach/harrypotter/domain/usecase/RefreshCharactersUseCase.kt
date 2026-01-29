package com.romanpolach.harrypotter.domain.usecase

import com.romanpolach.harrypotter.domain.repository.CharacterRepository

/**
 * Use case for refreshing characters from the network.
 */
class RefreshCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshCharacters()
    }
}
