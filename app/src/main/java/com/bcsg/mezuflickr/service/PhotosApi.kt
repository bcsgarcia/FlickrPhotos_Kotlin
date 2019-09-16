package com.bcsg.mezuflickr.service

import com.bcsg.mezuflickr.model.ResponsePhotoDetail
import com.bcsg.mezuflickr.model.ResponsePhotoList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("rest/")
    fun getPhotos(@Query("user_id") userId: String,
                  @Query("page") page: Int,
                  @Query("method") method: String,
                  @Query("api_key") apiKey: String,
                  @Query("format") format: String,
                  @Query("nojsoncallback") noJsonCallback: Int,
                  @Query("per_page") perPage: Int,
                  @Query("extras") extras: String): Single<ResponsePhotoList>

    @GET("rest/")
    fun getPhotoInfo( @Query("photo_id") photoId: String,
                      @Query("method") method: String,
                      @Query("api_key") apiKey: String,
                      @Query("format") format: String,
                      @Query("nojsoncallback") noJsonCallback: Int): Single<ResponsePhotoDetail>
}