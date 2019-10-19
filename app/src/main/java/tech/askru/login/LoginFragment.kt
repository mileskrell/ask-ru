package tech.askru.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val savedToken = prefs.getString("token", "")
        if (savedToken != null && savedToken.isNotEmpty()) {
            loginViewModel.userToken = savedToken
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                button_log_in.isEnabled =
                    edit_text_username.text.isNotEmpty() && edit_text_password.text.isNotEmpty()
                button_register.isEnabled =
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
            button_register.visibility = View.INVISIBLE
            progress_bar_trying_login.visibility = View.VISIBLE

            loginViewModel.viewModelScope.launch {
                tryLogin()
            }
        }

        button_register.setOnClickListener {
            hideSoftKeyboard()
            button_log_in.visibility = View.INVISIBLE
            button_register.visibility = View.INVISIBLE
            progress_bar_trying_login.visibility = View.VISIBLE

            loginViewModel.viewModelScope.launch {
                tryRegister()
            }
        }

    }

    private suspend fun tryRegister() {
        val registerResultType = loginViewModel.attemptRegister(
            edit_text_username.text.toString(),
            edit_text_password.text.toString()
        )
        when (registerResultType) {
            RegisterResultType.SUCCESS -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Successfully registered account. Attempting login...",
                        Toast.LENGTH_SHORT
                    ).show()
                    delay(1000)
                }
                tryLogin()
            }
            RegisterResultType.ERROR -> {
                progress_bar_trying_login.visibility = View.INVISIBLE
                button_log_in.visibility = View.VISIBLE
                button_register.visibility = View.VISIBLE
                Toast.makeText(
                    context,
                    "Username is taken - please choose a different one",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private suspend fun tryLogin() {
        val loginResultType = loginViewModel.attemptLogin(
            edit_text_username.text.toString(),
            edit_text_password.text.toString()
        )
        withContext(Dispatchers.Main) {
            when (loginResultType) {
                LoginResultType.SUCCESS -> {
                    Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
                    PreferenceManager.getDefaultSharedPreferences(context).edit {
                        putString("token", loginViewModel.userToken)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                LoginResultType.ERROR -> {
                    progress_bar_trying_login.visibility = View.INVISIBLE
                    button_log_in.visibility = View.VISIBLE
                    button_register.visibility = View.VISIBLE
                    Toast.makeText(
                        context,
                        "Incorrect username or password. Please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
