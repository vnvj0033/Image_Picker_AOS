package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PREFERENCE_KEY_NAME_FAVORITE_LIST
import com.yoosangyeop.imagepicker.data.preferences.PreferencesUtil
import javax.inject.Inject


class FavoriteDataSource @Inject constructor(
    private val preferencesUtil: PreferencesUtil
) {
    private var favorite = emptyList<String>()

    fun loadFavorites(): List<String> {
        if (favorite.isEmpty()) {
            favorite = preferencesUtil.getStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST)
        }

        return favorite
    }

    fun setFavorites(item: List<String>) {
        favorite = item
        preferencesUtil.setStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST, item)
    }

}
