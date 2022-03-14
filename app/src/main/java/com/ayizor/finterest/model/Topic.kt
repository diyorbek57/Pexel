package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Topic(
    @SerializedName("id")
    @Expose val id: String = "",
    @SerializedName("title")
    @Expose val title: String = "",
    @SerializedName("cover_photo")
    @Expose val cover_photo: Cover
) : Serializable