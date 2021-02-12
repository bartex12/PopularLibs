package com.bartex.states.model.entity.state

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class LatLng (
    @Expose val lat :Float? = null,
    @Expose val lng :Float? = null
):Parcelable