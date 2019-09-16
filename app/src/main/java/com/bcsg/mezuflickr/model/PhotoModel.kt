package com.bcsg.mezuflickr.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponsePhotoList (
    val photos: PhotoList?,
    val stat: String?
)

data class PhotoList (
    val page: Int?,
    val pages: Int?,
    @SerializedName("perpage")
    val perPage: Int?,
    val total: Int?,
    val photo: List<Photo>

)

data class Photo (
    val id: String?,
    val owner: String?,
    val secret: String?,
    val server: String?,
    val farm: String?,
    val title: String?,

    @SerializedName("ispublic")
    val isPublic: Int?,
    @SerializedName("isfriend")
    val isFriend: Int?,
    @SerializedName("isfamily")
    val isFamily: Int?,
    @SerializedName("url_n")
    val imageUrl: String?,
    @SerializedName("count_comments")
    val countComments: String?,
    @SerializedName("count_faves")
    val countFaves: String?
): Serializable