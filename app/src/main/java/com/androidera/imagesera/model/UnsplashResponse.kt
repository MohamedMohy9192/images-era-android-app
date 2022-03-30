package com.androidera.imagesera.model

import com.google.gson.annotations.SerializedName

data class UnsplashResponse(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val unsplashPhotos: List<UnsplashPhoto>
)

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

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class User(
    @SerializedName("first_name")
    val firstName: String,
    val id: String,
    @SerializedName("last_name")
    val lastName: String,
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    val username: String
)

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)


