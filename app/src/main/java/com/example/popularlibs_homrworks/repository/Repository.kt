package com.example.popularlibs_homrworks.repository

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File

interface Repository {
   fun readJPGpathFile():Observable<String>
   fun convertJPG_toPNG(file:File):Completable

   fun getDir(): Observable<File>
   fun saveJPGFile(fileRepo:File?):Completable
   fun getJPGBitmap():Bitmap
}