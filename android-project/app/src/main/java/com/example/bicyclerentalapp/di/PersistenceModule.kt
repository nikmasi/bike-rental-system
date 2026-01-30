package com.example.bicyclerentalapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bicyclerentalapp.R
import com.example.bicyclerentalapp.model.Bike
import com.example.bicyclerentalapp.persistence.AppDatabase
import com.example.bicyclerentalapp.persistence.BikeDao
import com.example.bicyclerentalapp.persistence.BikeRentalDao
import com.example.bicyclerentalapp.persistence.BikeReturnReportDao
import com.example.bicyclerentalapp.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule{

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application, bikeDaoProvider: Provider<BikeDao>): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // for adding
                    CoroutineScope(Dispatchers.IO).launch {
                        val bikeDao = bikeDaoProvider.get()
                        bikeDao.insertBike(Bike(1,"electrical","Block 45, New Belgrade",80.00,"photo1"))
                        bikeDao.insertBike(Bike(2,"electrical","Block 34, New Belgrade",80.00,"photo1"))
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao{
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideBikeDao(appDatabase: AppDatabase): BikeDao{
        return appDatabase.bikeDao()
    }

    @Provides
    @Singleton
    fun provideBikeRentalDao(appDatabase: AppDatabase): BikeRentalDao{
        return appDatabase.bikeRentalDao()
    }


    @Provides
    @Singleton
    fun provideBikeReturnReportDao(appDatabase: AppDatabase): BikeReturnReportDao{
        return appDatabase.bikeReturnReport()
    }
}