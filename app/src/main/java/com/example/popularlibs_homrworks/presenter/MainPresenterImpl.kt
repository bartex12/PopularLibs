package com.example.popularlibs_homrworks.presenter

import android.net.Uri
import android.util.Log
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.repository.Repository
import com.example.popularlibs_homrworks.view.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File


/*
*** Расположение каталога  зависит от состояние SD карты, поэтому каждый раз getDir()
*/
class MainPresenterImpl(val view:MainView, val repo: Repository): MainPresenter {

    override fun saveJPGfile() {
        repo.getDir()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(
                {
                    repo.saveJPGFile(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { view.showToast(App.instance.resources.getString(R.string.jpg_success)) },
                            { it.message?.let { it1 -> view.showError(it1) } })
                },
                { it.message?.let { it1 -> view.showError(it1) } })

    }

    override fun readAndShowJPG() {
        repo.getDir()
            .subscribeOn(Schedulers.io()) //уходим в другой поток
            .map { File(it, "tree.jpg") } //переход от папки к файлу изображения
            .map { it.absolutePath } //переход к строке для пути
            .map { Uri.parse(it) }//переход к Uri
            .map { it.toString() }//переход к строке с uri
            .observeOn(AndroidSchedulers.mainThread()) //возврат в поток UI
            .subscribe(
                {view.showJPGimage(it)},
                { it.message?.let { it1 -> view.showError(it1) } })
    }

    override fun showConvertDialog() {
        view.showDialog()
    }

    override fun convertJPGtoPNG() {
        Log.d(TAG, "MainPresenterImpl convert *** ")
        repo.getDir()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())//переход в поток для вычислений
            .subscribe({
                repo.convertJPG_toPNG(it)  //конвертируем JPG в PNG
                    .observeOn(AndroidSchedulers.mainThread())//переход в main поток для работы с view
                    .subscribe(
                        { view.showToast(App.instance.resources.getString(R.string.png_success)) },
                        { it.message?.let { it1 -> view.showToast(it1) } })
            },
                { it.message?.let { it1 -> view.showError(it1) } })
    }

}