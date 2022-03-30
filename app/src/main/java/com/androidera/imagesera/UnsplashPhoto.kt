package com.androidera.imagesera

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    @SerializedName("created_at")
    val createdAt: String,
    val description: String,
    val height: Int,
    val id: String,
    val likes: Int,
    val urls: Urls,
    val user: User,
    val width: Int
)