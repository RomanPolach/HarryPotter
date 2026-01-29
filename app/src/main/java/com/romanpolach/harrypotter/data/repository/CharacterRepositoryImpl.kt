package com.romanpolach.harrypotter.data.repository

import com.romanpolach.harrypotter.data.local.dao.CharacterDao
import com.romanpolach.harrypotter.data.mapper.toDomain
import com.romanpolach.harrypotter.data.mapper.toEntity
import com.romanpolach.harrypotter.data.remote.HarryPotterApi
import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Implementation of CharacterRepository following offline-first pattern.
 * 
 * Strategy:
 * 1. Observe Room database as single source of truth
 * 2. Trigger API refresh on first load
 * 3. Handle errors gracefully
 */
class CharacterRepositoryImpl(
    private val api: HarryPotterApi,
    private val dao: CharacterDao
) : CharacterRepository {
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun getCharacters(): Flow<Result<List<Character>>> {
        return dao.getAllCharacters()
            .map { entities ->
                Result.success(entities.toDomain())
            }
            .onStart {
                // Trigger background refresh when flow starts collecting
                scope.launch {
                    try {
                        refreshFromApi()
                    } catch (e: Exception) {
                        // Ignore refresh errors - we'll show cached data
                    }
                }
            }
            .catch { e ->
                emit(Result.failure(e))
            }
            .flowOn(Dispatchers.IO)
    }
    
    override fun getCharacterById(id: String): Flow<Result<Character>> {
        return dao.getCharacterById(id)
            .map { entity ->
                if (entity != null) {
                    Result.success(entity.toDomain())
                } else {
                    Result.failure(NoSuchElementException("Character not found"))
                }
            }
            .catch { e ->
                emit(Result.failure(e))
            }
            .flowOn(Dispatchers.IO)
    }
    
    override suspend fun toggleFavorite(id: String) {
        withContext(Dispatchers.IO) {
            dao.toggleFavorite(id)
        }
    }
    
    override suspend fun refreshCharacters(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                refreshFromApi()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    private suspend fun refreshFromApi() {
        val remoteCharacters = api.getCharacters()
        
        // Preserve favorite status when updating cache
        val existingEntities = dao.getAllCharactersSync().associateBy { it.id }
        val entities = remoteCharacters.map { dto ->
            dto.toEntity(existingEntities[dto.id])
        }
        
        dao.insertCharacters(entities)
    }
}
