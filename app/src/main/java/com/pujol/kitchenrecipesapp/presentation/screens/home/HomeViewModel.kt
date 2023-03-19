package com.pujol.kitchenrecipesapp.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pujol.kitchenrecipesapp.core.utils.UiEvent
import com.pujol.kitchenrecipesapp.core.utils.UiText
import com.pujol.kitchenrecipesapp.data.remote.response.Resource
import com.pujol.kitchenrecipesapp.domain.use_case.KitchenRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import javax.crypto.Cipher
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val kitchenRecipesUseCase: KitchenRecipesUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAllKitchenRecipes()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
                onSearch()
            }
            is HomeEvent.OnNavigateDetail -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(event.meal.idMeal?:""))
                }
            }
        }
    }

    private fun onSearch() {
        kitchenRecipesUseCase.searchKitchenRecipes(state.query)
            .onEach { kitchenRecipes ->
                state = state.copy(
                    kitchenRecipes = kitchenRecipes
                )
            }.launchIn(viewModelScope)
    }

    private fun getAllKitchenRecipes() {
        viewModelScope.launch {
            kitchenRecipesUseCase.getAllKitchenRecipes()
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                kitchenRecipes = resource.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                kitchenRecipes = resource.data ?: emptyList()
                            )
                            _uiEvent.send(
                                UiEvent.ShowSnackBar(
                                    message = UiText.DynamicString("${resource.message}")
                                )
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true,
                                kitchenRecipes = resource.data ?: emptyList(),
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}