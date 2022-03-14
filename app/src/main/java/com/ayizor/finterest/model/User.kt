package com.ayizor.finterest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    @Expose val id: String = "",
    @SerializedName("username")
    @Expose val username: String = "",
    @SerializedName("name")
    @Expose val name: String = "",
    @SerializedName("first_name")
    @Expose val first_name: String,
    @SerializedName("last_name")
    @Expose val last_name: String? = null,
    @SerializedName("portfolio_url")
    @Expose val urls: String = "",

    @SerializedName("bio")
    @Expose val bio: String = "",
    @SerializedName("location")
    @Expose val location: String = "",
    @SerializedName("total_likes")
    @Expose val total_likes: Int? = null,
    @SerializedName("total_photos")
    @Expose val total_photos: Int? = null,
    @SerializedName("total_collections")
    @Expose val total_collections: Int? = null,
    @SerializedName("instagram_username")
    @Expose val instagram_username: String = "",
    @SerializedName("twitter_username")
    @Expose val twitter_username: String = "",
    @SerializedName("profile_image")
    @Expose val profile_image: ProfileImage,
    val photos: ArrayList<UserPhoto>
) : Serializable
