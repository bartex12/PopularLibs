package com.bartex.states.model.entity.weather

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Weather(
    @Expose val id :Int? = 0,
    @Expose val main: String? = null,
    @Expose val description: String? = null,
    @Expose val icon: String? = null
): Parcelable