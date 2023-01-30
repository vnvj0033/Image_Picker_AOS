package com.yoosangyeop.imagepicker.core.repository.di

import com.yoosangyeop.imagepicker.core.repository.SearchRepository
import com.yoosangyeop.imagepicker.core.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryBinds {

    @Binds
    abstract fun bindsSearcherRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository

}