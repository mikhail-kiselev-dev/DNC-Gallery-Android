package com.dnc.androidgallery.features.dashboard.ui.navbar

import com.dnc.androidgallery.R

@Suppress("unused")
enum class BottomItem(
    val rootId: Int,
    val viewId: Int,
    val iconResDefault: Int,
    var iconResActive: Int,
    val title: Int,
    val colorTitleDefault: Int,
    var colorTitleActive: Int,
) {
    TOP(
        1,
        R.id.ivTop,
        R.drawable.ic_top_grey,
        R.drawable.ic_top_selected,
        R.string.top_pictures,
        R.color.grey,
        R.color.secondary
    ),
    RECENT(
        2,
        R.id.ivRecent,
        R.drawable.ic_recent_grey,
        R.drawable.ic_recent_selected,
        R.string.recent_pictures,
        R.color.grey,
        R.color.secondary
    ),
    SETTINGS(
        3,
        R.id.ivSettings,
        R.drawable.ic_settings_grey,
        R.drawable.ic_settings_selected,
        R.string.settings,
        R.color.grey,
        R.color.secondary
    );

    companion object {
        fun findByViewID(viewId: Int): BottomItem =
            values().firstOrNull { it.viewId == viewId } ?: TOP
    }
}
