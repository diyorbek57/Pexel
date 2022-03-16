package com.ayizor.finterest.api

import android.app.appsearch.SearchResults
import com.ayizor.finterest.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {
    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String
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

    @GET("topics")
    fun getTopics(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int?,
        @Query("order_by") orderBy: String
    ): Call<List<Topic>>

    @GET("topics/{id}/photos")
    fun getTopictPhotos(
        @Path("id") id: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int?,
        @Query("order_by") orderBy: String
    ): Call<List<Topic>>

    @GET("photos/{id}/related")
    fun getRelatedPhotos(
        @Path("id") id: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int?
    ): Call<RelatedPhotos>

    @GET("search/photos")
    fun getSearchPhoto(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<Explore>


    @GET("search/users")
    fun getSearchProfile(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<ResultProfiles>

}