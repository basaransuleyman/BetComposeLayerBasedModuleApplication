package com.betapp.data.persistence

import androidx.room.*

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches")
    fun getAllMatches(): List<MatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatch(match: MatchEntity)

    @Update
    fun updateMatch(match: MatchEntity)

    @Query("SELECT * FROM matches WHERE isFavorite = 1")
    fun getFavoriteMatches(): List<MatchEntity>
}
