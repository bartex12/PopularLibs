package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashfile

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File

interface IAvatarFile {
    fun getDir(): Observable<File>
    fun saveJPGFile(file: File?, bitmap: Bitmap?): Completable
}