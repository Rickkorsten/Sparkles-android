package com.fhict.sparklesandroid

import android.content.Context
import android.preference.PreferenceManager

class PreferencesHelper(context: Context) {
    companion object {
        private val DID_ONBOARDING = "data.source.prefs.DID_ONBOARDING"
        private val DEVICE_ID = "data.source.prefs.DEVICE_ID"
        private val FIRSTNAME = "data.source.prefs.FIRSTNAME"
        private val USER = "data.source.prefs.USER"
        private val RELATION = "data.source.prefs.RELATION"
        private val MAIN_SPARK = "data.source.prefs.MAIN_SPARK"
        private val MAIN_SPARK_IMAGE = "data.source.prefs.MAIN_SPARK_IMAGE"
        private val MAIN_SPARK_NAME = "data.source.prefs.MAIN_SPARK_NAME"
        private val DARK_MODE = "data.source.prefs.DARK_MODE"
        private val DARK_MODE_CHANGED = "data.source.prefs.DARK_MODE_CHANGED"
        private val PASSED_RELATIONS_LIST = "data.source.prefs.PASSED_RELATIONS_LIST"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    // save onbaording
    var didOnboarding = preferences.getBoolean(DID_ONBOARDING,false)
        set(value) = preferences.edit().putBoolean(DID_ONBOARDING, value).apply()

    // save darkmode
    var darkMode = preferences.getBoolean(DARK_MODE,false)
        set(value) = preferences.edit().putBoolean(DARK_MODE, value).apply()

    // save darkmode_changed
    var darkModeChanged = preferences.getBoolean(DARK_MODE_CHANGED,false)
        set(value) = preferences.edit().putBoolean(DARK_MODE_CHANGED, value).apply()

    // save device_id
    var deviceId = preferences.getString(DEVICE_ID,"")
        set(value) = preferences.edit().putString(DEVICE_ID, value).apply()

    // save name
    var firstName = preferences.getString(FIRSTNAME,"")
        set(value) = preferences.edit().putString(FIRSTNAME, value).apply()

    // save user
    var user = preferences.getString(USER, "")
        set(value) = preferences.edit().putString(USER, value).apply()

    // save relation
    var relation = preferences.getString(RELATION, "")
        set(value) = preferences.edit().putString(RELATION, value).apply()

    // save relation
    var mainSpark = preferences.getString(MAIN_SPARK, "")
        set(value) = preferences.edit().putString(MAIN_SPARK, value).apply()

    // save relation
    var mainSparkImage = preferences.getString(MAIN_SPARK_IMAGE, "")
        set(value) = preferences.edit().putString(MAIN_SPARK_IMAGE, value).apply()

    // save relation
    var mainSparkName = preferences.getString(MAIN_SPARK_NAME, "")
        set(value) = preferences.edit().putString(MAIN_SPARK_NAME, value).apply()

    // save past_relation_list
    var passedRelationList = preferences.getString(PASSED_RELATIONS_LIST, "")
        set(value) = preferences.edit().putString(PASSED_RELATIONS_LIST, value).apply()
}
