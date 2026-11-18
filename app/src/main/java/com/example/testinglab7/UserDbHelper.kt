
package com.example.testinglab7

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDbHelper(context: Context) :
    SQLiteOpenHelper(context, "testing_lab.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE
            )
            """.trimIndent()
        )

        insertUser(db, "Мирас", "miras@gmail.com")
        insertUser(db, "Асилбек", "asilbek@gmail.com")
        insertUser(db, "Денис", "denis@gmail.com")
    }

    private fun insertUser(
        db: SQLiteDatabase,
        name: String,
        email: String
    ) {
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
        }

        db.insertWithOnConflict(
            "users",
            null,
            values,
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun addUser(name: String, email: String) {

        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
        }

        writableDatabase.insertWithOnConflict(
            "users",
            null,
            values,
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun getAllUsers(): String {

        val result = StringBuilder()

        readableDatabase.rawQuery(
            "SELECT id, name, email FROM users ORDER BY id",
            null
        ).use { cursor ->

            while (cursor.moveToNext()) {

                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val email = cursor.getString(2)

                result.append("$id. $name — $email\n")
            }
        }

        return if (result.isEmpty()) {
            "Записей нет"
        } else {
            result.toString()
        }
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Обновление структуры базы данных.
    }
}
