package com.example.popularlibs_homrworks.view.adapter


interface UserItemView:IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}