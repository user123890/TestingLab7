
package com.example.testinglab7

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class RobolectricTest {

    private val context: Context
        get() = RuntimeEnvironment.getApplication()

    @Test
    fun sharedPreferencesSaveData() {
        val preferences = context.getSharedPreferences(
            "robolectric_test_save",
            Context.MODE_PRIVATE
        )

        preferences.edit().clear().commit()
        preferences.edit().putString("username", "Miras").commit()

        assertEquals(
            "Miras",
            preferences.getString("username", null)
        )
    }

    @Test
    fun sharedPreferencesReadData() {
        val preferences = context.getSharedPreferences(
            "robolectric_test_read",
            Context.MODE_PRIVATE
        )

        preferences.edit().clear().commit()
        preferences.edit().putString("username", "Miras").commit()

        val username = preferences.getString("username", null)

        assertNotNull(username)
        assertEquals("Miras", username)
    }

    @Test
    fun sharedPreferencesClearData() {
        val preferences = context.getSharedPreferences(
            "robolectric_test_clear",
            Context.MODE_PRIVATE
        )

        // Удаляем старые данные перед тестом
        preferences.edit().clear().commit()

        // Записываем тестовое значение
        preferences.edit()
            .putString("username", "Miras")
            .commit()

        assertEquals(
            "Miras",
            preferences.getString("username", null)
        )

        // Очищаем хранилище
        val result = preferences.edit().clear().commit()

        assertTrue(result)
        assertNull(preferences.getString("username", null))
    }

    @Test
    fun sharedPreferencesStoreInteger() {
        val preferences = context.getSharedPreferences(
            "robolectric_test_integer",
            Context.MODE_PRIVATE
        )

        preferences.edit().clear().commit()
        preferences.edit().putInt("age", 20).commit()

        assertEquals(20, preferences.getInt("age", 0))
    }

    @Test
    fun createIntent() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://example.com")
        )

        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(
            "https://example.com",
            intent.data.toString()
        )
    }
}
