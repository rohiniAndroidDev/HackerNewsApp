package com.example.sampleappfortest.home.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "idsTable")
data class IdModel(
    @PrimaryKey val id: Int,
    val addedTime: Long = System.currentTimeMillis()
) : Parcelable
