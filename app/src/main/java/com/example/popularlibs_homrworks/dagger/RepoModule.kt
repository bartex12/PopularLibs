package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.network.INetworkStatus
import com.example.popularlibs_homrworks.model.repositories.userrepo.IGithubRepositoriesRepo
import com.example.popularlibs_homrworks.model.repositories.userrepo.RetrofitGithubRepositoriesRepo
import com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos.IRoomRepositiriesRepoCash
import com.example.popularlibs_homrworks.model.repositories.usersrepo.IGithubUsersRepo
import com.example.popularlibs_homrworks.model.repositories.usersrepo.RetrofitGithubUsersRepo
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusers.IRoomGithubUsersCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
    class RepoModule {

    @Provides
    @Singleton
        fun usersRepo (api: IDataSource, networkStatus: INetworkStatus,
                       roomCash: IRoomGithubUsersCache): IGithubUsersRepo =
    RetrofitGithubUsersRepo(api, networkStatus,  roomCash)

    @Provides
    @Singleton
    fun userRepo (api: IDataSource, networkStatus: INetworkStatus,
                  roomCash: IRoomRepositiriesRepoCash): IGithubRepositoriesRepo =
        RetrofitGithubRepositoriesRepo(api, networkStatus,  roomCash)
}