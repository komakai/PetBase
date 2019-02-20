package net.telepathix.petbase.model

data class Config(
    val isChatEnabled: Boolean,
    val isCallEnabled: Boolean,
    val workHours: String
)
