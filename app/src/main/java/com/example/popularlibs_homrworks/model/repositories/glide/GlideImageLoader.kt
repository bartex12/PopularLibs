package com.example.popularlibs_homrworks.model.repositories.glide

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimageroom.IRoomGithubAvatarCache
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.view.main.TAG

class GlideImageLoader(val db: Database, val avatarCash: IRoomGithubAvatarCache):
    IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //todo если не удаётся загрузить аватарку юзера из сети
                    //возвращаем картинку конкретного юзера с диска читая url из базы
                    Log.d(TAG, "GlideImageLoader listener itemView onLoadFailed = false")
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //Log.d(TAG, "GlideImageLoader Online url = $url ")
                    //todo нужно сделать так же как и со списком - через сабжект и обзервербал
                    avatarCash.doAvatarsCache( db, resource, url)

//                    //реализация кэширования аватарок из сети на диск и внесение в базу данных пути к файлу
//                    //todo return false значит использовать встроенный cash
//                    AndroidNetworkStatus(App.instance).isOnline()
//                        .flatMap {isOnline->
//                            if (isOnline){
//                                Log.d(TAG, "GlideImageLoader Online url = $url ")
//                                avatarCash.doAvatarsCache( db, resource, url)
//                                Observable.just(1)
//                            }else{
//                              Log.d(TAG, "GlideImageLoader offLine url = $url ")
//                              val localPath =  db.cashedImage.findByUrl(url).localPath
//                              Log.d(TAG, "GlideImageLoader offLine localPath = $localPath ")
//                                Observable.just(1)
//                            }
//                        }.subscribeOn(Schedulers.io())
//                    //Log.d(TAG, "GlideImageLoader listener itemView  onResourceReady = false")
                    return false
                }
            })
            .into(container)
    }
}