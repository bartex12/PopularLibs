package com.example.popularlibs_homrworks.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


//Аннотация @Parcelize говорит о необходимости сгенерировать весь boilerplate-код,
// необходимый для работы Parcelable, автоматически, избавляя нас от рутины его написания вручную.
//поля сущностей, не помеченные аннотацией @Expose, должны игнорироваться при преобразовании.
@Parcelize
data class GithubUser(
    @Expose val id: String? = null,
    @Expose val login: String? = null,
    @Expose val avatarUrl: String? = null,
    @Expose val repos_url:String? = null
):Parcelable, Serializable {
}