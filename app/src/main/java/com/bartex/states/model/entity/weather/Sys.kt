package com.bartex.states.model.entity.weather

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Sys(
    @Expose val country :String? = "**"
) : Parcelable