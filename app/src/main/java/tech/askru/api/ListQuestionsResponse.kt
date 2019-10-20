package tech.askru.api

data class ListQuestionsResponse(
    val questions: List<ReducedQuestion>
) {
    class ReducedQuestion(
        val _id: String,
        val title: String,
        val body: String
    )
}
