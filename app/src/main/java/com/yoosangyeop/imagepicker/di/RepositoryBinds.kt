package com.yoosangyeop.imagepicker.di

import com.yoosangyeop.imagepicker.domain.SearchRepository
import com.yoosangyeop.imagepicker.domain.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBinds {

    @Binds
    abstract fun bindsSearcherRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository

}