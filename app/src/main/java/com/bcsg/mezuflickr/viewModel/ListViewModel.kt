package com.bcsg.mezuflickr.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bcsg.mezuflickr.model.Photo
import com.bcsg.mezuflickr.model.ResponsePhotoList
import com.bcsg.mezuflickr.service.PhotosApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {

    private val userId = "49191827@N00"
    var page = 1

    private val photosService = PhotosApiService()
    private val disposable = CompositeDisposable()

    val photos = MutableLiveData<List<Photo>>()
    val photosLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true

        disposable.add(
            photosService.getPhotos(userId, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ResponsePhotoList>() {
                    override fun onSuccess(responsePhoto: ResponsePhotoList) {
                        //To change body of created functions use File | Settings | File Templates.
                        photos.value = responsePhoto.photos?.photo
                        page++
                        loading.value = false
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