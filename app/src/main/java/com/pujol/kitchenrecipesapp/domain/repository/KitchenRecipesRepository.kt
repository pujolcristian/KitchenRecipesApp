package com.pujol.kitchenrecipesapp.domain.repository

import com.pujol.kitchenrecipesapp.data.remote.response.Resource
import com.pujol.kitchenrecipesapp.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface KitchenRecipesRepository {

    fun getKitchenRecipes(): Flow<Resource<List<Meal>>>

    fun searchKitchenRecipes(query: String): Flow<List<Meal>>

    fun getKitchenRecipeById(id: String): Flow<Meal>
}