package com.example.popularlibs_homrworks.presenter

import android.graphics.Bitmap
import java.io.File

interface MainView {

    fun saveJPGsuccess_Toast()
    fun showJPGerror(message:String)

    fun showError(message:String)

    fun showJPGimage(bitmap: Bitmap)
    fun showPNGsuccess_Toast()
}