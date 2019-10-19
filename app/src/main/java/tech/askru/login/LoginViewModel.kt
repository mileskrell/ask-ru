package tech.askru.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // we can observe this from LoginFragment to let us know when we've tried to log in
    val numLoginTries = MutableLiveData(0)

    suspend fun attemptLogin(username: String, password: String): Boolean {
        delay(3000)
        return false
    }
}
