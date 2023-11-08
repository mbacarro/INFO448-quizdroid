package edu.uw.ischool.mbacarro.quizdroid

class InMemoryTopicRepo : TopicRepository {
    private val topics: List<Topic> = listOf(
        Topic(
            title = "Math",
            shortDescription = "Math Quiz",
            longDescription = "This math quiz will consist of n math questions",
            questions = listOf(
                Quiz(
                    question = "What is 2 + 2?",
                    answers = listOf("3", "4", "5", "6"),
                    correctAnswerIndex = 1
                ),
                Quiz(
                    question = "What is 2 + 3?",
                    answers = listOf("3", "4", "5", "6"),
                    correctAnswerIndex = 2
                ),
            )
        ),
        Topic(
            title = "Physics",
            shortDescription = "Physics Quiz",
            longDescription = "This phsyics quiz will consist of n physics questions",
            questions = listOf(
                Quiz(
                    question = "What is the acceleration of gravity in m/s^2?",
                    answers = listOf("7.8", "8.8", "9.8", "10.8"),
                    correctAnswerIndex = 2
                ),
                Quiz(
                    question = "What is the speed of light??",
                    answers = listOf("100 m/s", "299,792,458 m/s", "1,000 m/s", "50,000 m/s"),
                    correctAnswerIndex = 1
                ),
            )
        ),
        Topic(
            title = "Marvel",
            shortDescription = "Marvel Super Heroes Quiz",
            longDescription = "This Marvel Super Heroes quiz will consist of n Marvel questions",
            questions = listOf(
                Quiz(
                    question = "Who is the leader of the Avengers?",
                    answers = listOf("Thor", "Hulk", "Iron Man", "Captain America"),
                    correctAnswerIndex = 3
                ),
            )
        )
    )

    override fun getAllTopics(): List<Topic> {
        return topics
    }

    override fun getTopicByTitle(title: String?): Topic? {
        return topics.find { it.title == title}
    }
}