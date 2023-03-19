package com.pujol.kitchenrecipesapp.data.remote.api

import com.pujol.kitchenrecipesapp.data.remote.dto.MealsDto
import retrofit2.http.GET

interface KitchenRecipesApi {
    @GET("b2847764-530e-4825-8d43-979370572cc0")
    suspend fun getRecipesKitchen(): MealsDto
}