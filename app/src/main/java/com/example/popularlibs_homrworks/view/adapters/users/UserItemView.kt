package com.example.popularlibs_homrworks.view.adapters.users

import com.example.popularlibs_homrworks.view.adapters.IItemView


interface UserItemView:
    IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}