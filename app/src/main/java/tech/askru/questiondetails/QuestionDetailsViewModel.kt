package tech.askru.questiondetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.askru.Repository
import tech.askru.api.GetAdviceByIdResponse
import tech.askru.api.GetQuestionByIdResponse
import tech.askru.api.Question

class QuestionDetailsViewModel : ViewModel() {

    val repository = Repository.instance

    val adviceList = MutableLiveData(listOf<GetAdviceByIdResponse>())

    suspend fun getAdviceForQuestion(question: Question) {
        val newAdviceList = mutableListOf<GetAdviceByIdResponse>()
        try {
            question.advice.forEach { adviceId ->
                newAdviceList.add(repository.getAdviceById(adviceId))
            }
            adviceList.value = newAdviceList

        } catch (e: RuntimeException) {
            adviceList.value = null
        }
    }

    // This is really gross, but hey, it's a hackathon. Hopefully you're not reading this right now.
    suspend fun getAdviceForQuestion2(question: GetQuestionByIdResponse.GetQuestionByIdResponseQuestion) {
        val newAdviceList = mutableListOf<GetAdviceByIdResponse>()
        try {
            question.advice.forEach { advice ->
                newAdviceList.add(repository.getAdviceById(advice._id))
            }
            adviceList.value = newAdviceList

        } catch (e: RuntimeException) {
            adviceList.value = null
        }
    }

    suspend fun addAdviceToQuestion(questionId: String, body: String, userToken: String): Boolean {
        val response = try {
            repository.createAdvice(questionId, body, userToken)
        } catch (e: RuntimeException) {
            return false
        }
        return response.advice != null
    }

    suspend fun refreshQuestionAndAdvice(questionId: String) {
        val newQuestion = try {
            repository.getQuestionById(questionId).question
        } catch (e: RuntimeException) {
            null
        }

        if (newQuestion != null) {
            getAdviceForQuestion2(newQuestion)
        }
    }
}
