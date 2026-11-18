
package com.example.testinglab7

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface TestUserDao {

    @Insert
    suspend fun insert(user: TestUser)

    @Query("SELECT * FROM test_users")
    suspend fun getAllUsers(): List<TestUser>

    @Query("SELECT * FROM test_users WHERE id = :userId")
    suspend fun getUserById(userId: Int): TestUser?

    @Delete
    suspend fun delete(user: TestUser)

    @Query("DELETE FROM test_users")
    suspend fun deleteAll()
}
