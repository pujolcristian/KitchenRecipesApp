package com.pujol.kitchenrecipesapp.domain.use_case

import com.pujol.kitchenrecipesapp.domain.model.Meal
import com.pujol.kitchenrecipesapp.domain.repository.KitchenRecipesRepository
import kotlinx.coroutines.flow.Flow

class SearchKitchenRecipes(private val repository: KitchenRecipesRepository) {

    operator fun invoke(query: String): Flow<List<Meal>> {
        return repository.searchKitchenRecipes(query)
    }
}