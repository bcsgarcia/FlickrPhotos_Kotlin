package com.bcsg.mezuflickr.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bcsg.mezuflickr.R
import com.bcsg.mezuflickr.view.adapter.PhotoListAdapter
import com.bcsg.mezuflickr.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val photosListAdapter = PhotoListAdapter(arrayListOf())

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()


    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(photosList.context)

        photosList.apply {
            layoutManager = linearLayoutManager
            adapter = photosListAdapter
        }

        setRecyclerViewScrollListener()


        refreshLayout.setOnRefreshListener {
            photosList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()

        if (viewModel.page == 1) {
            viewModel.refresh()
        }

        photosList.refreshDrawableState()

    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView?.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.refresh()
                }
            }
        }
        photosList.addOnScrollListener(scrollListener)
    }

    fun observeViewModel() {

        viewModel.photos.observe(this, Observer {photos ->
            photos?.let {
                photosListAdapter.updatePhotosList(photos)
                photosList.visibility = View.VISIBLE
                loadingView.visibility = View.GONE
            }
        })

        viewModel.photosLoadError.observe(this, Observer { isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    photosList.visibility = View.GONE
                }
            }
        })
    }

}
