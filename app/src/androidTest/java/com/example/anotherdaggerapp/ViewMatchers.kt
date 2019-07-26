package com.example.anotherdaggerapp

import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * Created by vitaly on 2019-07-26.
 */


fun withError(error: String) = object : TypeSafeMatcher<View?>() {
    override fun describeTo(description: Description?) {
        description?.appendText("EditText with error: ")
        description?.appendValue(error)
    }

    override fun matchesSafely(item: View?): Boolean {
        return (item as TextInputLayout).error == error
    }

}
