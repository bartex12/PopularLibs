package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashimage

import android.graphics.Bitmap
import android.util.Log
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashfile.IAvatarFile
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.model.room.tables.CashedImage
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class RoomGithubAvatarCache(val repoFile: IAvatarFile) :IRoomGithubAvatarCache{

    override fun doAvatarsCache(
        db: Database,
        bitmap: Bitmap?,
        url: String
    ):Single<String> {
      return  Single.fromCallable {
            repoFile.getDir() // /MyImages
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                    {
                        //делаем из информативной части url название файла
                        val subUrl = url.substringAfterLast("/")
                        val file = File(it, "/$subUrl.jpg") //создаём файл jpg
//                        Log.d(TAG, "RoomGithubAvatarCache doAvatarsCache url = " +
//                                "$url *** Путь к JPG = ${file.absolutePath}")

                        repoFile.saveJPGFile(file, bitmap)
                            .observeOn(Schedulers.computation())
                            .subscribe(
                                {//если все нормально
                                    val cashedImage =
                                        CashedImage(url = url, localPath =file.absolutePath )
                                    //запись в базу не в основном потоке!!!
                                    db.cashedImage.insert(cashedImage)
                                    //Log.d(TAG, "RoomGithubUsersCache  jpg_success ")
                                },
                                { it.message?.let { it1 ->
                                    Log.d(TAG, "RoomGithubUsersCache  jpg_error = $it1")
                                } })
                    },
                    { it.message?.let { it1 ->
                        Log.d(TAG, "RoomGithubUsersCache  Error = $it1")
                    } })
            return@fromCallable url
        }
    }

    override fun getAvatarsFromCash(db: Database, url: String): Single<String> {
        return Single.fromCallable {
            db.cashedImage.findByUrl(url).localPath
        }
    }
}