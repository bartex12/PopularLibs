package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashfile

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class AvatarFile:IAvatarFile {

    override fun getDir(): Observable<File> {
        var sdCard: File? = null
        //Получаем состояние SD карты
        val sdState = Environment.getExternalStorageState()
        //если sdState == MEDIA_MOUNTED
        if (sdState.equals(Environment.MEDIA_MOUNTED)){
            try {
                sdCard = App.instance.applicationContext
                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                //Log.d(TAG, "RepositoryImpl : MEDIA_MOUNTED")
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            //если sdState == MEDIA_SHARED
        }else if (sdState.equals(Environment.MEDIA_SHARED)){
            try {
                sdCard = App.instance.getFilesDir()
                Log.d(TAG, "RepositoryImpl : MEDIA_SHARED")
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        val sdCardCat = sdCard?. let {File(sdCard, "/MyImages")}
        sdCardCat?.mkdir()
        return Observable.just(sdCardCat)
    }

    override fun saveJPGFile(file:File?,  bitmap: Bitmap?) =
        Completable.create { emitter ->
            saveJPG(file, bitmap).let {
                if (it) {
                    emitter.onComplete()
                } else {
                    emitter.onError(RuntimeException(" Ошибка при сохранении в JPG файл "))
                    return@create
                }
            }
        }

    fun saveJPG(file:File?,  bitmap: Bitmap?):Boolean{
        file?. let{if (it.exists ()) it.delete()}
        var fOut: OutputStream? =null
        try{
            fOut = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            return true
        }catch (e:Exception){
            e.printStackTrace()
            return false
        }finally {
            fOut?.close()
        }
    }
}