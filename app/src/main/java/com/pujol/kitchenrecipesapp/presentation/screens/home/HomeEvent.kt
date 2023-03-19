package com.pujol.kitchenrecipesapp.presentation.screens.home

import com.pujol.kitchenrecipesapp.domain.model.Meal


sealed class HomeEvent {
    data class OnQueryChange(val query: String) : HomeEvent()
    data class OnNavigateDetail(val meal : Meal) : HomeEvent()
}