package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PreferenceUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val PREFERENCE_KEY_NAME_FAVORITE_LIST = "PREFERENCE_KEY_NAME_FAVORITE_LIST"

class FavoriteDataSource @Inject constructor(
    private val preferenceUtil: PreferenceUtil
) {

    fun loadFavorites(): Flow<List<String>> =
        flowOf(preferenceUtil.getStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST))

    fun setFavorites(item: List<String>) {
        preferenceUtil.setStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST, item)
    }

}
