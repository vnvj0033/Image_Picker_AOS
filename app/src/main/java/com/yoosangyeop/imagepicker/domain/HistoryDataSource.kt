package com.yoosangyeop.imagepicker.domain

import com.yoosangyeop.imagepicker.data.preferences.PREFERENCE_KEY_NAME_HISTORY_LIST
import com.yoosangyeop.imagepicker.data.preferences.PreferencesUtil
import javax.inject.Inject

class HistoryDataSource @Inject constructor(
    private val preferencesUtil: PreferencesUtil
) {
    private var history = emptyList<String>()

    fun loadHistory(): List<String> {
        if (history.isEmpty()) {
            history = preferencesUtil.getStringList(PREFERENCE_KEY_NAME_HISTORY_LIST)
        }
        return history
    }

    fun setHistory(item: List<String>) {
        history = item
        preferencesUtil.setStringList(PREFERENCE_KEY_NAME_HISTORY_LIST, item)
    }

}
