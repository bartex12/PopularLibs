package com.bartex.states.view.adapter.imageloader

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}