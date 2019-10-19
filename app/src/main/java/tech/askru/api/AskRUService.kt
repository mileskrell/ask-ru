package tech.askru.api

import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AskRUService {

    @POST("users/create")
    suspend fun createUserAsync(@Query("userName") username: String, @Query("password") password: String): CreateUserResponse

    @POST("users/authenticate")
    suspend fun authenticateUserAsync(@Query("userName") username: String, @Query("password") password: String): AuthenticateUserResponse

//    @POST("users/list")
//    suspend fun listUsersAsync(): List<User>
//
//    @POST("questions/list")
//    suspend fun listQuestionsAsync(): List<User>
//
//    @POST("questions/create")
//    suspend fun createQuestion(): Boolean
//
//    @POST("questions/search")
//    suspend fun searchQuestions(): List<User>
}
