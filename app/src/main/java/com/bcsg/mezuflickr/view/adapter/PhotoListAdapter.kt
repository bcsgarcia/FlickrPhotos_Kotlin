package com.bcsg.mezuflickr.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bcsg.mezuflickr.R
import com.bcsg.mezuflickr.model.Photo
import com.bcsg.mezuflickr.util.getProgressDrawable
import com.bcsg.mezuflickr.util.loadImage
import com.bcsg.mezuflickr.view.ListFragmentDirections
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoListAdapter(val photoList: ArrayList<Photo>): RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>()  {

    fun updatePhotosList(newPhotoList: List<Photo>){
        //photoList.clear()
        photoList.addAll(newPhotoList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount() = photoList.size


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.view.title.text = photoList[position].title
        //holder.view.detail.text = photoList[position].

        holder.view.imageView.loadImage(photoList[position].imageUrl, getProgressDrawable(holder.view.imageView.context))

        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment(photoList[position]))
        }
    }

    class PhotoViewHolder(var view: View): RecyclerView.ViewHolder(view)
}