package bj4.yhh.ezcounter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testAddCount1_MainActivity() throws Exception {
        onView(withId(R.id.add_one)).perform(click());
        onView(withText("1")).check(matches(isDisplayed()));
    }

    @Test
    public void testResetTo0_MainActivity() throws Exception {
        onView(withId(R.id.add_one)).perform(click());
        onView(withId(R.id.reset)).perform(click());
        onView(withId(R.id.counter)).check(matches(withText("0")));
    }

    @Test
    public void testLockResetTextLock_MainActivity() throws Exception {
        onView(withId(R.id.lock_reset)).check(matches(withText(R.string.lock_reset)));
    }

    @Test
    public void testLockResetTextUnLock_MainActivity() throws Exception {
        onView(withId(R.id.lock_reset)).perform(click());
        onView(withId(R.id.lock_reset)).check(matches(withText(R.string.unlock_reset)));
    }


    @Test
    public void testLockResetLock_MainActivity() throws Exception {
        onView(withId(R.id.add_one)).perform(click());
        onView(withId(R.id.lock_reset)).perform(click());
        onView(withId(R.id.reset)).perform(click());
        onView(withId(R.id.counter)).check(matches(withText("1")));
    }
}
