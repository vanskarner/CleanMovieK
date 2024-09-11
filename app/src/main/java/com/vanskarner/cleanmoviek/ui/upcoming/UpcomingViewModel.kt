package com.vanskarner.cleanmoviek.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.cleanmoviek.ui.MovieBasicModel
import com.vanskarner.cleanmoviek.ui.errors.ErrorFilter
import com.vanskarner.cleanmoviek.ui.errors.ErrorView
import com.vanskarner.cleanmoviek.ui.toModel
import com.vanskarner.movie.MovieComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val movieComponent: MovieComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {
    private val _list = MutableLiveData<List<MovieBasicModel>>()
    private val _error = MutableLiveData<ErrorView<*>>()

    val list: LiveData<List<MovieBasicModel>> = _list
    val error: LiveData<ErrorView<*>> = _error

    fun initialLoad(page: Int) {
        viewModelScope.launch {
            if (page == 1) {
                movieComponent.showUpcoming(page)
                    .onSuccess { _list.value = it.map { item -> item.toModel() } }
                    .onFailure { _error.value = errorFilter.filter(it) }
            }
        }
    }

    fun loadMore(page: Int) {
        viewModelScope.launch {
            movieComponent.showUpcoming(page)
                .onSuccess { _list.value = it.map { item -> item.toModel() } }
                .onFailure { _error.value = errorFilter.filter(it) }
        }
    }

}