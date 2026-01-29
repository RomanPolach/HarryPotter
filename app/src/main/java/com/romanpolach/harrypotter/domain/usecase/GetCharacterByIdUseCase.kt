package com.romanpolach.harrypotter.domain.usecase

import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting a single character by ID.
 */
class GetCharacterByIdUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: String): Flow<Result<Character>> {
        return repository.getCharacterById(id)
    }
}
