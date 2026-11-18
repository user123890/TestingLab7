
package com.example.testinglab7

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TestUser::class],
    version = 1,
    exportSchema = false
)
abstract class TestDatabase : RoomDatabase() {

    abstract fun testUserDao(): TestUserDao
}
