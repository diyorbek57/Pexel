package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cover(
    @SerializedName("urls")
    @Expose val urls: Urls
) : Serializable