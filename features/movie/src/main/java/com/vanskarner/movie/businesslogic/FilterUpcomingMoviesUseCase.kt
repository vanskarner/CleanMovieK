package com.vanskarner.movie.businesslogic

import com.vanskarner.movie.MovieBasicDS
import com.vanskarner.movie.MoviesFilterDS
import java.util.Locale

class FilterUpcomingMoviesUseCase {

    fun execute(inputValues: MoviesFilterDS): Result<MoviesFilterDS> {
        val query: String = inputValues.query.toString().lowercase().trim()
        inputValues.filterList = inputValues.fullList
        if (query.isNotEmpty()) {
            val filteredList: MutableList<MovieBasicDS> = ArrayList()
            for (item in inputValues.fullList)
                if (item.title.lowercase(Locale.ENGLISH).contains(query)) filteredList.add(item)
            inputValues.filterList = filteredList
        }
        return Result.success(inputValues)
    }

}