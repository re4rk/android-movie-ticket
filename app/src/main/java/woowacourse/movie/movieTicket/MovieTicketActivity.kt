package woowacourse.movie.movieTicket

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import movie.MovieTicket
import woowacourse.movie.R
import woowacourse.movie.utils.DateUtil
import java.text.DecimalFormat

class MovieTicketActivity : AppCompatActivity() {
    private val ticket by lazy { intent.getSerializableExtra(KEY_MOVIE_TICKET) as? MovieTicket ?: throw IllegalArgumentException() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_ticket)
        registerToolbar()
        updateMovieView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun registerToolbar() {
        val reservationToolbar = findViewById<Toolbar>(R.id.ticket_toolbar)
        setSupportActionBar(reservationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateMovieView() {
        val ticketTitleView = findViewById<TextView>(R.id.ticket_movie_title)
        val ticketCountView = findViewById<TextView>(R.id.ticket_total_ticket_count)
        val ticketMovieReleaseDateView = findViewById<TextView>(R.id.ticket_release_date)
        val ticketTotalPriceView = findViewById<TextView>(R.id.ticket_total_price)
        val context = this

        with(ticket) {
            ticketTitleView.text = title
            ticketMovieReleaseDateView.text = DateUtil(context).getDate(date)
            ticketCountView.text = getString(R.string.movie_ticket_count).format(count.toInt())
            ticketTotalPriceView.text = getString(R.string.movie_ticket_total_price).format(decimalFormat.format(getTotalPrice()))
        }
    }

    companion object {
        private val decimalFormat = DecimalFormat("#,###")
        const val KEY_MOVIE_TICKET = "movieTicket"
    }
}
