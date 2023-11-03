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

    private val TAG: String = "TopicsOverviewActivity"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)
        Log.i(TAG, "Topic Overview was launched")


        // getting passed through data
        val topic = intent.getStringExtra("topic")
        val desc = intent.getStringExtra("desc")
        val questions = intent.getStringArrayListExtra("questions")
        val questNum = questions?.size

        Log.i(TAG, "num of questions $questNum and questions: $questions")


        // getting view elements
        beginBtn = findViewById(R.id.begin_button)
        topicDesc = findViewById(R.id.topic_description)
        totalQuestions = findViewById(R.id.total_questions)

        // changing topic overview based on data
        topicDesc.text = "Topic: $topic. $desc"
        totalQuestions.text = "Total Questons: $questNum"


        // start the questions activity
        beginBtn.setOnClickListener {
            Log.i(TAG, "Begin button was pressed")
            startActivity(Intent(this, QuestionActivity::class.java).apply {
                putStringArrayListExtra("questions", questions)
                putExtra("currentQuestionIndex", 0)
            })
        }
    }
}