package tech.askru

import androidx.fragment.app.Fragment
import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Fragment.hideSoftKeyboard() {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
}
