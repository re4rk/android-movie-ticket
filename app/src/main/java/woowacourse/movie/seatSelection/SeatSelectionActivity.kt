package woowacourse.movie.seatSelection

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import model.MovieTicketModel
import model.SeatSelectionModel
import woowacourse.movie.R
import woowacourse.movie.movieTicket.MovieTicketActivity
import woowacourse.movie.utils.getSerializableExtraCompat

class SeatSelectionActivity : AppCompatActivity() {
    private val seatSelection: SeatSelectionModel by lazy {
        intent.getSerializableExtraCompat(KEY_SEAT_SELECTION) as? SeatSelectionModel ?: run {
            finish()
            Toast.makeText(this, INVALID_MOVIE_SCREENING, Toast.LENGTH_LONG).show()
            SeatSelectionModel.EMPTY
        }
    }

    private val seatSelectionView by lazy {
        SeatSelectionTable(
            window.decorView.rootView,
            seatSelection,
        ) { ticketModel -> onConfirmClick(ticketModel) }
    }

    private fun onConfirmClick(movieTicketModel: MovieTicketModel) = AlertDialog.Builder(this)
        .setCancelable(false)
        .setTitle(getString(R.string.seat_selection_confirm_dialog_title))
        .setMessage(getString(R.string.seat_selection_confirm_dialog_contents))
        .setPositiveButton(getString(R.string.seat_selection_confirm_dialog_yes)) { _, _ -> MovieTicketActivity.start(this, movieTicketModel) }
        .setNegativeButton(getString(R.string.seat_selection_confirm_dialog_no)) { dialog, _ -> dialog.dismiss() }
        .show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        initToolbar()

        savedInstanceState?.let { seatSelectionView.loadSeatSelection(it) } ?: seatSelectionView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        seatSelectionView.saveInstanceState(outState)
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

    private fun initToolbar() {
        val reservationToolbar = findViewById<Toolbar>(R.id.seat_selection_toolbar)
        setSupportActionBar(reservationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val INVALID_MOVIE_SCREENING = "잘못된 영화 상영 정보입니다."
        private const val KEY_SEAT_SELECTION = "key_seat_selection"

        fun start(context: AppCompatActivity, seatSelection: SeatSelectionModel) {
            context.startActivity(
                Intent(context, SeatSelectionActivity::class.java).apply {
                    putExtra(KEY_SEAT_SELECTION, seatSelection)
                },
            )
        }
    }
}
