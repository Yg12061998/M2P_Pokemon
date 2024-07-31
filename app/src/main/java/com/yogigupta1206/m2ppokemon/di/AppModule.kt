package com.yogigupta1206.m2ppokemon.di

import android.app.Application
import androidx.room.Room
import com.yogigupta1206.m2ppokemon.data.data_source.db.AppDbDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDbDataSource(app: Application): AppDbDataSource {
        return Room
            .databaseBuilder(
                app,
                AppDbDataSource::class.java,
                AppDbDataSource.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }
}