package com.example.foodmarket.ui.home.newtaste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmarket.R
import com.example.foodmarket.model.dummy.HomeVerticalModel
import com.example.foodmarket.model.response.home.Data
import com.example.foodmarket.utils.Helpers.formatPrice

class HomeNewStateAdapter(private val listData : List<Data>,private val itemAdapterCallback : ItemAdapterCallback) :  RecyclerView.Adapter<HomeNewStateAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home_vertical,parent,false)
        return ViewHolder(view )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount() = listData.size

    class ViewHolder (itemView:View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView
        var tvPrice: TextView
        var ratingBar: RatingBar

        init {
            // Define click listener for the ViewHolder's View.
            imageView = itemView.findViewById(R.id.ivPoster)
            textView = itemView.findViewById(R.id.tvTitle)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            ratingBar = itemView.findViewById(R.id.rbFood)
        }
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                textView.text = data.name
                ratingBar.rating = data.rate?.toFloat() ?: 0f
                tvPrice.formatPrice(data.price.toString())
//                 this is library for image sourco, you have to download this library in gradle
                Glide.with(context)
                    .load(data.picturePath)
                    .into(imageView)

                itemView.setOnClickListener { itemAdapterCallback.onClick(it, data) }
            }
        }
    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data: Data)
    }


}