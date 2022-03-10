package com.ayizor.finterest.api

import android.app.appsearch.SearchResults
import com.ayizor.finterest.model.Download
import com.ayizor.finterest.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {
    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String,
        @Query("w") width: Int,
        @Query("h") height: Int
    ): Call<Photo>

    @GET("photos")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int?,
        @Query("order_by") orderBy: String
    ): Call<List<Photo>>

    @GET("photos/curated")
    fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<Photo>>

    @GET("photos/random")
    fun getRandomPhoto(
        @Query("collections") collections: String,
        @Query("featured") featured: Boolean,
        @Query("username") username: String,
        @Query("query") query: String,
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("orientation") orientation: String
    ): Call<Photo>

    @GET("photos/random")
    fun getRandomPhotos(
        @Query("collections") collections: String,
        @Query("featured") featured: Boolean,
        @Query("username") username: String,
        @Query("query") query: String,
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("orientation") orientation: String,
        @Query("count") count: Int
    ): Call<List<Photo>>

    @GET("photos/{id}/download")
    fun getPhotoDownloadLink(@Path("id") id: String): Call<Download>

    @GET("search/photos")
    fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("orientation") orientation: String
    ): Call<SearchResults>
}