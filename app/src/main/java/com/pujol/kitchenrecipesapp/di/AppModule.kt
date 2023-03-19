package com.pujol.kitchenrecipesapp.di

import android.app.Application
import androidx.room.Room
import com.pujol.kitchenrecipesapp.BuildConfig
import com.pujol.kitchenrecipesapp.data.local.database.KitchenRecipeDatabase
import com.pujol.kitchenrecipesapp.data.remote.api.KitchenRecipesApi
import com.pujol.kitchenrecipesapp.data.repository.KitchenRecipesRepositoryImpl
import com.pujol.kitchenrecipesapp.domain.repository.KitchenRecipesRepository
import com.pujol.kitchenrecipesapp.domain.use_case.GetAllKitchenRecipes
import com.pujol.kitchenrecipesapp.domain.use_case.GetKitchenRecipeById
import com.pujol.kitchenrecipesapp.domain.use_case.KitchenRecipesUseCase
import com.pujol.kitchenrecipesapp.domain.use_case.SearchKitchenRecipes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideKitchenRecipesApi(retrofit: Retrofit): KitchenRecipesApi {
        return retrofit.create(KitchenRecipesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKitchenRecipeDataBase(app: Application): KitchenRecipeDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            KitchenRecipeDatabase::class.java,
            "kitchen_recipe_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideKitchenRecipesRepository(
        kitchenRecipesApi: KitchenRecipesApi,
        database: KitchenRecipeDatabase
    ): KitchenRecipesRepository {
        return KitchenRecipesRepositoryImpl(kitchenRecipesApi, database.mealDao)
    }

    @Provides
    @Singleton
    fun provideKitchenRecipesUseCases(repository: KitchenRecipesRepository): KitchenRecipesUseCase {
        return KitchenRecipesUseCase(
            getAllKitchenRecipes = GetAllKitchenRecipes(repository = repository),
            searchKitchenRecipes = SearchKitchenRecipes(repository = repository),
            getKitchenRecipeById = GetKitchenRecipeById(repository = repository)
        )
    }
}