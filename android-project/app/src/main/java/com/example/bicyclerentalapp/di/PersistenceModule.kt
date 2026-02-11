package com.example.bicyclerentalapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bicyclerentalapp.R
import com.example.bicyclerentalapp.model.ParkingZone
import com.example.bicyclerentalapp.model.Station
import com.example.bicyclerentalapp.persistence.AppDatabase
import com.example.bicyclerentalapp.persistence.BikeDao
import com.example.bicyclerentalapp.persistence.BikeRentalDao
import com.example.bicyclerentalapp.persistence.BikeReturnReportDao
import com.example.bicyclerentalapp.persistence.ParkingZoneDao
import com.example.bicyclerentalapp.persistence.StationDao
import com.example.bicyclerentalapp.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule{

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        lateinit var database: AppDatabase

        database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            application.getString(R.string.database)
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {

                        val stationDao = database.stationDao()
                        // belgrade
                        stationDao.insertStation(Station(1,"Ušće Shopping Center",44.8152,20.4365,10,10,10,10,true))
                        stationDao.insertStation(Station(2,"Štark Arena North",44.8145,20.4202,15,5,15,5,true, 1.70,1.20))
                        stationDao.insertStation(Station(3,"Hotel Jugoslavija",44.8305,20.4184,20,0,20,0,false))
                        stationDao.insertStation(Station(4,"Ada Ciganlija",44.7895,20.4032,20,0,20,0,false))
                        stationDao.insertStation(Station(5,"Savski Trg",44.8078,20.4565,30,0,30,0,false))
                        stationDao.insertStation(Station(6,"Tasmajdan",44.811173,20.468600,30,0,30,0,false))

                        // novi sad
                        stationDao.insertStation(Station(7,"Trg Slobode",45.2551,19.8451,20,10,20,10,true))
                        stationDao.insertStation(Station(8,"Promenada",45.2464,19.8415,20,0,20,0,false))
                        stationDao.insertStation(Station(9,"Petrovaradin",45.2525,19.8605,30,0,30,0,false,1.80,1.60))


                        val parkingDao = database.parkingZoneDao()

                        // Usce
                        parkingDao.insertParkingZone(
                            ParkingZone(1, "Ušće Park Zona A", 44.8160, 20.4358, 80.0, false)
                        )
                        parkingDao.insertParkingZone(
                            ParkingZone(2, "Ušće Charging Zona", 44.8148, 20.4372, 60.0, true)
                        )

                        // Stark Arena
                        parkingDao.insertParkingZone(
                            ParkingZone(3, "Arena Parking A", 44.8150, 20.4195, 70.0, false)
                        )
                        parkingDao.insertParkingZone(
                            ParkingZone(4, "Arena Charging Hub", 44.8138, 20.4210, 60.0, true)
                        )

                        // Hotel Jugoslavija
                        parkingDao.insertParkingZone(
                            ParkingZone(5, "Kej Zona", 44.8312, 20.4175, 75.0, false)
                        )

                        // Ada Ciganlija
                        parkingDao.insertParkingZone(
                            ParkingZone(6, "Ada Glavna Zona", 44.7900, 20.4025, 100.0, false)
                        )

                        // Savski Trg
                        parkingDao.insertParkingZone(
                            ParkingZone(7, "Savski Trg Parking", 44.8085, 20.4575, 70.0, false)
                        )
                        parkingDao.insertParkingZone(
                            ParkingZone(8, "Savski Charging Zona", 44.8069, 20.4558, 60.0, true)
                        )

                        // Trg Slobode
                        parkingDao.insertParkingZone(
                            ParkingZone(9, "Centar Zona A", 45.2558, 19.8445, 70.0, false)
                        )
                        parkingDao.insertParkingZone(
                            ParkingZone(10, "Centar Charging Hub", 45.2545, 19.8460, 60.0, true)
                        )

                        // Promenada
                        parkingDao.insertParkingZone(
                            ParkingZone(11, "Promenada Parking", 45.2470, 19.8422, 80.0, false)
                        )

                        // Petrovaradin
                        parkingDao.insertParkingZone(
                            ParkingZone(12, "Petrovaradin Zona", 45.2535, 19.8615, 90.0, false)
                        )
                        // tehnicki fakulteti
                        parkingDao.insertParkingZone(
                            ParkingZone(13, "Tehnicki fakulteti", 44.805438, 20.476110, 80.0, false)
                        )
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()

        return database
    }
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideStationDao(appDatabase: AppDatabase): StationDao = appDatabase.stationDao()

    @Provides
    @Singleton
    fun provideBikeDao(appDatabase: AppDatabase): BikeDao = appDatabase.bikeDao()

    @Provides
    @Singleton
    fun provideBikeRentalDao(appDatabase: AppDatabase): BikeRentalDao = appDatabase.bikeRentalDao()

    @Provides
    @Singleton
    fun provideBikeReturnReportDao(appDatabase: AppDatabase): BikeReturnReportDao= appDatabase.bikeReturnReport()

    @Provides
    @Singleton
    fun provideParkingZoneDao(appDatabase: AppDatabase) : ParkingZoneDao = appDatabase.parkingZoneDao()
}