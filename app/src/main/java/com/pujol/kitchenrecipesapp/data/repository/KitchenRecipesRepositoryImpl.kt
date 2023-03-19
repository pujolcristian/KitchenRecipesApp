package com.pujol.kitchenrecipesapp.data.repository

import android.util.Log
import com.pujol.kitchenrecipesapp.data.local.dao.MealDao
import com.pujol.kitchenrecipesapp.data.mapper.toMeal
import com.pujol.kitchenrecipesapp.data.mapper.toMealEntity
import com.pujol.kitchenrecipesapp.data.remote.api.KitchenRecipesApi
import com.pujol.kitchenrecipesapp.data.remote.response.Resource
import com.pujol.kitchenrecipesapp.domain.model.Meal
import com.pujol.kitchenrecipesapp.domain.repository.KitchenRecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KitchenRecipesRepositoryImpl(
    private val kitchenRecipesApi: KitchenRecipesApi,
    private val mealDao: MealDao
) :
    KitchenRecipesRepository {
    override fun getKitchenRecipes(): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        val meals = mealDao.getAllMeals().map { it.toMeal() }
        emit(Resource.Loading(data = meals))

        try {
            val remoteMeals = kitchenRecipesApi.getRecipesKitchen()
            val list = remoteMeals.toMealEntity()
            mealDao.deleteAllMeal()
            mealDao.insertAllMeal(list)
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "Oops, algo salió mal, vuelve a intentar.",
                    data = meals
                )
            )
            Log.d("mplTAG", e.toString())
            return@flow
        }

        val newMeals = mealDao.getAllMeals().map { it.toMeal() }
        emit(Resource.Success(data = newMeals))
    }

    override fun searchKitchenRecipes(query: String): Flow<List<Meal>> = flow {
        val meals = mealDao.getAllMealsByQuery(query).map { it.toMeal() }
        emit(meals)
    }

    override fun getKitchenRecipeById(id: String): Flow<Meal> = flow {
        val meal = mealDao.getMealsById(id).toMeal()
        emit(meal)
    }
}