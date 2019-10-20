package tech.askru

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.askru.api.*


class Repository {

    companion object {
        val instance = Repository()
    }

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

    suspend fun searchQuestions(query: String): SearchQuestionsResponse {
        return service.searchQuestions(query)
    }

    suspend fun listQuestions(): ListQuestionsResponse {
        return service.listQuestions()
    }

    suspend fun getQuestionById(id: String): GetQuestionByIdResponse {
        return service.getQuestionById(id)
    }

    suspend fun getAdviceById(id: String): GetAdviceByIdResponse {
        return service.getAdviceById(id)
    }

    suspend fun createQuestion(title: String, body: String, userToken: String): CreateQuestionResponse {
        return service.createQuestion(CreateQuestionBody(title, body), userToken)
    }

    suspend fun createAdvice(questionId: String, adviceBody: String, userToken: String): CreateAdviceResponse {
        return service.createAdvice(CreateAdviceBody(questionId, adviceBody), userToken)
    }
}
