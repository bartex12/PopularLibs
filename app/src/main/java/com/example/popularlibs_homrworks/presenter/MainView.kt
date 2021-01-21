package com.example.popularlibs_homrworks.presenter

interface MainView {

    fun showError(message:String)
    fun showJPGimage(uriString: String)
    fun showToast(message:String)
    fun showDialog()
}