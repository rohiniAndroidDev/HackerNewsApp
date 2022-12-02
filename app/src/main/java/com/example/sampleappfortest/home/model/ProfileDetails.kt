package com.example.sampleappfortest.home.model

import com.google.gson.annotations.SerializedName


data class ProfileDetails (
    val createdAt: String,
    val name: String,
    val avatar: String,
    val lastname: String,
    val email: String,
    val id: String
)
