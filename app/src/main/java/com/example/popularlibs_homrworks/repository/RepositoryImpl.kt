package com.example.popularlibs_homrworks.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.view.TAG
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class RepositoryImpl:Repository {
    override fun readJPGpathFile():Observable<String> {
        val pathFile = App.instance.applicationContext
            .getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath +"/MyImages/tree.jpg"
        //val bitmap:Bitmap = BitmapFactory.decodeFile(pathFile)
        return Observable.just(pathFile)
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
        val bitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
        try{
            fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fOut)
            fOut.flush()
            Toast.makeText(App.instance, " Сохранено PNG", Toast.LENGTH_SHORT ).show()
            return true
        }catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(App.instance, "НЕ сохранено PNG ${e.printStackTrace()}", Toast.LENGTH_LONG ).show()
            return false
        }finally {
            fOut?.close()
        }
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
                Log.d(TAG, "saveFile:MEDIA_MOUNTED")
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            //если sdState == MEDIA_SHARED
        }else if (sdState.equals(Environment.MEDIA_SHARED)){
            try {
                sdCard = App.instance.getFilesDir()
                Log.d(TAG, "saveFile:MEDIA_SHARED")
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        val sdCardCat = sdCard?. let {File(sdCard, "/MyImages")}
        Log.d(TAG, "saveFile: путь = ${sdCardCat?.absolutePath}")
        sdCardCat?.mkdir()
        return Observable.just(sdCardCat)


    }

    override fun getJPGBitmap():Bitmap {
        val bitmap =BitmapFactory.decodeResource(App.instance.resources, R.drawable.tree)
        return bitmap
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
        val file: File = File(fileRepo, "tree.jpg"  )
        if (file.exists ()) file.delete()

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


}