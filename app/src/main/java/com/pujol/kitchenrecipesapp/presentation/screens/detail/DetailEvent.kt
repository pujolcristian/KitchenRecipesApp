package com.pujol.kitchenrecipesapp.presentation.screens.detail

import com.pujol.kitchenrecipesapp.domain.model.Meal


sealed class DetailEvent {
    data class OnNavigateMap(val meal : Meal) : DetailEvent()
    data class OnGetKitchenRecipeById (val id : String) : DetailEvent()
}