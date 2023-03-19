package com.pujol.kitchenrecipesapp.presentation.screens.detail

import com.pujol.kitchenrecipesapp.domain.model.Meal

data class DetailState(
    val kitchenRecipe: Meal = Meal(),
)
