package net.telepathix.petbase.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Pet(
    @JsonProperty("image_url") val imageUrl: String,
    val title: String,
    @JsonProperty("content_url") val contentUrl: String,
    @JsonProperty("date_added") val dateAdded: String
)