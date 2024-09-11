package com.vanskarner.cleanmoviek.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.cleanmoviek.ui.MovieBasicModel
import com.vanskarner.cleanmoviek.ui.MovieDetailModel
import com.vanskarner.cleanmoviek.ui.errors.ErrorFilter
import com.vanskarner.cleanmoviek.ui.errors.ErrorView
import com.vanskarner.cleanmoviek.ui.toModel
import com.vanskarner.movie.MovieComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val movieComponent: MovieComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {

    private val _list = MutableLiveData<List<MovieBasicModel>>()
    private val _item = MutableLiveData<MovieDetailModel>()
    private val _visibilityDeletedItems = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<ErrorView<*>>()

    val list: LiveData<List<MovieBasicModel>> = _list
    val item: LiveData<MovieDetailModel> = _item
    val visibilityDeletedItems: LiveData<Boolean> = _visibilityDeletedItems
    val error: LiveData<ErrorView<*>> = _error


    fun favoritesList() {
        viewModelScope.launch {
            movieComponent.showFavorite()
                .map { it.map { item -> item.toModel() } }
                .onSuccess {
                    _list.value = it
                    _visibilityDeletedItems.value = it.isEmpty()
                }
                .onFailure { _error.value = errorFilter.filter(it) }
        }
    }

    fun favoriteDetail(movieId: Int) {
        viewModelScope.launch {
            movieComponent.findFavorite(movieId)
                .map { it.toModel() }
                .onSuccess { _item.value = it }
                .onFailure { _error.value = errorFilter.filter(it) }
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            movieComponent.deleteAllFavorite()
                .onSuccess { _visibilityDeletedItems.value = true }
                .onFailure { _error.value = errorFilter.filter(it) }
        }
    }

}