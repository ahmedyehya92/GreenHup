package com.openet.usecases.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.openet.entities.SeatDB
import com.openet.usecases.applicationLiveData
import com.openet.usecases.getApplication

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