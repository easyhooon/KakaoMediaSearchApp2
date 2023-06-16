package com.kenshi.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kenshi.data.source.local.SearchLocalDataSourceImpl.PreferenceKeys.SORT_MODE
import com.kenshi.data.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SearchLocalDataSource {
    private object PreferenceKeys {
        // key 로 string 을 사용하던 spf 와 다르게 type-safe 를 위해 preferencesKey 를 사용
        // 저장할 type 이 string 이기 때문에 stringPreferencesKey
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    // 저장 작업은 coroutine block 내에서 이루어짐
    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override fun getSortMode(): Flow<String> {
        // 파일에 접근하기 위해 data 메소드를 사용
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            // map 블럭 내에서 key 를 전달해서 flow 를 반환을 받음
            // 기본 값으로는 ACCURACY 값이 나오도록
            .map { prefs ->
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }
}