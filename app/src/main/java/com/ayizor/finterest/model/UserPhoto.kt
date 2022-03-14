package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserPhoto(
    @SerializedName("id")
    @Expose val id: String,
    @SerializedName("created_at")
    @Expose val created_at: String,
    @SerializedName("updated_at")
    @Expose val updated_at: String,
    @SerializedName("blur_hash")
    @Expose val blur_hash: String,
    @SerializedName("urls")
    @Expose val urls: Urls
)
