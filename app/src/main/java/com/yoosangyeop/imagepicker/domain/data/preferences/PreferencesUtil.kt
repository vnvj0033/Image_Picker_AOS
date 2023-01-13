package com.yoosangyeop.imagepicker.domain.data.preferences

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray


class PreferencesUtil(context: Context, name: String) {
    private val prefs: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String = prefs.getString(key, defValue).toString()
    fun setString(key: String, str: String) = prefs.edit().putString(key, str).apply()

    fun getBoolean(key: String, defValue: Boolean): Boolean = prefs.getBoolean(key, defValue)
    fun setBoolean(key: String, value: Boolean) = prefs.edit().putBoolean(key, value).apply()

    fun getStringList(key: String): List<String> {
        val string = getString(key, "")
        val list = ArrayList<String>()
        runCatching {
            val jsonArray = JSONArray(string)

            for (i in 0 until jsonArray.length()) {
                val url = jsonArray.optString(i)
                list.add(url)
            }
        }
        return list
    }

    fun setStringList(key: String, value: List<String>) {
        val jsonArray = JSONArray()
        value.forEach {
            jsonArray.put(it)
        }
        setString(key, jsonArray.toString())
    }
}