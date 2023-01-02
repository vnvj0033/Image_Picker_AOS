package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PREFERENCE_KEY_NAME_FAVORITE_LIST
import com.yoosangyeop.imagepicker.data.preferences.PreferencesUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject



class FavoriteDataSource @Inject constructor(
    private val preferencesUtil: PreferencesUtil
) {

    fun loadFavorites(): Flow<List<String>> =
        flowOf(preferencesUtil.getStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST))

    fun setFavorites(item: List<String>) {
        preferencesUtil.setStringList(PREFERENCE_KEY_NAME_FAVORITE_LIST, item)
    }

}
