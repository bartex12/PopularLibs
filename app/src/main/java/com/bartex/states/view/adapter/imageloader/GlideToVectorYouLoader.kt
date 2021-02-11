package com.bartex.states.view.adapter.imageloader

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bartex.states.view.main.TAG
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener

class GlideToVectorYouLoader(val activity: Activity):
    IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        GlideToVectorYou
            .init()
            .with(activity)
            .withListener(object : GlideToVectorYouListener {
                override fun onLoadFailed() {
                    Log.d(TAG, "GlideToVectorYouLoader onLoadFailed: ")
                }

                override fun onResourceReady() {
                    Log.d(TAG, "GlideToVectorYouLoader onResourceReady: ")
                }
            })
            //.setPlaceHolder(placeholderLoading, placeholderError)
            .load(Uri.parse(url), container)
    }
}