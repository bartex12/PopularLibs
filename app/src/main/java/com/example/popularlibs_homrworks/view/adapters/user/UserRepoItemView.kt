package com.example.popularlibs_homrworks.view.adapters.user

import com.example.popularlibs_homrworks.view.adapters.IItemView


interface UserRepoItemView:
    IItemView {
    fun setId(text: String)
    fun setName(text: String)
    fun setForks(forks: Int)

}