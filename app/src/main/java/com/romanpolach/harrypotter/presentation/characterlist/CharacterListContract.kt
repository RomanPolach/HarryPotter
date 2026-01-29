package com.romanpolach.harrypotter.presentation.characterlist

import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.util.UiText

/**
 * MVI Contract for Character List Screen.
 */
object CharacterListContract {
    
    /**
     * UI State for character list screen.
     */
    data class State(
        val isLoading: Boolean = true,
        val characters: List<Character> = emptyList(),
        val error: UiText? = null,
        val showOnlyFavorites: Boolean = false,
        val isRefreshing: Boolean = false
    ) {
        val displayedCharacters: List<Character>
            get() = if (showOnlyFavorites) {
                characters.filter { it.isFavorite }
            } else {
                characters
            }
    }
    
    /**
     * User intents/actions.
     */
    sealed interface Intent {
        data object LoadCharacters : Intent
        data object Refresh : Intent
        data object ToggleFavoritesFilter : Intent
        data class ToggleFavorite(val characterId: String) : Intent
        data class NavigateToDetail(val characterId: String) : Intent
    }
    
    /**
     * Side effects (one-time events).
     */
    sealed interface Effect {
        data class NavigateToDetail(val characterId: String) : Effect
        data class ShowError(val message: UiText) : Effect
    }
}
