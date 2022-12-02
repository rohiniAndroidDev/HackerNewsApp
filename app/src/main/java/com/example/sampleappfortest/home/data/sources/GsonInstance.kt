package com.example.sampleappfortest.home.data.sources

import com.google.gson.Gson

object GsonInstance {
    fun instance(): Gson {
        return Gson()
    }
}