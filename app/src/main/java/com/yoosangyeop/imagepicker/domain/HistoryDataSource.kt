package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PreferenceUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val PREFERENCE_KEY_NAME_HISTORY_LIST = "PREFERENCE_KEY_NAME_HISTORY_LIST"

class HistoryDataSource @Inject constructor(
    private val preferenceUtil: PreferenceUtil
) {

    fun loadHistory(): Flow<List<String>> =
        flowOf(preferenceUtil.getStringList(PREFERENCE_KEY_NAME_HISTORY_LIST))

    fun setHistory(history: List<String>) {
        preferenceUtil.setStringList(PREFERENCE_KEY_NAME_HISTORY_LIST, history)
    }

}
