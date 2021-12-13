package com.dnc.androidgallery.features.settings.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.recycler.AdapterDelegate
import com.dnc.androidgallery.base.recycler.ItemModel
import com.dnc.androidgallery.core.extensions.loadImage
import com.dnc.androidgallery.databinding.ItemSettingBinding
import com.dnc.androidgallery.features.settings.ui.model.SettingItemModel

class SettingsAdapterDelegate(
    context: Context,
    private val clickListener: (SettingItemModel) -> Unit
) : AdapterDelegate(context) {

    override fun onCreateViewHolder(parent: ViewGroup) =
        SettingsViewHolder(
            ItemSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ItemModel) {
        val currentItem = item as SettingItemModel
        (viewHolder as SettingsViewHolder).bind(currentItem)
        viewHolder.itemView.setOnClickListener {
            clickListener.invoke(currentItem)
        }
    }

    override fun isForViewType(item: ItemModel, position: Int): Boolean =
        item is SettingItemModel

    override fun getViewType(): Int = R.layout.item_setting

    class SettingsViewHolder(
        private val binding: ItemSettingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SettingItemModel) {
            binding.apply {
                ivSetting.loadImage(item.image)
                tvSetting.text = item.title
            }
        }
    }
}
