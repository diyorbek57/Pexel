package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Download(
    @SerializedName("url")
    @Expose val url: String = ""
) : Serializable








