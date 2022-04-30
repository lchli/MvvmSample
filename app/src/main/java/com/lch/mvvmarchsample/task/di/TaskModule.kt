package com.lch.mvvmarchsample.task.di

import com.lch.mvvmarchsample.task.data.ITaskRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TaskModule {

    @Provides
    fun bindTaskRepo(): ITaskRepo {
        return DefaultTaskRepo()
    }
}