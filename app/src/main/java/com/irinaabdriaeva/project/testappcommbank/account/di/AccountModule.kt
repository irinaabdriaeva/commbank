package com.irinaabdriaeva.project.testappcommbank.account.di

import android.content.Context
import com.google.gson.Gson
import com.irinaabdriaeva.project.testappcommbank.account.data.repository.AccountRepository
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetAccountDetailsUseCase
import com.irinaabdriaeva.project.testappcommbank.account.ui.usecases.GetTransactionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        @ApplicationContext context: Context,
        gson: Gson
    ): AccountRepository {
        return AccountRepository(context, gson)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsUseCase(repository: AccountRepository): GetTransactionsUseCase {
        return GetTransactionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAccountDetailsUseCase(repository: AccountRepository): GetAccountDetailsUseCase {
        return GetAccountDetailsUseCase(repository)
    }
}