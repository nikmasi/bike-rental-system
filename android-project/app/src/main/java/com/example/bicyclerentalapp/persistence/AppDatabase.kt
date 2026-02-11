package com.example.bicyclerentalapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bicyclerentalapp.model.User
import com.example.bicyclerentalapp.model.Bike
import com.example.bicyclerentalapp.model.BikeRental
import com.example.bicyclerentalapp.model.BikeReturnReport
import com.example.bicyclerentalapp.model.ParkingZone
import com.example.bicyclerentalapp.model.Station

@Database(
    entities = [User::class, Bike::class, BikeRental::class, BikeReturnReport::class, Station::class, ParkingZone::class],
    version=1 , exportSchema =true
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao

    abstract fun stationDao(): StationDao
    abstract fun bikeDao(): BikeDao
    abstract fun bikeRentalDao(): BikeRentalDao
    abstract fun bikeReturnReport(): BikeReturnReportDao
    abstract fun parkingZoneDao(): ParkingZoneDao
}