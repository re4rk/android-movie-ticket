package model

import java.io.Serializable
import java.time.LocalDateTime

class SeatSelectionModel(
    val title: String,
    val reserveTime: LocalDateTime,
    val Quantity: Int,
) : Serializable
