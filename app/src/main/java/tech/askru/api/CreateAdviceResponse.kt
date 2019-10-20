package tech.askru.api

data class CreateAdviceResponse(
    val error: String?,
    val advice: CreateAdviceResponseAdvice?
)

data class CreateAdviceResponseAdvice(
    val _id: String,
    val user: String, // user ID
    val question: String, // question ID
    val body: String,
    val __v: Int
)
