package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photo(
    @SerializedName("id")
    @Expose val id: String = "",
    @SerializedName("description")
    @Expose val description: String = "",
    @SerializedName("width")
    @Expose val width: Int? =null,
    @SerializedName("height")
    @Expose val height: Int? =null,
    @SerializedName("likes")
    @Expose val likes: Int? =null,
    @SerializedName("urls")
    @Expose val urls: Urls
) : Serializable

