package com.pujol.kitchenrecipesapp.domain.use_case

import com.pujol.kitchenrecipesapp.data.remote.response.Resource
import com.pujol.kitchenrecipesapp.domain.model.Meal
import com.pujol.kitchenrecipesapp.domain.repository.KitchenRecipesRepository
import kotlinx.coroutines.flow.Flow

class GetKitchenRecipeById (private val repository: KitchenRecipesRepository){

    operator fun invoke(id: String) : Flow<Meal> {
        return repository.getKitchenRecipeById(id = id)
    }
}