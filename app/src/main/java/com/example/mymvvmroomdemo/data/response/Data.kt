package com.mindorks.example.paging3.data.response

import androidx.lifecycle.LiveData
import androidx.room.*
import com.squareup.moshi.Json
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "my_dao_table",
)
data class Data(
    @PrimaryKey @NotNull @Json(name = "id")
    val id: Int,
    @Json(name = "avatar")
    val avatar: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String
)

@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articlesItem: List<Data>)

    @Query("DELETE FROM my_dao_table")
    suspend fun deleteAll()

    @Query("SELECT * from my_dao_table WHERE id = :id")
    fun getSelectedUser(id: Int): LiveData<List<Data>>

    @Query("SELECT * from my_dao_table")
    fun getAllUser(): LiveData<List<Data>>

}

