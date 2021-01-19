package com.example.popularlibs_homrworks.presenter

import android.graphics.BitmapFactory
import android.util.Log
import com.example.popularlibs_homrworks.repository.Repository
import com.example.popularlibs_homrworks.view.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class MainPresenterImpl(val view:MainView, val repo: Repository): MainPresenter  {

    override fun getJPGBitmap() {
        val bitmap = repo.getJPGBitmap()
        view.showJPGimage(bitmap)
    }

    override fun saveJPGfile() {
        repo.getDir()
            .subscribeOn(Schedulers.io())
            .subscribe(
            {repo.saveJPGFile(it)
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                     {view.saveJPGsuccess_Toast()},
                     {it.message?.let { it1 -> view.showError(it1) }})
            },
            {it.message?.let { it1 -> view.showError(it1) }},
            { Log.d(TAG, "MainPresenterImpl saveJPGfile  - Завершено успешно")})

    }

    override fun readJPG() {
        repo.readJPGpathFile()
            .map {BitmapFactory.decodeFile(it)}
            .subscribe(
                {view.showJPGimage(it)},
                {it.message?.let { it1 -> view.showError(it1) }})
    }

    override fun convertJPG_toPNG() {
        repo.readJPGpathFile()
            .subscribeOn(Schedulers.io())
            .map { File(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                repo.convertJPG_toPNG(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (
                        {Log.d(TAG, "MainPresenterImpl convertJPG_toPNG  -${it.absolutePath.substringAfter(".")}")
                            view.showPNGsuccess_Toast()},
                        {it.message?.let { it1 -> view.showError(it1)
                         Log.d(TAG, "MainPresenterImpl convertJPG_toPNG  -Конвертация НЕ успешно")
                         }})
            },
                {it.message?.let { it1 -> view.showError(it1) }},
                {Log.d(TAG, "MainPresenterImpl convertJPG_toPNG  - Путь получен успешно")})
    }
}