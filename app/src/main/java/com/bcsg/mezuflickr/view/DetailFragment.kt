package com.bcsg.mezuflickr.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.bcsg.mezuflickr.R
import com.bcsg.mezuflickr.model.Owner
import com.bcsg.mezuflickr.model.Photo
import com.bcsg.mezuflickr.model.PhotoInfo
import com.bcsg.mezuflickr.util.getProgressDrawable
import com.bcsg.mezuflickr.util.loadImage
import com.bcsg.mezuflickr.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.loadingView
import kotlinx.android.synthetic.main.item_photo.*
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var photo: Photo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title =  "Detail"
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            photo = DetailFragmentArgs.fromBundle(it).photo
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        observeViewModel()

        photo?.let {
            viewModel.fetchData(it.id!!)
        }

        photoImage.loadImage(photo.imageUrl, getProgressDrawable(photoImage.context))
    }

    fun observeViewModel() {

        viewModel.photoInfo.observe(this, Observer { photoInfo ->
            photoInfo?.let {
                loadingView.visibility = View.GONE
                render(photoInfo)
            }
        })

        viewModel.photosLoadError.observe(this, Observer { isError ->
            isError?.let {
                //listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE

                /*
                if (it) {
                    listError.visibility = View.GONE
                }

                 */
            }
        })

    }

    fun render(photoInfo: PhotoInfo) {



        photoInfo?.let {_photoInfo ->

            _photoInfo.owner?.let { _owner ->
                userImage.loadImage(_owner.getUrlImage(), getProgressDrawable(userImage.context))
                txtUsername.text = _owner.username
            }

            _photoInfo.title?.let {
                txtPhotoTitle.text = it.value
            }

            _photoInfo.description?.let {
                txtPhotoDescription.text = it.value
            }

            _photoInfo.dates?.let {
                txtPhotoDate.text = it.getPostedString()
            }

        }

    }



}
