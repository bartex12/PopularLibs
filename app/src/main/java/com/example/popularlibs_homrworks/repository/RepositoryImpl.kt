package com.example.popularlibs_homrworks.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.view.TAG
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class RepositoryImpl:Repository {

    companion object{
        const val JPG_FILE = "tree.jpg"
        const val PNG_FILE = "tree.png"
    }

    override fun getDir(): Observable<File> {
        var sdCard: File? = null

        //Получаем состояние SD карты
        val sdState = Environment.getExternalStorageState()
        //если sdState == MEDIA_MOUNTED
        if (sdState.equals(Environment.MEDIA_MOUNTED)){
            try {
                sdCard = App.instance.applicationContext
                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                Log.d(TAG, "RepositoryImpl : MEDIA_MOUNTED")
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

    override fun saveJPGFile(fileRepo:File?) = Completable.create { emitter ->
        saveJPG(fileRepo).let {
            if (it) {
                emitter.onComplete()
            } else {
                emitter.onError(RuntimeException(" Ошибка при сохранении в JPG файл "))
                return@create
            }
        }
    }


    fun saveJPG(fileRepo:File?):Boolean{
        val file = File(fileRepo, JPG_FILE  )
        if (file.exists ()) file.delete()
        Log.d(TAG, "RepositoryImpl saveJPG путь к JPG = ${file.absolutePath}")
        //получаем  Bitmap из ресурсов
        val bitmap =BitmapFactory.decodeResource(App.instance.resources, R.drawable.tree)
        var fOut: OutputStream? =null
        try{
            fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            return true
        }catch (e:Exception){
            e.printStackTrace()
            return false
        }finally {
            fOut?.close()
        }
    }

    override fun convertJPG_toPNG(file:File)= Completable.create {emitter ->
        convertToPNG(file). let{
            if (it){
                emitter.onComplete()
            }else{
                emitter.onError(RuntimeException("Ошибка конвертации JPG в PNG"))
            }
        }
    }

    fun convertToPNG(file:File):Boolean {
        var fOut: OutputStream? =null
        val oldFile  = File(file, JPG_FILE) // путь к jpg
        val bitmap = BitmapFactory.decodeFile(oldFile.absolutePath) //bitmap для jpg
        val newFile = File(file, PNG_FILE) //меняем файл - новый путь
        Log.d(TAG, "RepositoryImpl convertToPNG путь к PNG = ${newFile.absolutePath}")
        try{
            fOut = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fOut) //конвертация в png
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