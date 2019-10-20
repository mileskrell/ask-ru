package tech.askru.api

data class GetQuestionByIdResponse(
    val error: String?,
    val question: GetQuestionByIdResponseQuestion?
) {
    data class GetQuestionByIdResponseQuestion(
        val advice: List<GetQuestionByIdResponseQuestionAdvice>,
        val _id: String,
        val user: User,
        val title: String,
        val body: String,
        val __v: Int
    ) {
        data class GetQuestionByIdResponseQuestionAdvice(
            val _id: String,
            val user: User,
            val body: String
        )
    }
}
