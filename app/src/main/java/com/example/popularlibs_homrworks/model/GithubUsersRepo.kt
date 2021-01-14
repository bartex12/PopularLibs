package com.example.popularlibs_homrworks.model

import rx.Observable

class GithubUsersRepo {
    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers(): Observable<GithubUser>{
        return Observable.from(repositories)
    }
}