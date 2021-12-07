package com.dnc.androidgallery.core.providers

interface PreferencesProvider {

    fun saveString(key: String, value: String?)

    fun getString(key: String): String?

    fun saveInt(key: String, value: Int)

    fun getInt(key: String): Int

    fun saveLong(key: String, value: Long)

    fun getLong(key: String): Long

    fun saveBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean

    fun getBooleanWithDefaultValue(key: String, default: Boolean): Boolean

    fun removeValue(key: String)

    fun clearAll()

    fun clear(key: String)
}
