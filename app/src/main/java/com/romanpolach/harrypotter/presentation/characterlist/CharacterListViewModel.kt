package com.romanpolach.harrypotter.presentation.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.romanpolach.harrypotter.domain.usecase.GetCharactersPagingUseCase
import com.romanpolach.harrypotter.domain.usecase.GetCharactersUseCase
import com.romanpolach.harrypotter.domain.usecase.RefreshCharactersUseCase
import com.romanpolach.harrypotter.domain.usecase.ToggleFavoriteUseCase
import com.romanpolach.harrypotter.util.UiText
import com.romanpolach.harrypotter.R
import com.romanpolach.harrypotter.domain.model.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Character List Screen following MVI pattern.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersPagingUseCase: GetCharactersPagingUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val refreshCharactersUseCase: RefreshCharactersUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(CharacterListContract.State())
    val state: StateFlow<CharacterListContract.State> = _state.asStateFlow()
    
    private val _effect = Channel<CharacterListContract.Effect>()
    val effect = _effect.receiveAsFlow()
    
    val pagingDataFlow: Flow<PagingData<Character>> = _state
        .map { it.showOnlyFavorites }
        .flatMapLatest { showOnlyFavorites ->
            getCharactersPagingUseCase(showOnlyFavorites)
        }
        .cachedIn(viewModelScope)
    
    init {
        handleIntent(CharacterListContract.Intent.LoadCharacters)
    }
    
    fun handleIntent(intent: CharacterListContract.Intent) {
        when (intent) {
            is CharacterListContract.Intent.LoadCharacters -> loadCharacters()
            is CharacterListContract.Intent.Refresh -> refresh()
            is CharacterListContract.Intent.ToggleFavoritesFilter -> toggleFavoritesFilter()
            is CharacterListContract.Intent.ToggleFavorite -> toggleFavorite(intent.characterId)
            is CharacterListContract.Intent.NavigateToDetail -> navigateToDetail(intent.characterId)
        }
    }
    
    private fun loadCharacters() {
        // Paging handles loading automatically. 
        // This method can be used for explicit refresh if needed, 
        // but currently PullToRefresh handles it.
        _state.update { it.copy(isLoading = false) }
    }
    
    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            
            refreshCharactersUseCase().fold(
                onSuccess = {
                    _state.update { it.copy(isRefreshing = false) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isRefreshing = false) }
                    _effect.send(CharacterListContract.Effect.ShowError(
                        error.message?.let { UiText.DynamicString(it) } 
                            ?: UiText.StringResource(R.string.error_refresh_failed)
                    ))
                }
            )
        }
    }
    
    private fun toggleFavoritesFilter() {
        _state.update { it.copy(showOnlyFavorites = !it.showOnlyFavorites) }
    }
    
    private fun toggleFavorite(characterId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(characterId)
            // State will be updated through the Flow from repository
        }
    }
    
    private fun navigateToDetail(characterId: String) {
        viewModelScope.launch {
            _effect.send(CharacterListContract.Effect.NavigateToDetail(characterId))
        }
    }
}
