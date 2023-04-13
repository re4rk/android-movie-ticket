package woowacourse.movie.movieList

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import movie.Cinema
import movie.MovieInfo
import woowacourse.movie.R
import woowacourse.movie.movieReservation.MovieReservationActivity
import woowacourse.movie.utils.DateUtil

class MovieListAdapter(
    private val context: Context,
    private val Cinema: Cinema,
) : BaseAdapter() {
    override fun getCount(): Int {
        return Cinema.size
    }

    override fun getItem(position: Int): MovieInfo {
        return Cinema[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(
            parent?.context,
            R.layout.include_movie_list_item,
            null,
        )

        val poster = view.findViewById<ImageView>(R.id.movie_poster)
        val title = view.findViewById<TextView>(R.id.movie_title)
        val releaseDate = view.findViewById<TextView>(R.id.movie_release_date)
        val runningTime = view.findViewById<TextView>(R.id.movie_running_time)
        val reservationButton = view.findViewById<Button>(R.id.movie_reservation_button)

        with(Cinema[position]) {
            poster.setImageResource(movie.poster)
            title.text = movie.title

            releaseDate.text = DateUtil(context).getDateRange(startDate, endDate)
            runningTime.text = context.getString(R.string.movie_running_time).format(this.movie.runningTime)
            reservationButton.setOnClickListener {
                val intent = Intent(context, MovieReservationActivity::class.java)
                intent.putExtra("movieInfo", this)
                startActivity(context, intent, null)
            }
        }

        return view
    }
}
