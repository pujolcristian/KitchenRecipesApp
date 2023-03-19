package com.pujol.kitchenrecipesapp.core.utils

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(val message: UiText) : UiEvent()
}
