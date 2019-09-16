package com.bcsg.mezuflickr.model

import com.google.gson.annotations.SerializedName
import java.io.FileDescriptor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

data class ResponsePhotoDetail (
    val photo: PhotoInfo?,
    val stat: String?
)

data class PhotoInfo (
    val id: String?,
    val secret: String?,
    val server: String?,
    val farm: String?,
    val owner: Owner?,
    val title: ObjKeyValue?,
    val description: ObjKeyValue,
    val dates: Dates?
)

data class Owner(
    val nsid: String?,
    val username: String?,
    val location: String?,
    @SerializedName("iconserver")
    val iconServer: String?,
    @SerializedName("iconfarm")
    val iconFarm: String?,
    @SerializedName("path_alias")
    val pathAlias: String
) {
    fun getUrlImage(): String {
        return "https://farm$iconFarm.staticflickr.com/$iconServer/buddyicons/$nsid.jpg"
    }
}

data class ObjKeyValue (
    @SerializedName("_content")
    val value: String?
)

data class Dates(
    val posted: Long?
) {
    fun getPostedString(): String {
        var str = ""
        try {
            posted?.let { str = Date(it * 1000).toString() }
        } catch (e: Exception) {
            return e.toString()
        }
        return str
    }
}