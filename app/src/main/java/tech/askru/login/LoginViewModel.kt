package tech.askru.login

import androidx.lifecycle.ViewModel
import tech.askru.Repository

class LoginViewModel : ViewModel() {

    private val repository = Repository()

    lateinit var userToken: String

    suspend fun attemptLogin(username: String, password: String): LoginResultType {
        val response = try {
            repository.authenticateUser(username, password)
        } catch (e: RuntimeException) {
            return LoginResultType.ERROR
        }

        // no error, and we're authenticated

        userToken = response.token ?: return LoginResultType.ERROR // return error if somehow null

        return LoginResultType.SUCCESS
    }

    suspend fun attemptRegister(username: String, password: String): RegisterResultType {
        try {
            repository.createUser(username, password)
        } catch (e: RuntimeException)  {
            return RegisterResultType.ERROR
        }

        return RegisterResultType.SUCCESS
    }
}

enum class RegisterResultType {
    SUCCESS,
    ERROR,
}

enum class LoginResultType {
    SUCCESS,
    ERROR, // represents incorrect username OR password
}
