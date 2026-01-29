package com.romanpolach.harrypotter.domain.usecase

import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for getting all characters sorted by name.
 */
class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<Result<List<Character>>> {
        return repository.getCharacters().map { result ->
            result.map { characters ->
                characters.sortedBy { it.name.lowercase() }
            }
        }
    }
}
