package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimageroom

import android.graphics.Bitmap
import com.example.popularlibs_homrworks.model.room.Database

interface IRoomGithubAvatarCache {
    fun doAvatarsCache(db: Database, bitmap: Bitmap?, url: String)
}