package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileImage (
    @SerializedName("small")
    @Expose val small: String = "",
    @SerializedName("medium")
    @Expose val medium:String = "",
    @SerializedName("large")
    @Expose val large: String = ""
) : Serializable