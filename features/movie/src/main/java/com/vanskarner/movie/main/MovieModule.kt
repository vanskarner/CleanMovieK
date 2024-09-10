package com.vanskarner.movie.main

import com.google.gson.GsonBuilder
import com.vanskarner.movie.DefaultMovieComponent
import com.vanskarner.movie.MovieComponent
import com.vanskarner.movie.businesslogic.CheckFavoriteMovieUseCase
import com.vanskarner.movie.businesslogic.DeleteAllFavoriteMoviesUseCase
import com.vanskarner.movie.businesslogic.FilterUpcomingMoviesUseCase
import com.vanskarner.movie.businesslogic.FindFavoriteMovieUseCase
import com.vanskarner.movie.businesslogic.FindUpcomingMovieUseCase
import com.vanskarner.movie.businesslogic.MovieLocalRepository
import com.vanskarner.movie.businesslogic.MovieRemoteRepository
import com.vanskarner.movie.businesslogic.ShowFavoriteMoviesUseCase
import com.vanskarner.movie.businesslogic.ShowUpcomingMoviesUseCase
import com.vanskarner.movie.businesslogic.ToggleMovieFavoriteUseCase
import com.vanskarner.movie.businesslogic.ValidateLoginUseCase
import com.vanskarner.movie.persistence.local.MovieDao
import com.vanskarner.movie.persistence.local.RoomMovieLocalRepository
import com.vanskarner.movie.persistence.remote.MovieApiClient
import com.vanskarner.movie.persistence.remote.MovieDTO
import com.vanskarner.movie.persistence.remote.MovieDeserializer
import com.vanskarner.movie.persistence.remote.MovieRemoteErrorInterceptor
import com.vanskarner.movie.persistence.remote.RetrofitMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MovieModule {

    @Provides
    @Singleton
    fun provideMovieComponent(
        checkFavoriteMovieUseCase: Provider<CheckFavoriteMovieUseCase>,
        deleteAllFavoriteMoviesUseCase: Provider<DeleteAllFavoriteMoviesUseCase>,
        filterUpcomingMoviesUseCase: Provider<FilterUpcomingMoviesUseCase>,
        findFavoriteMovieUseCase: Provider<FindFavoriteMovieUseCase>,
        findUpcomingMovieUseCase: Provider<FindUpcomingMovieUseCase>,
        showFavoriteMoviesUseCase: Provider<ShowFavoriteMoviesUseCase>,
        showUpcomingMoviesUseCase: Provider<ShowUpcomingMoviesUseCase>,
        toggleMovieFavoriteUseCase: Provider<ToggleMovieFavoriteUseCase>,
        validateLoginUseCase: Provider<ValidateLoginUseCase>
    ): MovieComponent {
        return DefaultMovieComponent(
            checkFavoriteMovieUseCase,
            deleteAllFavoriteMoviesUseCase,
            filterUpcomingMoviesUseCase,
            findFavoriteMovieUseCase,
            findUpcomingMovieUseCase,
            showFavoriteMoviesUseCase,
            showUpcomingMoviesUseCase,
            toggleMovieFavoriteUseCase,
            validateLoginUseCase
        )
    }

    @Provides
    @Singleton
    fun provideToggleMovieFavoriteUseCase(
        localRepository: MovieLocalRepository,
        checkFavoriteMovieUseCase: CheckFavoriteMovieUseCase
    ): ToggleMovieFavoriteUseCase {
        return ToggleMovieFavoriteUseCase(localRepository, checkFavoriteMovieUseCase)
    }

    @Provides
    @Singleton
    fun provideCheckFavoriteMovieUseCase(localRepository: MovieLocalRepository): CheckFavoriteMovieUseCase {
        return CheckFavoriteMovieUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllFavoriteMoviesUseCase(localRepository: MovieLocalRepository): DeleteAllFavoriteMoviesUseCase {
        return DeleteAllFavoriteMoviesUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideFilterUpcomingMoviesUseCase(): FilterUpcomingMoviesUseCase {
        return FilterUpcomingMoviesUseCase()
    }

    @Provides
    @Singleton
    fun provideFindFavoriteMovieUseCase(localRepository: MovieLocalRepository): FindFavoriteMovieUseCase {
        return FindFavoriteMovieUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideFindUpcomingMovieUseCase(remoteRepository: MovieRemoteRepository): FindUpcomingMovieUseCase {
        return FindUpcomingMovieUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun provideShowFavoriteMoviesUseCase(localRepository: MovieLocalRepository): ShowFavoriteMoviesUseCase {
        return ShowFavoriteMoviesUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideShowUpcomingMoviesUseCase(remoteRepository: MovieRemoteRepository): ShowUpcomingMoviesUseCase {
        return ShowUpcomingMoviesUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun provideValidateLoginUseCase(): ValidateLoginUseCase {
        return ValidateLoginUseCase()
    }

    @Provides
    @Singleton
    fun provideMovieLocalRepository(movieDao: MovieDao): MovieLocalRepository {
        return RoomMovieLocalRepository(movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteRepository(): MovieRemoteRepository {
        val apiKey = ""
        val baseImageUrl = ""
        val baseUrl = ""
        val interceptor = MovieRemoteErrorInterceptor()
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .readTimeout(8, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        val deserializer = MovieDeserializer(baseImageUrl)
        val gsonConverter = GsonConverterFactory.create(
            GsonBuilder().registerTypeAdapter(
                MovieDTO::class.java,
                deserializer
            ).create()
        )
        val movieApiClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverter)
            .client(httpClient)
            .build().create(MovieApiClient::class.java)
        return RetrofitMovieRepository(apiKey, movieApiClient)
    }

}