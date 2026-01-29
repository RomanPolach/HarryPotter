package com.romanpolach.harrypotter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.romanpolach.harrypotter.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for character database operations.
 */
@Dao
interface CharacterDao {
    
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: String): Flow<CharacterEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    
    @Query("UPDATE characters SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: String)
    
    @Query("SELECT * FROM characters")
    suspend fun getAllCharactersSync(): List<CharacterEntity>
}
