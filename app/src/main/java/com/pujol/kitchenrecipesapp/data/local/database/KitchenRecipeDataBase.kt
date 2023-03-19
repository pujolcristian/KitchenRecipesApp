package com.pujol.kitchenrecipesapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pujol.kitchenrecipesapp.data.local.dao.MealDao
import com.pujol.kitchenrecipesapp.data.local.entity.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1
)
abstract class KitchenRecipeDatabase : RoomDatabase() {
    abstract val mealDao: MealDao
}