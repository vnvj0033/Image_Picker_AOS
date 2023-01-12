package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PREFERENCE_KEY_NAME_HISTORY_LIST
import com.yoosangyeop.imagepicker.data.preferences.PreferencesUtil
import javax.inject.Inject

class HistoryDataSource @Inject constructor(
    private val preferencesUtil: PreferencesUtil
) {
    fun loadHistory(): List<String> = preferencesUtil.getStringList(PREFERENCE_KEY_NAME_HISTORY_LIST)

    fun setHistory(history: List<String>) {
        preferencesUtil.setStringList(PREFERENCE_KEY_NAME_HISTORY_LIST, history)
    }
}
