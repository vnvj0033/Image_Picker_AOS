package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PREFERENCE_KEY_NAME_FAVORITE_LIST
import com.yoosangyeop.imagepicker.data.preferences.PreferencesUtil
import javax.inject.Inject


class FavoriteDataSource @Inject constructor(
    private val preferencesUtil: PreferencesUtil
) {

    fun loadFavorites(): List<String> = preferencesUtil.getStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST)

    fun setFavorites(item: List<String>) {
        preferencesUtil.setStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST, item)
    }

}
