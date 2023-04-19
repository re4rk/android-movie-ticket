package model

import java.io.Serializable
import java.time.LocalDateTime

data class TicketModel(
    val title: String,
    val reserveTime: LocalDateTime,
    val price: Int,
    val seats: List<SeatModel>,
) : Serializable {
    companion object {
        val EMPTY = TicketModel("", LocalDateTime.MIN, 0, emptyList())
    }
}
