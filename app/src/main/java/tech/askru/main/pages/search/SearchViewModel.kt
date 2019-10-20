package tech.askru.main.pages.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.askru.Repository
import tech.askru.api.GetQuestionByIdResponse
import tech.askru.api.Question

class SearchViewModel : ViewModel() {
    private val repository = Repository.instance
    val searchResults = MutableLiveData(listOf<Question>())

    var searchText: String = ""

    suspend fun searchQuestions(searchText: String) {
        val result = repository.searchQuestions(searchText)
        searchResults.value = result.questions
    }

    suspend fun listQuestions() {
        val result = repository.listQuestions()
        val newQuestions = result.questions.map {
            getQuestionById(it._id)
        }
        searchResults.value = newQuestions.map {
            Question(
                it.question!!.advice.map { it._id },
                it.question._id,
                it.question.user._id,
                it.question.title,
                it.question.body,
                it.question.__v
            )
        }
    }

    suspend fun getQuestionById(id: String): GetQuestionByIdResponse {
        return repository.getQuestionById(id)
    }
}
