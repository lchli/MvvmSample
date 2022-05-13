package com.lch.mvvmarchsample.task.di

import com.lch.mvvmarchsample.task.di.data.DefaultTaskRepo
import com.lch.mvvmarchsample.task.domain.ITaskRepo
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