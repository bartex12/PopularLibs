package com.bartex.states.model.entity.weather

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherInCapital(
    @Expose val weather: List<Weather>? = null,
    @Expose val main: Main? = null,
    @Expose val sys: Sys? = null,
    @Expose val id : Long? = 0,
    @Expose val name: String? = "",
    @Expose val cod: Int? = 0
) : Parcelable