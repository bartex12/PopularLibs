package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimage

import android.graphics.Bitmap
import com.example.popularlibs_homrworks.model.room.Database
import io.reactivex.rxjava3.core.Single

interface IRoomGithubAvatarCache {
    fun doAvatarsCache(db: Database, bitmap: Bitmap?, url: String): Single<String>
    fun getAvatarsFromCash( db: Database, url: String): Single<String>
}