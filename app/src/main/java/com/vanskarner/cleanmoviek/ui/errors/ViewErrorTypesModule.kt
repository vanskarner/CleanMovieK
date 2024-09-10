package com.vanskarner.cleanmoviek.ui.errors

import com.vanskarner.movie.MovieError
import com.vanskarner.movie.MovieRemoteError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewErrorTypesModule {

    @Provides
    @Singleton
    fun provideErrorDialog(): ErrorDialog {
        return ErrorDialog()
    }

    @Provides
    @IntoMap
    @ClassKey(MovieError.FavoriteLimitError::class)
    fun provideFavoritesLimitError(errorDialog: ErrorDialog): ErrorView<*> {
        return FavoritesLimitError(errorDialog)
    }

    @Provides
    @IntoMap
    @ClassKey(MovieRemoteError.NoInternet::class)
    fun provideNoInternetError(errorDialog: ErrorDialog): ErrorView<*> {
        return NoInternetError(errorDialog)
    }

    @Provides
    @IntoMap
    @ClassKey(MovieRemoteError.NotFound::class)
    fun provideNotFoundError(errorDialog: ErrorDialog): ErrorView<*> {
        return NotFoundError(errorDialog)
    }

    @Provides
    @IntoMap
    @ClassKey(MovieRemoteError.Server::class)
    fun provideServerError(errorDialog: ErrorDialog): ErrorView<*> {
        return ServerError(errorDialog)
    }

    @Provides
    @IntoMap
    @ClassKey(MovieRemoteError.ServiceUnavailable::class)
    fun provideServiceUnavailableError(errorDialog: ErrorDialog): ErrorView<*> {
        return ServiceUnavailableError(errorDialog)
    }

    @Provides
    @IntoMap
    @ClassKey(MovieRemoteError.Unauthorised::class)
    fun provideUnauthorisedError(errorDialog: ErrorDialog): ErrorView<*> {
        return UnauthorisedError(errorDialog)
    }

    @Provides
    @Singleton
    fun provideErrorFilter(
        errorDialog: ErrorDialog,
        errorMap: Map<Class<*>, @JvmSuppressWildcards Provider<ErrorView<*>>>
    ): ErrorFilter {
        val defaultError = UnknownError(errorDialog)
        return ViewErrorFilter(defaultError, errorMap)
    }

}