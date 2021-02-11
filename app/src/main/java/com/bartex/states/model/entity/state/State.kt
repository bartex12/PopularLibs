package com.bartex.states.model.entity.state

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

//Аннотация @Parcelize говорит о необходимости сгенерировать весь boilerplate-код,
// необходимый для работы Parcelable, автоматически, избавляя нас от рутины его написания вручную.
@Parcelize
data class State(
    @Expose val numericCode :Int? = null,
    @Expose val flag :String? = null,
    @Expose val name :String? = null,
    @Expose val alpha3Code :String? = null,
    @Expose val capital :String? = null,
    @Expose val region :String? = null,
    @Expose val population :Int? = null,
    @Expose val area :Float? = null
): Parcelable