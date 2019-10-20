package tech.askru.api

data class Question(
    val advice: List<Advice>,
    val id: String,
    val user: String,
    val title: String,
    val body: String,
    val __v: Int
)

//data class User(
//    val id: String,
//    val userName: String
//)

data class Advice(
    val temp: String
)
