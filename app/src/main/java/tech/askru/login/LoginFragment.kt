package tech.askru.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.askru.R
import tech.askru.hideSoftKeyboard

class LoginFragment : Fragment() {

    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                button_log_in.isEnabled =
                    edit_text_username.text.isNotEmpty() && edit_text_password.text.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        edit_text_username.addTextChangedListener(loginTextWatcher)
        edit_text_password.addTextChangedListener(loginTextWatcher)

        button_log_in.setOnClickListener {
            hideSoftKeyboard()
            button_log_in.visibility = View.INVISIBLE
            progress_bar_trying_login.visibility = View.VISIBLE

            GlobalScope.launch {
                val logInSuccessful = loginViewModel.attemptLogin(
                    edit_text_username.text.toString(),
                    edit_text_password.text.toString()
                )
                activity?.runOnUiThread {
                    if (logInSuccessful) {
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
                    } else {
                        progress_bar_trying_login.visibility = View.INVISIBLE
                        button_log_in.visibility = View.VISIBLE
                        Toast.makeText(
                            context,
                            "Error logging in. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
