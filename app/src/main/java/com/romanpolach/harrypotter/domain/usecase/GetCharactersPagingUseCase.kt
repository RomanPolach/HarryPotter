package com.romanpolach.harrypotter.domain.usecase

import androidx.paging.PagingData
import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for fetching characters with paging support and optional filtering.
 */
class GetCharactersPagingUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(showOnlyFavorites: Boolean = false): Flow<PagingData<Character>> {
        return repository.getCharactersPaging(showOnlyFavorites)
    }
}
