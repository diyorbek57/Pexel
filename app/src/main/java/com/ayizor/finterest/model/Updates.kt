package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Updates(
    @SerializedName("photos")
    @Expose val photos: Photo,
    @SerializedName("urls")
    @Expose val urls: Urls,
    @SerializedName("user")
    @Expose val user: User
) : Serializable
