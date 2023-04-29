package woowacourse.movie

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import model.SeatSelectionModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.movieTicket.MovieTicketActivity
import woowacourse.movie.seatSelection.SeatSelectionActivity
import java.time.LocalDateTime

class SeatSelectionActivityTest {

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<SeatSelectionActivity> =
        ActivityScenarioRule(
            SeatSelectionActivity.getIntent(
                ApplicationProvider.getApplicationContext(),
                SeatSelectionModel(
                    title = "해리포터와 마법사의 돌",
                    reserveTime = LocalDateTime.of(2021, 1, 1, 1, 1),
                    Quantity = 3,
                ),
            ),
        )

    @Test
    fun 전달_받은_영화_제목을_설정하고_최초_가격은_0원이다() {
        onView(withId(R.id.seat_selection_title))
            .check(matches(withText("해리포터와 마법사의 돌")))

        onView(withId(R.id.seat_selection_price))
            .check(matches(withText("0원")))
    }

    @Test
    fun B_Class_좌석에_앉으면_B_클래스_가격만큼_증가한다() {
        onView(withText("A1")).perform(ViewActions.click())
        onView(withId(R.id.seat_selection_price))
            .check(matches(withText("8000원")))
    }

    @Test
    fun A_Class_좌석에_앉으면_A_클래스_가격만큼_증가한다() {
        onView(withText("E1")).perform(ViewActions.click())
        onView(withId(R.id.seat_selection_price))
            .check(matches(withText("10000원")))
    }

    @Test
    fun S_Class_좌석에_앉으면_S_클래스_가격만큼_증가한다() {
        onView(withText("C1")).perform(ViewActions.click())
        onView(withId(R.id.seat_selection_price))
            .check(matches(withText("13000원")))
    }

    @Test
    fun 전달_받은_수_만큼_좌석을_선택하지_않으면_결제하기_버튼이_활성화된다() {
        onView(withId(R.id.seat_selection_confirm))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun 전달_받은_수_만큼_좌석을_선택하면_결제하기_버튼이_활성화된다() {
        onView(withText("A1")).perform(ViewActions.click())
        onView(withText("B1")).perform(ViewActions.click())
        onView(withText("C1")).perform(ViewActions.click())
        onView(withId(R.id.seat_selection_confirm))
            .check(matches(isEnabled()))
    }

    @Test
    fun 초기_좌석들은_Enable_이_false_다() {
        onView(withText("A1")).check(matches(isEnabled()))
    }

    @Test
    fun 선택하면_Enable이_true로_바뀐다() {
        onView(withText("A1")).perform(ViewActions.click())
        onView(withText("A1")).check(matches(isEnabled()))
    }

    @Test
    fun 확인_버튼을_누르면_다이어로그가_나온다() {
        onView(withText("A1")).perform(ViewActions.click())
        onView(withText("B1")).perform(ViewActions.click())
        onView(withText("C1")).perform(ViewActions.click())
        onView(withId(R.id.seat_selection_confirm)).perform(ViewActions.click())
        onView(withText(R.string.seat_selection_confirm_dialog_contents)).check(matches(isDisplayed()))
    }

    @Test
    fun 다이어로그에서_확인을_누르면_결제화면으로_이동한다() {
        onView(withText("A1")).perform(ViewActions.click())
        onView(withText("B1")).perform(ViewActions.click())
        onView(withText("C1")).perform(ViewActions.click())
        onView(withId(R.id.seat_selection_confirm)).perform(ViewActions.click())
        onView(withText(R.string.seat_selection_confirm_dialog_contents)).check(matches(isDisplayed()))
        onView(withText(R.string.seat_selection_confirm_dialog_yes)).perform(ViewActions.click())
        intended(hasComponent(MovieTicketActivity::class.java.name))
    }
}
