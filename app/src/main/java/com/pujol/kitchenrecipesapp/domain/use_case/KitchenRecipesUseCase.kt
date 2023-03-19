package com.pujol.kitchenrecipesapp.domain.use_case

data class KitchenRecipesUseCase(
    val getAllKitchenRecipes: GetAllKitchenRecipes,
    val searchKitchenRecipes: SearchKitchenRecipes,
    val getKitchenRecipeById: GetKitchenRecipeById
)