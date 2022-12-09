package es.unex.giiis.medicinex.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import es.unex.giiis.medicinex.R
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MedicineDataTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(Login::class.java)

    @Test
    fun medicineDataTest() {
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
        Thread.sleep(1000)

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

        runBlocking {
            val fakRef = Firebase.database.getReference("CHICLE/<")
            val response = fakRef.get().await()

            fakRef.child("1234567").child("Comerc").setValue(true)
            fakRef.child("1234567").child("Conduc").setValue(true)
            fakRef.child("1234567").child("Cpresc").setValue("Sin Receta")
            fakRef.child("1234567").child("Docs").child("0").child("Secc").setValue(true)
            fakRef.child("1234567").child("Docs").child("0").child("Tipo").setValue(1)
            fakRef.child("1234567").child("Docs").child("0").child("Url").setValue("https://cima.aemps.es/cima/pdfs/ft/33924/FT_33924.pdf")
            fakRef.child("1234567").child("Docs").child("0").child("UrlHtml").setValue("https://cima.aemps.es/cima/dochtml/ft/33924/FT_33924.html")
            fakRef.child("1234567").child("Dosis").setValue("0.025 g")
            fakRef.child("1234567").child("Ema").setValue(false)
            fakRef.child("1234567").child("Excipientes").child("0").child("Cantidad").setValue("2.960")
            fakRef.child("1234567").child("Excipientes").child("0").child("Id").setValue(1611)
            fakRef.child("1234567").child("Excipientes").child("0").child("Nombre").setValue("AZUCAR")
            fakRef.child("1234567").child("Excipientes").child("0").child("Orden").setValue(1)
            fakRef.child("1234567").child("Excipientes").child("0").child("Unidad").setValue("g")
            fakRef.child("1234567").child("Excipientes").child("1").child("Cantidad").setValue("1.000")
            fakRef.child("1234567").child("Excipientes").child("1").child("Id").setValue(4774)
            fakRef.child("1234567").child("Excipientes").child("1").child("Nombre").setValue("GLUCOSA")
            fakRef.child("1234567").child("Excipientes").child("1").child("Orden").setValue(3)
            fakRef.child("1234567").child("Excipientes").child("1").child("Unidad").setValue("g")
            fakRef.child("1234567").child("FormaFarmaceutica").child("Id").setValue(71)
            fakRef.child("1234567").child("FormaFarmaceutica").child("Nombre").setValue("CHICLE MEDICAMENTOSO")
            fakRef.child("1234567").child("FormaFarmaceuticaSimplificada").child("Id").setValue(7)
            fakRef.child("1234567").child("FormaFarmaceuticaSimplificada").child("Nombre").setValue("CHICLE")
            fakRef.child("1234567").child("Labtitular").setValue("Fleer Española S.L.")
            fakRef.child("1234567").child("Nombre").setValue("<CHICLE MEDICAMENTOSOS 25 mg ASUCAR")
            fakRef.child("1234567").child("Nregistro").setValue("1234567")
            fakRef.child("1234567").child("Presentaciones").child("0").child("Cn").setValue("7654321")
            fakRef.child("1234567").child("Presentaciones").child("0").child("Nombre").setValue("<CHICLES MEDICAMENTOSOS DE ASUCAR")
            fakRef.child("1234567").child("PrincipiosActivos").child("0").child("Cantidad").setValue("0.025")
            fakRef.child("1234567").child("PrincipiosActivos").child("0").child("Codigo").setValue("1883A")
            fakRef.child("1234567").child("PrincipiosActivos").child("0").child("Id").setValue(1883)
            fakRef.child("1234567").child("PrincipiosActivos").child("0").child("Nombre").setValue("MECLOZINA")
            fakRef.child("1234567").child("PrincipiosActivos").child("0").child("Orden").setValue(1)
            fakRef.child("1234567").child("PrincipiosActivos").child("0").child("Unidad").setValue("g")
            fakRef.child("1234567").child("Receta").setValue(false)
            fakRef.child("1234567").child("ViasAdministracion").child("0").child("Id").setValue(49)
            fakRef.child("1234567").child("ViasAdministracion").child("0").child("Nombre").setValue("USO BUCOFARÍNGEO")
        }

        Thread.sleep(2000)


        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.queryText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(replaceText("<chicle"), closeSoftKeyboard())

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.searchButton), withContentDescription("Botón de búsqueda"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())
        Thread.sleep(11000)

        val recyclerView = onView(
            allOf(
                withId(R.id.medicine_rv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    3
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        Thread.sleep(1000)

        val textView = onView(
            allOf(
                withId(R.id.medicineName), withText("<CHICLE MEDICAMENTOSOS 25 mg ASUCAR"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("<CHICLE MEDICAMENTOSOS 25 mg ASUCAR")))
        Thread.sleep(500)

        val textView2 = onView(
            allOf(
                withId(R.id.name), withText("<CHICLE MEDICAMENTOSOS 25 mg ASUCAR"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("<CHICLE MEDICAMENTOSOS 25 mg ASUCAR")))
        Thread.sleep(500)

        val textView3 = onView(
            allOf(
                withId(R.id.presc), withText("Sin Receta"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Sin Receta")))
        Thread.sleep(500)

        val textView4 = onView(
            allOf(
                withId(R.id.via), withText("USO BUCOFARÍNGEO : CHICLE MEDICAMENTOSO"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("USO BUCOFARÍNGEO : CHICLE MEDICAMENTOSO")))
        Thread.sleep(500)

        val textView5 = onView(
            allOf(
                withId(R.id.dosis), withText("0.025 g"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("0.025 g")))
        Thread.sleep(500)

        val textView6 = onView(
            allOf(
                withId(R.id.lab), withText("Creado por Fleer Española S.L."),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Creado por Fleer Española S.L.")))
        Thread.sleep(500)

        val textView7 = onView(
            allOf(
                withId(R.id.identifiers),
                withText("Nº registro: 1234567\nCódigo nacional: 7654321"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Nº registro: 1234567\nCódigo nacional: 7654321")))
        Thread.sleep(500)

        val appCompatImageView4 = onView(
            allOf(
                withId(R.id.closeButtonImage),
                withContentDescription("Botón que cierra la pantalla actual"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView4.perform(click())

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

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.manageButton), withText("Administrar"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    12
                ),
                isDisplayed()
            )
        )
        appCompatButton4.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(1000)

        runBlocking {
            val medRef = Firebase.database.getReference("CHICLE")
            medRef.child("<").removeValue()
        }

        Thread.sleep(1500)

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    allOf(
                        withId(R.id.cLayoutC),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            4
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText10.perform(click())

        val appCompatEditText11 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    allOf(
                        withId(R.id.cLayoutC),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            4
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText11.perform(replaceText("jose1234"), closeSoftKeyboard())

        //pressBack()
        Thread.sleep(1000)

        val appCompatButton5 = onView(
            allOf(
                withId(R.id.deleteButton), withText("Eliminar cuenta"),
                childAtPosition(
                    allOf(
                        withId(R.id.cLayoutC),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            4
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatButton5.perform(click())
        Thread.sleep(2000)

        val appCompatButton6 = onView(
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
        appCompatButton6.perform(scrollTo(), click())
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
