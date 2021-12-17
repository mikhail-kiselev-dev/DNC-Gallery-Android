package com.dnc.androidgallery.features.feed.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.recycler.AdapterDelegate
import com.dnc.androidgallery.base.recycler.ItemModel
import com.dnc.androidgallery.core.extensions.loadImage
import com.dnc.androidgallery.databinding.ItemPhotoBinding
import com.dnc.androidgallery.features.feed.ui.model.FeedItemModel

class FeedAdapterDelegate(
    context: Context,
    private val clickListener: (Long, Int) -> Unit
) : AdapterDelegate(context) {

    override fun onCreateViewHolder(parent: ViewGroup) =
        FeedViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ItemModel) {
        val currentItem = item as FeedItemModel
        (viewHolder as FeedViewHolder).bind(currentItem)
        viewHolder.itemView.setOnClickListener {
            clickListener.invoke(currentItem.id, viewHolder.absoluteAdapterPosition)
        }
    }

    override fun isForViewType(item: ItemModel, position: Int) =
        item is FeedItemModel

    override fun getViewType() = R.layout.item_photo

    class FeedViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedItemModel) {
            binding.apply {
                ivPhoto.loadImage(item.image)
                tvPhoto.text = item.title
            }
        }
    }
}
