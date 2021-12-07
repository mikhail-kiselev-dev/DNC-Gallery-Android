package com.dnc.androidgallery.core.providers

import android.content.Context

@Suppress("unused")
class PreferencesProviderImpl(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCES = "app_preferences"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? = sharedPreferences.getString(key, null)

    override fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)

    override fun saveLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String): Long = sharedPreferences.getLong(key, 0)

    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    override fun getBooleanWithDefaultValue(key: String, default: Boolean): Boolean =
        sharedPreferences.getBoolean(key, default)

    override fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    override fun clear(key: String) {
        removeValue(key)
    }
}
