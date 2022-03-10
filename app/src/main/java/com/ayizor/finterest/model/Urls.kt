package com.ayizor.finterest.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




 class Urls{
     @SerializedName("raw")
     @Expose
     private val raw: String? = null

     @SerializedName("full")
     @Expose
     private var full: String? = null

     @SerializedName("regular")
     @Expose
     private var regular: String? = null

     @SerializedName("small")
     @Expose
     private var small: String? = null

     @SerializedName("thumb")
     @Expose
     private var thumb: String? = null

     fun getFull(): String? {
         return full
     }

     fun setFull(full: String?) {
         this.full = full
     }

     fun getRegular(): String? {
         return regular
     }

     fun setRegular(regular: String?) {
         this.regular = regular
     }

     fun getSmall(): String? {
         return small
     }

     fun setSmall(small: String?) {
         this.small = small
     }

     fun getThumb(): String? {
         return thumb
     }

     fun setThumb(thumb: String?) {
         this.thumb = thumb
     }
}
