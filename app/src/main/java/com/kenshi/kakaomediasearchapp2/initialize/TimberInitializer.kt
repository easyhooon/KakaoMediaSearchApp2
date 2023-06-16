package com.kenshi.kakaomediasearchapp2.initialize

import android.content.Context
import androidx.startup.Initializer
import com.kenshi.kakaomediasearchapp2.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}