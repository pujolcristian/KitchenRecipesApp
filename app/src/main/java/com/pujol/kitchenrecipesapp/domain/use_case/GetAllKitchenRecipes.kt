package com.pujol.kitchenrecipesapp.domain.use_case

import com.pujol.kitchenrecipesapp.data.remote.response.Resource
import com.pujol.kitchenrecipesapp.domain.model.Meal
import com.pujol.kitchenrecipesapp.domain.repository.KitchenRecipesRepository
import kotlinx.coroutines.flow.Flow

class GetAllKitchenRecipes (private val repository: KitchenRecipesRepository){

    operator fun invoke() : Flow<Resource<List<Meal>>>{
        return repository.getKitchenRecipes()
    }
}