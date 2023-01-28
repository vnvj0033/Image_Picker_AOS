package com.yoosangyeop.imagepicker.core.data.di

import android.content.Context
import androidx.room.Room
import com.yoosangyeop.imagepicker.core.data.db.SearchFavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideFavoriteDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, SearchFavoriteDatabase::class.java, "favorite_database")
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideSearchImageDao(database: SearchFavoriteDatabase) =
        database.searchImageDao()

    @Provides
    fun provideSearchClipDao(database: SearchFavoriteDatabase) =
        database.searchClipDao()
}