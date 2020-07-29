package com.am_development.usecases.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.am_development.entities.SeatDB
import com.am_development.usecases.applicationLiveData
import com.am_development.usecases.getApplication

val cafDatabase by lazy {
    initializeDatabase(applicationLiveData.getApplication())
}

@Database(
    entities = [SeatDB::class],
    version = 1,
    exportSchema = false
)

abstract class CafDatabase : RoomDatabase() {
    abstract val seatsDao: SeatsDao

}