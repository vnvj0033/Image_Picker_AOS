package com.yoosangyeop.imagepicker.di

import android.content.Context
import androidx.room.Room
import com.yoosangyeop.imagepicker.domain.data.api.SearchService
import com.yoosangyeop.imagepicker.domain.data.db.SearchClipDao
import com.yoosangyeop.imagepicker.domain.data.db.SearchImageDao
import com.yoosangyeop.imagepicker.domain.data.db.SearchDatabase
import com.yoosangyeop.imagepicker.domain.data.preferences.PreferencesUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePreferenceUtil(@ApplicationContext context: Context) =
        PreferencesUtil(context, "NAME_PREFERENCE_UTIL")

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK 2b470e386ac884d75aea54170fbf72f3")
                    .build()
                chain.proceed(request)
            }.build()

        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun provideSearchDB(@ApplicationContext context: Context): SearchDatabase =
        Room.databaseBuilder(context, SearchDatabase::class.java, "search_database")
            .build()

    @Singleton
    @Provides
    fun provideSearchImageDao(database: SearchDatabase) : SearchImageDao =
        database.searchImageDao()

    @Singleton
    @Provides
    fun provideSearchClipDao(database: SearchDatabase) : SearchClipDao =
        database.searchClipDao()

}