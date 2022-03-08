package com.ayizor.finterest

import android.app.Application
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker.init

class FinterestApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        UnsplashPhotoPicker.init(this,
            "WcWDIPIQCDHc6Hhj4PmSW8wqhJArZrG2m5U6mn-9Gps",
            "WjHezNKhA6Q-jUQlaKAO3smXAmteoEKrQDTcLtw3FD8" )

    }
}