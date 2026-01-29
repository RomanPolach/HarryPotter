package com.romanpolach.harrypotter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romanpolach.harrypotter.data.local.dao.CharacterDao
import com.romanpolach.harrypotter.data.local.entity.CharacterEntity

/**
 * Room database for the Harry Potter app.
 */
@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun characterDao(): CharacterDao
    
    companion object {
        const val DATABASE_NAME = "harry_potter_db"
    }
}
