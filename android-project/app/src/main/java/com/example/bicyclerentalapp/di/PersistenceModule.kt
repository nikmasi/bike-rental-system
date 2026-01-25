package com.example.bicyclerentalapp.di

import android.app.Application
import androidx.room.Room
import com.example.bicyclerentalapp.R
import com.example.bicyclerentalapp.persistence.AppDatabase
import com.example.bicyclerentalapp.persistence.BikeDao
import com.example.bicyclerentalapp.persistence.BikeRentalDao
import com.example.bicyclerentalapp.persistence.BikeReturnReportDao
import com.example.bicyclerentalapp.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule{

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
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