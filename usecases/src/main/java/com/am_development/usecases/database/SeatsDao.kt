package com.am_development.usecases.database

import androidx.room.Dao
import androidx.room.Query
import com.am_development.entities.SeatDB

@Dao
interface SeatsDao {

    @Query("select * from SeatDB ")
    fun queryAll(): List<SeatDB>
}