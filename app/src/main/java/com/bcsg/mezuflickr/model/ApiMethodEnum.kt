package com.bcsg.mezuflickr.model

interface IApiMethod {
    fun getMethod(): String
}

enum class ApiMethod: IApiMethod {
    PUBLIC_PHOTOS {
        override fun getMethod() = "flickr.people.getPublicPhotos"
    },
    INFO {
        override fun getMethod() = "flickr.photos.getInfo"
    }
}