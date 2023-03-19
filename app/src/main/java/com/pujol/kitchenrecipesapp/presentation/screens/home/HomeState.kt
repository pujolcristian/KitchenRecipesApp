package com.pujol.kitchenrecipesapp.presentation.screens.home

import com.pujol.kitchenrecipesapp.domain.model.Meal

data class HomeState(
    val kitchenRecipes: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = "",
)
