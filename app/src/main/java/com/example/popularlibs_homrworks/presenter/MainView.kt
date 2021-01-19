package com.example.popularlibs_homrworks.presenter

import android.net.Uri

interface MainView {

    fun saveJPGsuccess_Toast()
    fun showJPGerror(message:String)

    fun showError(message:String)

    fun showJPGimage(uri: Uri)
    fun showPNGsuccess_Toast()
}