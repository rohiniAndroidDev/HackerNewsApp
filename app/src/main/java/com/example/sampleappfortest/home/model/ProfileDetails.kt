package com.example.sampleappfortest.home.model

import com.google.gson.annotations.SerializedName


data class ProfileDetails (
    val about: String,
    val created: Long,
    val delay: Long,
    val id: String,
    val karma: Long,
    val submitted: List<Long>
)
