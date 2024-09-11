package com.vanskarner.cleanmoviek.ui.upcoming

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class Pagination @Inject constructor() : RecyclerView.OnScrollListener() {
    companion object {
        private const val LAST_POSITION: Int = 1
        internal const val LAST_POSITION_COMPLETE: Int = 2
    }

    private var pageNumber = 1
    private var allowScroll = false
    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var positionType: Int = LAST_POSITION

    fun getPageNumber(): Int {
        return pageNumber
    }

    fun enableScroll() {
        allowScroll = true
    }

    private fun disableScroll() {
        allowScroll = false
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun setPositionType(positionType: Int) {
        this.positionType = positionType
    }

    fun increment() {
        pageNumber++
        allowScroll = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (isValidScrolled(recyclerView.layoutManager, dy)) {
            disableScroll()
            onLoadMoreListener.onLoadMore(pageNumber)
        }
    }

    private fun isValidScrolled(manager: RecyclerView.LayoutManager?, dy: Int): Boolean {
        return manager != null && dy > 0 &&
                isLastPosition(manager) &&
                allowScroll
    }

    private fun isLastPosition(manager: RecyclerView.LayoutManager): Boolean {
        return getLastPosition(manager) == manager.itemCount - 1
    }

    private fun getLastPosition(layoutManager: RecyclerView.LayoutManager): Int {
        var position = 0
        if (layoutManager is GridLayoutManager) position = lastPositionGrid(layoutManager)
        else if (layoutManager is LinearLayoutManager) position = lastPositionLinear(layoutManager)
        return position
    }

    private fun lastPositionGrid(manager: GridLayoutManager): Int {
        return if ((positionType == LAST_POSITION)) manager.findLastVisibleItemPosition() else manager.findLastCompletelyVisibleItemPosition()
    }

    private fun lastPositionLinear(manager: LinearLayoutManager): Int {
        return if ((positionType == LAST_POSITION)) manager.findLastVisibleItemPosition() else manager.findLastCompletelyVisibleItemPosition()
    }

}

interface OnLoadMoreListener {
    fun onLoadMore(page: Int)
}