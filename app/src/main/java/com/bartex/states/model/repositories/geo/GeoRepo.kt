package com.bartex.states.model.repositories.geo

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.bartex.states.App
import com.bartex.states.model.entity.state.State

class GeoRepo(val app: App):IGeoRepo {

    override fun sendGeoIntent(state: State?) {
        var zoom = 0
        state?.area?. let{
            if (it<10f){
                zoom = 13
            }else if (it in 10f..1000f){
                zoom = 11
            }else if (it in 1000f..30000f){
                zoom = 9
            }else if (it in 1000f..100000f) {
                zoom = 7
            }else if (it in 100000f..1000000f){
                zoom = 5
            }else if (it in 1000000f..5000000f){
                zoom = 3
            }else{
                zoom = 1
            }
        }
        val geoCoord = String.format("geo:%s,%s?z=%s",
            state?.latlng?.get(0).toString(), state?.latlng?.get(1).toString(), zoom.toString())

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoCoord))
        // You could specify package for use the GoogleMaps app, only
        val packageManager: PackageManager = app.getPackageManager()
        if (isPackageInstalled("com.google.android.apps.maps", packageManager)) {
            intent.setPackage("com.google.android.apps.maps")
        }
        app.startActivity(intent)
    }

    private fun isPackageInstalled(packageName: String,packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}