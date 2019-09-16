package com.bcsg.mezuflickr.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bcsg.mezuflickr.model.Photo
import com.bcsg.mezuflickr.model.PhotoInfo
import com.bcsg.mezuflickr.model.ResponsePhotoDetail
import com.bcsg.mezuflickr.model.ResponsePhotoList
import com.bcsg.mezuflickr.service.PhotosApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel: ViewModel() {

    private val photosService = PhotosApiService()
    private val disposable = CompositeDisposable()

    val photoInfo = MutableLiveData<PhotoInfo>()
    val photosLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun fetchData(photoId: String) {
        loading.value = true
        disposable.add(
            photosService.getPhotoInfo(photoId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ResponsePhotoDetail>() {
                    override fun onSuccess(responsePhotoDetail: ResponsePhotoDetail) {
                        photoInfo.value = responsePhotoDetail?.photo
                    }

                    override fun onError(e: Throwable) {
                        photosLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

}