package com.romanpolach.harrypotter.presentation.characterdetail

import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.util.UiText

/**
 * MVI Contract for Character Detail Screen.
 */
object CharacterDetailContract {
    
    /**
     * UI State for character detail screen.
     */
    data class State(
        val isLoading: Boolean = true,
        val character: Character? = null,
        val error: UiText? = null
    )
    
    /**
     * User intents/actions.
     */
    sealed interface Intent {
        data object LoadCharacter : Intent
        data object ToggleFavorite : Intent
        data object GoBack : Intent
    }
    
    /**
     * Side effects (one-time events).
     */
    sealed interface Effect {
        data object GoBack : Effect
        data class ShowError(val message: UiText) : Effect
    }
}
