package com.example.anotherdaggerapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.example.anotherdaggerapp.api.ILoginApi
import com.example.anotherdaggerapp.data.LoginRequest
import com.example.anotherdaggerapp.data.LoginResponse
import com.example.anotherdaggerapp.login.*
import com.example.anotherdaggerapp.utils.cast
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.verification.Times
import javax.inject.Named
import javax.inject.Singleton


@Singleton
@Component(modules = [TestNetworkModule::class])
interface TestAppComponent : AppComponent{
    override fun newLoginComponent(module: LoginModule): LoginComponent
}

@Module
class TestNetworkModule(val mockLoginApi: ILoginApi){
    @Provides
    @Named("stub")
    fun loginStubApi(): ILoginApi = mockLoginApi
}


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java, false, false)

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }


    @Test
    fun testLoginMockApi() {

        val apiMock = mock<ILoginApi> {
            on { login(LoginRequest("login1@a.ru", "pass1")) } doReturn Observable.just(
                LoginResponse("True_token")
            )
        }

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App
        app.appComponent = DaggerTestAppComponent.builder().testNetworkModule(TestNetworkModule(apiMock)).build()

        activityRule.launchActivity(null)
        onView(withId(R.id.do_login)).perform(click())

        verify(apiMock, Times(1)).login(LoginRequest("login1@a.ru", "pass1"))
    }

    @Test
    fun testLoginMockViewModel() {

        val mock = mock<ILoginViewModel> { on { loginResult } doReturn LceLiveData() }

        LoginActivity.componentFactory = { activity ->
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext.cast<App>().appComponent
                .newLoginComponent(object : LoginModule(activity) {
                    override fun viewModel(loginApi: ILoginApi): ILoginViewModel {
                        return mock
                    }
                })
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.do_login)).perform(click())
        verify(mock, Times(1)).doLogin("login1@a.ru", "pass1")
    }

    @Test
    fun testError() {

        activityRule.launchActivity(null)
        activityRule.activity.runOnUiThread {
            activityRule.activity.loginVM.loginResult.value = Result(
                status = Status.ERROR, error = ValidationError(
                    setOf(ValidationError.Type.EMPTY_EMAIL, ValidationError.Type.EMPTY_PASSWORD)
                )
            )
        }

        onView(withId(R.id.form))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.login_til))
            .check(matches(withError("Must not be empty")))

        onView(withId(R.id.password_til))
            .check(matches(withError("Must not be empty")))
    }
}