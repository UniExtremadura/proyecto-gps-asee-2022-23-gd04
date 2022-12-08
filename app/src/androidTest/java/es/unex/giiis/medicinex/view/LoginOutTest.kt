package es.unex.giiis.medicinex.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.medicinex.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginOutTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun loginOutTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        val appCompatTextView = onView(
            allOf(
                withId(R.id.createAccount), withText("Crea una nueva"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.name_field),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.name_field),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("josefino"), closeSoftKeyboard())
        Thread.sleep(1000)

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.user_field),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("josejose"), closeSoftKeyboard())
        Thread.sleep(1000)

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.email_field),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("josejose@gmail.com"), closeSoftKeyboard())
        Thread.sleep(1000)

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.password_field),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("jose1234"), closeSoftKeyboard())
        Thread.sleep(1000)

        //pressBack()

        val appCompatButton = onView(
            allOf(
                withId(R.id.registerButton), withText("Registrarse"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2000)

        val appCompatButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("Aceptar"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton2.perform(scrollTo(), click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.closeButtonImage),
                withContentDescription("Botón que cierra la pantalla actual"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.usermail),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(click())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.usermail),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText7.perform(replaceText("josejose@gmail.com"), closeSoftKeyboard())
        Thread.sleep(1000)

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText8.perform(replaceText("jose1234"), closeSoftKeyboard())
        Thread.sleep(1000)

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.loginButton), withText("Entrar"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        val floatingActionButton = onView(
            allOf(
                withId(R.id.openSettings), withContentDescription("Abrir los ajustes del perfil"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.logOnButton),
                withContentDescription("Botón para cerrar la sesión actual."),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    13
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val textView = onView(
            allOf(
                withId(android.R.id.message),
                withText("¿Desea cerrar sesión con la cuenta actual?"),
                withParent(withParent(withId(androidx.constraintlayout.widget.R.id.scrollView))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("¿Desea cerrar sesión con la cuenta actual?")))
        Thread.sleep(1000)

        val appCompatButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("Sí"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.constraintlayout.widget.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton4.perform(scrollTo(), click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        val textView2 = onView(
            allOf(
                withId(R.id.iniciarSesion), withText("Iniciar sesión"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
