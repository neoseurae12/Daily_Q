package online.dailyq.api.response

import java.util.Date

data class Question(
    val id: String,
    val text: String,
    val answerCount: Int,
    val updatedAt: Date,
    val createdAt: Date
)