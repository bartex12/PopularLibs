package com.example.popularlibs_homrworks.model.glide

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.popularlibs_homrworks.model.network.INetworkStatus
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimage.IRoomGithubAvatarCache
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GlideImageLoader(val db: Database, val avatarCash: IRoomGithubAvatarCache,
                       val networkStatus: INetworkStatus):
    IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        var urlNew  = url  //нужна изменяемая переменная!!!
        Glide.with(container.context)
            .asBitmap() //получить в виде Bitmap
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d(TAG, "GlideImageLoader listener itemView onLoadFailed " +
                            "Error = ${e?.message}")
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //реализация кэширования аватарок из сети на диск и внесение в базу данных пути к файлу
                    networkStatus.isOnlineSingle()
                        .flatMap { isOnline ->
                            if (isOnline) {
                                //если сеть есть - кэшируем аватарки на диск
                                avatarCash.doAvatarsCache( db, resource, url)
                            } else {
                                //если сети нет - достаём аватарки из кэша
                                avatarCash.getAvatarsFromCash( db,  url)
                            }
                        }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe (
                            {urlNew =it  //получаем url в зависимости от наличия сети !!!
                                Log.d(TAG, "GlideImageLoader onResourceReady Online:  url = $url " +
                                        "*** urlNew $urlNew" )
                            },{error->
                                Log.d(TAG, "GlideImageLoader onResourceReady Online:" +
                                        " Error ${error.message} ")
                            }
                        )
                    return false
                }   // end onResourceReady
            })    //end listener
            .load(urlNew)
            .into(container)
    }
}