package com.irinaabdriaeva.project.testappcommbank.account.di

import android.content.Context
import com.google.gson.Gson
import com.irinaabdriaeva.project.testappcommbank.account.data.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    // Provide Gson instance
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    // Provide AccountRepository instance
    @Provides
    @Singleton
    fun provideAccountRepository(
        @ApplicationContext context: Context,
        gson: Gson
    ): AccountRepository {
        return AccountRepository(context, gson)
    }
}