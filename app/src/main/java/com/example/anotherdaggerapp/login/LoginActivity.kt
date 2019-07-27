package com.example.anotherdaggerapp.login

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.anotherdaggerapp.App
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.Result
import com.example.anotherdaggerapp.Status
import com.example.anotherdaggerapp.data.LoginResponse
import com.example.anotherdaggerapp.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    companion object {

        @VisibleForTesting
        var componentFactory: (activity: LoginActivity) -> BaseLoginComponent =
            { activity ->
                (activity.applicationContext as App).appComponent.newLoginComponent(LoginModule(activity))
            }
    }

    @Inject
    lateinit var loginVM: ILoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        componentFactory(this).inject(this)

        do_login.setOnClickListener {
            loginVM.doLogin(login.text.toString(), password.text.toString())
        }

        loginVM.loginResult.observe(
            this, Observer {
                showLoading(it.status == Status.LOADING)
                if (it.status == Status.SUCCESS) onSuccessLogin(it)
                if (it.status == Status.ERROR) showError(it.error)
            }
        )
    }

    private fun showLoading(isLoading: Boolean) {
        progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        form.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun onSuccessLogin(result: Result<LoginResponse>) {
        toast("Success ${result.data?.token}")
    }

    private fun showError(t: Throwable?) {
        if (t is ValidationError) handleValidationError(t)
        else toast("ERROR ${t?.message}")
    }

    private fun handleValidationError(error: ValidationError) {
        login_til.error = null;
        password_til.error = null
        error.types.forEach {
            when (it) {
                ValidationError.Type.EMPTY_PASSWORD -> password_til.error = "Must not be empty"
                ValidationError.Type.WRONG_PASSWORD -> password_til.error = "Wrong password"
                ValidationError.Type.EMPTY_EMAIL -> login_til.error = "Must not be empty"
                ValidationError.Type.INVALID_EMAIL -> login_til.error = "Invalid email"
            }
        }
    }
}

