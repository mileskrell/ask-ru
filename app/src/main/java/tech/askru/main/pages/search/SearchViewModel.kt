package tech.askru.main.pages.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.askru.Repository
import tech.askru.api.Question

class SearchViewModel : ViewModel() {
    private val repository = Repository.instance
    val searchResults = MutableLiveData(listOf<Question>())

    suspend fun searchQuestions(searchText: String) {
        val result = repository.searchQuestions(searchText)
        searchResults.value = result.questions
    }

    suspend fun getQuestionById(id: String) {
        val result = repository.getQuestionById(id)
    }
}
