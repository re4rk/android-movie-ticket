package woowacourse.movie.movieList.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import model.ItemViewType
import woowacourse.movie.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    private val view: View,
    onClickButton: (Int) -> Unit,

) : ItemViewHolder(view) {
    private val posterView: ImageView = view.findViewById(R.id.movie_poster)
    private val titleView: TextView = view.findViewById(R.id.movie_title)
    private val releaseDateView: TextView = view.findViewById(R.id.movie_release_date)
    private val runningTimeView: TextView = view.findViewById(R.id.movie_running_time)
    private val reservationButton: View = view.findViewById(R.id.movie_reservation_button)

    init {
        reservationButton.setOnClickListener { onClickButton(adapterPosition) }
    }

    override fun bind(itemViewType: ItemViewType) {
        val movieListItem = itemViewType as? ItemViewType.Movie ?: return

        posterView.setImageResource(movieListItem.poster)
        titleView.text = movieListItem.title
        releaseDateView.text = getScreeningDate(movieListItem.startDate, movieListItem.endDate)
        runningTimeView.text = view.context.getString(R.string.movie_running_time).format(movieListItem.runTime)
    }

    private fun getScreeningDate(startDate: LocalDate, endDate: LocalDate): String {
        return "${startDate.format(dateTimeFormatter)} ~ ${endDate.format(dateTimeFormatter)}"
    }

    companion object {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}
