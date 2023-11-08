package edu.uw.ischool.mbacarro.quizdroid

interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopicByTitle(title: String?): Topic?
}

data class Topic (
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val questions: List<Quiz>
)
data class Quiz(
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)