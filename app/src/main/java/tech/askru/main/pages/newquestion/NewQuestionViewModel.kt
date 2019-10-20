package tech.askru.main.pages.newquestion

import androidx.lifecycle.ViewModel
import tech.askru.Repository
import tech.askru.api.CreateQuestionResponse

class NewQuestionViewModel : ViewModel() {

    private val repository = Repository.instance

    /**
     * @return true if question created, false if an error
     */
    suspend fun createQuestion(title: String, body: String, userToken: String): Boolean {
        return try {
            repository.createQuestion(title, body, userToken).error == null
        } catch (e: RuntimeException) {
            false
        }
    }
}
