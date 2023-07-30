package com.umutcansahin.mynewsapp.manager.recyclerview_listener

import androidx.recyclerview.widget.RecyclerView

class RecyclerviewListener(
    private val onScrollUp: ()-> Unit,
    private val onScrollDown: ()-> Unit
):RecyclerView.OnScrollListener() {

    private var scrolledDistance = 0
    private var controlsVisible = true
    private val scrollThreshold = 20

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (scrolledDistance > scrollThreshold && controlsVisible) {
            onScrollDown()
            controlsVisible = false
            scrolledDistance = 0
        } else if (scrolledDistance < -scrollThreshold && !controlsVisible) {
            onScrollUp()
            controlsVisible = true
            scrolledDistance = 0
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy
        }
    }
}