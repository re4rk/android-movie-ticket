package woowacourse.movie.movieReservation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import model.ReservationModel
import woowacourse.movie.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReservationContents(
    view: View,
    reservationModel: ReservationModel,
) {
    private val posterView: ImageView = view.findViewById(R.id.reservation_movie_poster)
    private val titleView: TextView = view.findViewById(R.id.reservation_movie_title)
    private val screeningDateView: TextView = view.findViewById(R.id.reservation_movie_release_date)
    private val runTimeView: TextView = view.findViewById(R.id.reservation_movie_running_time)
    private val summaryView: TextView = view.findViewById(R.id.reservation_movie_summary)

    init {
        reservationModel.let {
            posterView.setImageResource(it.poster)
            titleView.text = it.title
            screeningDateView.text = getScreeningDate(it.startDate, it.endDate)
            runTimeView.text = view.context.getString(R.string.movie_running_time).format(it.runTime)
            summaryView.text = it.summary
        }
    }

    private fun getScreeningDate(startDate: LocalDate, endDate: LocalDate): String {
        return "${startDate.format(dateTimeFormatter)} ~ ${endDate.format(dateTimeFormatter)}"
    }

    companion object {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}
