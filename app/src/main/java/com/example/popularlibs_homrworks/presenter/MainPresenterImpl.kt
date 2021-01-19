package com.example.popularlibs_homrworks.presenter

import android.net.Uri
import com.example.popularlibs_homrworks.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class MainPresenterImpl(val view:MainView, val repo: Repository): MainPresenter  {


    override fun saveJPGfile() {
        repo.getDir()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(
            {repo.saveJPGFile(it)
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                     {view.saveJPGsuccess_Toast()},
                     {it.message?.let { it1 -> view.showError(it1) }})
            },
            {it.message?.let { it1 -> view.showError(it1) }})

    }

override fun readAndShowJPG() {
    repo.getDir()
        .subscribeOn(Schedulers.io()) //уходим в другой поток
        .map {File(it, "tree.jpg")} //переход от папки к файлу изображения
        .map { it.absolutePath} //переход к строке для пути
        .map {Uri.parse(it)}//переход к Uri
        .observeOn(AndroidSchedulers.mainThread()) //возврат в поток UI
        .subscribe(
            {view.showJPGimage(it)},
            {it.message?.let { it1 -> view.showError(it1) }})
}

    override fun convertJPG_toPNG() {
        repo.getDir()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe ({
                repo.convertJPG_toPNG(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (
                        {view.showPNGsuccess_Toast()},
                        {it.message?.let { it1 -> view.showError(it1) }})
            },
                {it.message?.let { it1 -> view.showError(it1) }})
    }
}