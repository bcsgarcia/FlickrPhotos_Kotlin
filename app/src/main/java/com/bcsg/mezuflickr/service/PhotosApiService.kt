package com.bcsg.mezuflickr.service

import com.bcsg.mezuflickr.model.ApiMethod
import com.bcsg.mezuflickr.model.Photo
import com.bcsg.mezuflickr.model.ResponsePhotoDetail
import com.bcsg.mezuflickr.model.ResponsePhotoList
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PhotosApiService {

    private val BASE_URL = "https://api.flickr.com/services/"
    private val API_KEY = "7ec9d191b3831b20d5b7695d49db8946"
    private val FORMAT = "json"
    private val NO_JSON_CALLBACK = 1
    private val PER_PAGE = 20
    private val EXTRAS = "url_n,count_comments,count_faves"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PhotosApi::class.java)

    fun getPhotos(userId: String, page: Int): Single<ResponsePhotoList> {
        return api.getPhotos(userId, page, ApiMethod.PUBLIC_PHOTOS.getMethod(), API_KEY, FORMAT, NO_JSON_CALLBACK, PER_PAGE, EXTRAS)
    }

    fun getPhotoInfo(id: String): Single<ResponsePhotoDetail> {
        return api.getPhotoInfo(id, ApiMethod.INFO.getMethod(), API_KEY, FORMAT, NO_JSON_CALLBACK)
    }

}