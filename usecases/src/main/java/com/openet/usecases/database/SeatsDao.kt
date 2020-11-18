package com.openet.usecases.database

import androidx.room.Dao
import androidx.room.Query
import com.openet.entities.SeatDB

@Dao
interface SeatsDao {

    @Query("select * from SeatDB ")
    fun queryAll(): List<SeatDB>
}