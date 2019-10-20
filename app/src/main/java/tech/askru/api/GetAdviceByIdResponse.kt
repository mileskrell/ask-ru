package tech.askru.api

data class GetAdviceByIdResponse(
    val advice: Advice?,
    val error: String?
)

data class Advice(
    val _id: String,
    val user: User, // each of these is an ID for the advice object
    val question: QuestionIdHolder,
    val body: String,
    val __v: Int
)

data class User(
    val _id: String,
    val userName: String
)

data class QuestionIdHolder(val _id: String)
