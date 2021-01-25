package com.example.popularlibs_homrworks.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUserRepos(
    @Expose val id:String? = null,
    @Expose val name:String? = null,
    @Expose val forks:Int? = 0
) :Parcelable{
}