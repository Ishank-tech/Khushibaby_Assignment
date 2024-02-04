package com.example.tmdb.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.data.models.PopularMovieRemoteKey

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM `remote-keys` WHERE id=:id ")
    suspend fun getRemoteKeys(id: String): PopularMovieRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemoteKey(popularMovieRemoteKey: PopularMovieRemoteKey)

    @Query("DELETE FROM `remote-keys`")
    suspend fun deleteAllRemoteKeys()
}