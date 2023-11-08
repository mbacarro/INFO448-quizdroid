package edu.uw.ischool.mbacarro.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.util.Log

class TopicOverviewActivity : AppCompatActivity() {

    lateinit var beginBtn : Button
    lateinit var topicDesc : TextView
    lateinit var totalQuestions : TextView
    lateinit var topicRepository: TopicRepository

    private val TAG: String = "TopicsOverviewActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)
        Log.i(TAG, "Topic Overview was launched")


        // getting passed through data
        val topic = intent.getStringExtra("topic")

        topicRepository = (application as QuizApp).topicRepository

        val topicDetails = topicRepository.getTopicByTitle(topic)


        // getting view elements
        beginBtn = findViewById(R.id.begin_button)
        topicDesc = findViewById(R.id.topic_description)
        totalQuestions = findViewById(R.id.total_questions)

        if (topicDetails != null) {
            topicDesc.text = "Topic: ${topicDetails.title}. ${topicDetails.longDescription}"
            totalQuestions.text = "Total Number of Questions: ${topicDetails.questions.size}"

            // start the questions activity
            beginBtn.setOnClickListener {
                Log.i(TAG, "Begin button was pressed")
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("topic", topic)
                startActivity(intent)
            }
        }



    }
}