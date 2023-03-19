package com.pujol.kitchenrecipesapp.presentation.screens.map

sealed class MapEvent {
    data class OnsetLatLng(val latitude: String, val longitude: String) : MapEvent()
}