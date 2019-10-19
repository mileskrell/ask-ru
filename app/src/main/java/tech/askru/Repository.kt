package tech.askru

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.askru.api.AskRUService
import tech.askru.api.AuthenticateUserResponse
import tech.askru.api.CreateUserResponse


class Repository {
    val service: AskRUService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://askru-hackru.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(AskRUService::class.java)
    }

    suspend fun authenticateUser(username: String, password: String): AuthenticateUserResponse {
        return service.authenticateUserAsync(username, password)
    }

    suspend fun createUser(username: String, password: String): CreateUserResponse {
        return service.createUserAsync(username, password)
    }
}
