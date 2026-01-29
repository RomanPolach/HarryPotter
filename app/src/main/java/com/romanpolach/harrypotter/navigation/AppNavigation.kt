package com.romanpolach.harrypotter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import com.romanpolach.harrypotter.presentation.characterdetail.CharacterDetailScreen
import com.romanpolach.harrypotter.presentation.characterdetail.CharacterDetailViewModel
import com.romanpolach.harrypotter.presentation.characterlist.CharacterListScreen
import com.romanpolach.harrypotter.presentation.characterlist.CharacterListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Main navigation composable using Navigation 3.
 * The back stack is just a list - add/remove items to navigate!
 */
@Composable
fun AppNavigation() {

    val backStack = remember { mutableStateListOf<Any>(CharacterList) }
    
    val decorators: List<NavEntryDecorator<Any>> = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )
    
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = decorators,
        entryProvider = { key ->
            when (key) {
                is CharacterList -> NavEntry(key) {
                    val viewModel: CharacterListViewModel = koinViewModel()
                    CharacterListScreen(
                        viewModel = viewModel,
                        onCharacterClick = { characterId ->
                            backStack.add(CharacterDetail(characterId))
                        }
                    )
                }
                
                is CharacterDetail -> NavEntry(key) {
                    val viewModel: CharacterDetailViewModel = koinViewModel { 
                        parametersOf(key.characterId) 
                    }
                    CharacterDetailScreen(
                        viewModel = viewModel,
                        onBackClick = {
                            backStack.removeLastOrNull()
                        }
                    )
                }
                
                else -> NavEntry(key) { }
            }
        }
    )
}
