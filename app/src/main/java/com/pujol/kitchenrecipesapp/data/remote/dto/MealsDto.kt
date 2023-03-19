package com.pujol.kitchenrecipesapp.data.remote.dto

import com.squareup.moshi.Json


data class MealsDto(
    @field:Json(name = "meals")
    val meals: List<Meal>? = emptyList()
)