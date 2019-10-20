package tech.askru.api

data class GetQuestionByIdResponse(
    val error: String?,
    val question: Question?
)
