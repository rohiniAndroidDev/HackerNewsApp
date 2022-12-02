// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                  = Json(JsonConfiguration.Stable)
// val categoryCountResponse = json.parse(ImageDetails.serializer(), jsonString)

package com.example.sampleappfortest.home.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName



data class ImageDetails (
    val createdAt: String,
    val name: String,
    val avatar: String,
    val id: String
)