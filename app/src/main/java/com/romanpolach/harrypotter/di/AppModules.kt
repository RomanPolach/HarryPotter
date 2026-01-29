package com.romanpolach.harrypotter.di

import androidx.room.Room
import com.romanpolach.harrypotter.data.local.AppDatabase
import com.romanpolach.harrypotter.data.remote.HarryPotterApi
import com.romanpolach.harrypotter.data.repository.CharacterRepositoryImpl
import com.romanpolach.harrypotter.domain.repository.CharacterRepository
import com.romanpolach.harrypotter.domain.usecase.GetCharacterByIdUseCase
import com.romanpolach.harrypotter.domain.usecase.GetCharactersUseCase
import com.romanpolach.harrypotter.domain.usecase.RefreshCharactersUseCase
import com.romanpolach.harrypotter.domain.usecase.ToggleFavoriteUseCase
import com.romanpolach.harrypotter.presentation.characterdetail.CharacterDetailViewModel
import com.romanpolach.harrypotter.presentation.characterlist.CharacterListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Network module - Ktor client configuration
 */
val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
    }
    
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(get())
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
    
    single { HarryPotterApi(get()) }
}

/**
 * Database module - Room configuration
 */
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    
    single { get<AppDatabase>().characterDao() }
}

/**
 * Repository module
 */
val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
}

/**
 * Use case module
 */
val useCaseModule = module {
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterByIdUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { RefreshCharactersUseCase(get()) }
}

/**
 * ViewModel module
 */
val viewModelModule = module {
    viewModel { CharacterListViewModel(get(), get(), get()) }
    viewModel { (characterId: String) -> CharacterDetailViewModel(characterId, get(), get()) }
}

/**
 * All application modules
 */
val appModules = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
