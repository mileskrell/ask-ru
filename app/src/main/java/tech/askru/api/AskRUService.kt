package tech.askru.api

import retrofit2.http.*

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

    @GET("questions/search")
    suspend fun searchQuestions(@Query("query") query: String): SearchQuestionsResponse

    @GET("questions/list")
    suspend fun listQuestions(): ListQuestionsResponse

    @GET("questions/oneById")
    suspend fun getQuestionById(@Query("_id") id: String): GetQuestionByIdResponse

    @GET("advice/oneById")
    suspend fun getAdviceById(@Query("_id") id: String): GetAdviceByIdResponse

    /*
    You pass in the title and body (IN THE BODY), and the user's token as a header.
    You get back:
    success -> question { _id: "aLongString" } }
    error -> HTTP 400 and { error: "errorMessage" }
     */
    @POST("questions/create")
    suspend fun createQuestion(@Body createQuestionBody: CreateQuestionBody, @Header("x-auth-token") userToken: String): CreateQuestionResponse

    @POST("advice/create")
    suspend fun createAdvice(@Body createAdviceBody: CreateAdviceBody, @Header("x-auth-token") userToken: String): CreateAdviceResponse
}
