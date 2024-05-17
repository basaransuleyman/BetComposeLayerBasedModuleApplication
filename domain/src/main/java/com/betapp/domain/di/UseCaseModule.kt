package com.betapp.domain.di

import com.betapp.domain.repository.MatchRepository
import com.betapp.domain.usecase.GetFinishedMatchesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetFinishedMatchesUseCase(matchRepository: MatchRepository): GetFinishedMatchesUseCase {
        return GetFinishedMatchesUseCase(matchRepository)
    }
}