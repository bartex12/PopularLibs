package com.example.popularlibs_homrworks.presenter

import android.net.Uri

interface MainView {

    fun showError(message:String)

    fun showJPGimage(uri: Uri)

    fun showToast(message:String)
}