package com.romanpolach.harrypotter.presentation.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romanpolach.harrypotter.domain.usecase.GetCharacterByIdUseCase
import com.romanpolach.harrypotter.domain.usecase.ToggleFavoriteUseCase
import com.romanpolach.harrypotter.util.UiText
import com.romanpolach.harrypotter.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Character Detail Screen following MVI pattern.
 */
class CharacterDetailViewModel(
    private val characterId: String,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(CharacterDetailContract.State())
    val state: StateFlow<CharacterDetailContract.State> = _state.asStateFlow()
    
    private val _effect = Channel<CharacterDetailContract.Effect>()
    val effect = _effect.receiveAsFlow()
    
    init {
        handleIntent(CharacterDetailContract.Intent.LoadCharacter)
    }
    
    fun handleIntent(intent: CharacterDetailContract.Intent) {
        when (intent) {
            is CharacterDetailContract.Intent.LoadCharacter -> loadCharacter()
            is CharacterDetailContract.Intent.ToggleFavorite -> toggleFavorite()
            is CharacterDetailContract.Intent.GoBack -> goBack()
        }
    }
    
    private fun loadCharacter() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            getCharacterByIdUseCase(characterId).collect { result ->
                result.fold(
                    onSuccess = { character ->
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                character = character,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _state.update { 
                            it.copy(
                                isLoading = false,
                                error = error.message?.let { UiText.DynamicString(it) } 
                                    ?: UiText.StringResource(R.string.error_load_character_failed)
                            )
                        }
                    }
                )
            }
        }
    }
    
    private fun toggleFavorite() {
        viewModelScope.launch {
            toggleFavoriteUseCase(characterId)
            // State will be updated through the Flow
        }
    }
    
    private fun goBack() {
        viewModelScope.launch {
            _effect.send(CharacterDetailContract.Effect.GoBack)
        }
    }
}
