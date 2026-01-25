package com.example.bicyclerentalapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bicyclerentalapp.model.User
import com.example.bicyclerentalapp.model.Bike
import com.example.bicyclerentalapp.model.BikeRental
import com.example.bicyclerentalapp.model.BikeReturnReport

@Database(
    entities = [User::class, Bike::class, BikeRental::class, BikeReturnReport::class],
    version=1 , exportSchema =true
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun bikeDao(): BikeDao
    abstract fun bikeRentalDao(): BikeRentalDao
    abstract fun bikeReturnReport(): BikeReturnReportDao
}