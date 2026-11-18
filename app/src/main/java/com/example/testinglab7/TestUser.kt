
package com.example.testinglab7

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_users")
data class TestUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int
)
