package com.pujol.kitchenrecipesapp.data.mapper

import com.pujol.kitchenrecipesapp.data.local.entity.MealEntity
import com.pujol.kitchenrecipesapp.data.remote.dto.MealsDto
import com.pujol.kitchenrecipesapp.domain.model.Meal

fun MealEntity.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        ingredients = ingredients,
        latitude = latitude,
        longitude = longitude,
        measures = measures,
        strArea = strArea,
        strCategory = strCategory,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strSource = strSource,
        strTags = strTags,
        strYoutube = strYoutube
    )
}

fun MealsDto.toMealEntity(): List<MealEntity> {
    val list = mutableListOf<MealEntity>()
    meals?.forEach {
        list.add(
            MealEntity(
                idMeal = it.idMeal?:"",
                ingredients = it.ingredients?: "",
                latitude = it.latitude?:"",
                longitude = it.longitude?:"",
                measures = it.measures?: "",
                strArea = it.strArea?:"",
                strCategory = it.strCategory?:"",
                strInstructions = it.strInstructions?:"",
                strMeal = it.strMeal?:"",
                strMealThumb = it.strMealThumb?:"",
                strSource = it.strSource?:"",
                strTags = it.strTags?:"",
                strYoutube = it.strYoutube?:""
            )
        )
    }
    return list
}