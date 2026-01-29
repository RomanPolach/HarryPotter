package com.romanpolach.harrypotter.data.remote

import com.romanpolach.harrypotter.data.remote.dto.CharacterDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * API service for fetching Harry Potter character data.
 */
class HarryPotterApi(
    private val client: HttpClient
) {
    companion object {
        private const val BASE_URL = "https://hp-api.onrender.com/api"
    }
    
    /**
     * Fetch all characters from the API.
     */
    suspend fun getCharacters(): List<CharacterDto> {
        return client.get("$BASE_URL/characters").body()
    }
    
    /**
     * Fetch a single character by ID.
     */
    suspend fun getCharacterById(id: String): CharacterDto? {
        val characters: List<CharacterDto> = client.get("$BASE_URL/character/$id").body()
        return characters.firstOrNull()
    }
}
