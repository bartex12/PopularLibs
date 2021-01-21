package com.example.popularlibs_homrworks.model.repository

import com.example.popularlibs_homrworks.model.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}