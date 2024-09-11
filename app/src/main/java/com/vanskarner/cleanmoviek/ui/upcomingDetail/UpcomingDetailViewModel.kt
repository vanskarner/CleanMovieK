package com.vanskarner.cleanmoviek.ui.upcomingDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.cleanmoviek.ui.MovieDetailModel
import com.vanskarner.cleanmoviek.ui.errors.ErrorFilter
import com.vanskarner.cleanmoviek.ui.errors.ErrorView
import com.vanskarner.cleanmoviek.ui.toData
import com.vanskarner.cleanmoviek.ui.toModel
import com.vanskarner.movie.MovieComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingDetailViewModel @Inject constructor(
    private val movieComponent: MovieComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {

    private val _markedAsFavorite = MutableLiveData<Boolean>()
    private val _item = MutableLiveData<MovieDetailModel>()
    private val _error = MutableLiveData<ErrorView<*>>()

    val markedAsFavorite: LiveData<Boolean> = _markedAsFavorite
    val item: LiveData<MovieDetailModel> = _item
    val error: LiveData<ErrorView<*>> = _error

    fun checkFavorite(movieId: Int) {
        viewModelScope.launch {
            movieComponent.checkFavorite(movieId)
                .onSuccess { _markedAsFavorite.value = it }
                .onFailure { _error.value = errorFilter.filter(it) }

            movieComponent.findUpcoming(movieId)
                .onSuccess { _item.value = it.toModel() }
                .onFailure { _error.value = errorFilter.filter(it) }
        }
    }

    fun toggleFavorite(movieDetailModel: MovieDetailModel) {
        viewModelScope.launch {
            movieComponent.toggleFavorite(movieDetailModel.toData())
                .onSuccess { _markedAsFavorite.value = it }
                .onFailure { _error.value = errorFilter.filter(it) }
        }
    }

}