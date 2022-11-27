//package ba.etf.rma21.projekat
//
//import androidx.recyclerview.widget.RecyclerView
//import androidx.test.espresso.Espresso.*
//import androidx.test.espresso.NoMatchingViewException
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.intent.rule.IntentsTestRule
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import ba.etf.rma21.projekat.data.repositories.KvizRepository
//import org.hamcrest.CoreMatchers
//import org.hamcrest.CoreMatchers.anything
//import org.hamcrest.CoreMatchers.not
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class MySpirala2AndroidTest {
//    @get:Rule
//    val intentsTestRule = IntentsTestRule<MainActivity>(
//        MainActivity::class.java)
//
//    @Test
//    fun test1() {
//        val kvizovi = KvizRepository.getUpisani()
//        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText(kvizovi[2].naziv)),
//                hasDescendant(withText(kvizovi[2].nazivPredmeta))), click()))
//        onView(withId(R.id.navigacijaPitanja)).check(matches(isDisplayed()))
//        onData(anything()).inAdapterView(withId(R.id.odgovori)).atPosition(1).perform(click());
//        onView(withId(R.id.predajKviz)).perform(click())
//        onView(withSubstring("Završili ste kviz")).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun test2() {
//        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
//        onView(withId(R.id.predmeti)).perform(click())
//        try {
//            onView(withId(R.id.filterKvizova)).check(matches(not(isDisplayed())))
//            return assert(false)
//        } catch (e: NoMatchingViewException) {}
//        onView(withId(R.id.odabirGodina)).check(matches(isDisplayed()))
//        onView(withId(R.id.dodajPredmetDugme)).perform(click())
//        try {
//            onView(withSubstring("Uspješno ste upisani u grupu")).check(matches(isDisplayed()))
//            return assert(true)
//        } catch (e: NoMatchingViewException) {}
//        //da li pritisak nazad vraca na kvizove
//        pressBack()
//        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
//        //ponovo pritisak nazad da ne vraca na predmete
//        pressBack()
//        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
//    }
//}