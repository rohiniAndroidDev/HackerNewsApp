package com.example.sampleappfortest.home.data.repository

import com.example.sampleappfortest.common.MIN_WAIT_TIME_FETCHING_IDS
import java.util.concurrent.TimeUnit

object AppCache {
    private var fetchTrack: MutableMap<String, Long> = HashMap()

    fun canFetch(storyType: String): Boolean {
        val lastFetch = fetchTrack[storyType]
        if (lastFetch != null) {
            val current = System.currentTimeMillis()
            val diff = current - lastFetch
            if (TimeUnit.MILLISECONDS.toMinutes(diff) < MIN_WAIT_TIME_FETCHING_IDS) {
                return false
            }
        }
        fetchTrack[storyType] = System.currentTimeMillis()
        return true
    }
}