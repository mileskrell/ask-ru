package tech.askru.api

import java.io.Serializable

data class Question(
    val advice: List<String>, // each of these is an ID for the advice object
    val _id: String,
    val user: String,
    val title: String,
    val body: String,
    val __v: Int
) : Serializable
