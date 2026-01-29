package com.romanpolach.harrypotter.domain.repository

import com.romanpolach.harrypotter.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for character data operations.
 * Follows the repository pattern for clean architecture.
 */
interface CharacterRepository {
    
    /**
     * Get all characters as a Flow.
     * Implements offline-first: emits cached data first, then updates from network.
     */
    fun getCharacters(): Flow<Result<List<Character>>>
    
    /**
     * Get a single character by ID.
     */
    fun getCharacterById(id: String): Flow<Result<Character>>
    
    /**
     * Toggle the favorite status of a character.
     */
    suspend fun toggleFavorite(id: String)
    
    /**
     * Force refresh characters from the network.
     */
    suspend fun refreshCharacters(): Result<Unit>
}
