package com.example.popularlibs_homrworks.model.glide

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader:
    IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}