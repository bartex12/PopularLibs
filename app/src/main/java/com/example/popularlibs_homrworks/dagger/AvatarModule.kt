package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashfile.AvatarFile
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashfile.IAvatarFile
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimage.IRoomGithubAvatarCache
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimage.RoomGithubAvatarCache
import dagger.Module
import dagger.Provides

@Module
class AvatarModule {

    @Provides
    fun avatarCash(repoFile: IAvatarFile): IRoomGithubAvatarCache{
      return  RoomGithubAvatarCache(repoFile)
    }

    @Provides
    fun avatarFile(): IAvatarFile{
       return AvatarFile()
    }
}