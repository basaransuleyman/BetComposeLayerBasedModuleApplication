package com.betapp.data.di

import android.content.Context
import androidx.room.Room
import com.betapp.data.BuildConfig
import com.betapp.data.api.ApiService
import com.betapp.data.persistence.MatchDao
import com.betapp.data.persistence.MatchDatabase
import com.betapp.data.repository.MatchRepositoryImpl
import com.betapp.domain.repository.MatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMatchRepository(apiService: ApiService, matchDao: MatchDao): MatchRepository {
        return MatchRepositoryImpl(apiService, matchDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MatchDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MatchDatabase::class.java,
            "matches_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMatchDao(database: MatchDatabase): MatchDao {
        return database.matchDao()
    }
}