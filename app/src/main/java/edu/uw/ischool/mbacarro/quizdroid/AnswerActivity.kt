package edu.uw.ischool.mbacarro.quizdroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import com.google.gson.Gson

class AnswerActivity : AppCompatActivity() {
    private val TAG: String = "AnswerActivity"
    lateinit var topicRepository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        Log.i(TAG, "AnswerActivity was launched")

        // get Views
        val yourAnswerText = findViewById<TextView>(R.id.your_answer)
        val correctAnswerText = findViewById<TextView>(R.id.correct_answer)
        val correctCountText = findViewById<TextView>(R.id.correct_count)
        val nextButton = findViewById<Button>(R.id.next_button)
        val finishButton = findViewById<Button>(R.id.finish_button)

        // getting data
        topicRepository = (application as QuizApp).topicRepository
        val topic = intent.getStringExtra("topic")
        val topicDetails = topicRepository.getTopicByTitle(topic)
        val selectedAnswerIndex = intent.getIntExtra("selectedAnswerIndex", 0)
        val correctAnswersTotal = intent.getIntExtra("correctAnswersTotal", 0)
        val currentQuestionIndex = intent.getIntExtra("currentQuestionIndex", 0)
        val currentQuestion = topicDetails?.questions?.get(currentQuestionIndex)
        val questNum = topicDetails?.questions?.size


        Log.i(TAG, "CurrentQuestionIndex: $currentQuestionIndex")

        yourAnswerText.text = currentQuestion?.answers?.get(selectedAnswerIndex)
        correctAnswerText.text = currentQuestion?.answers?.get(currentQuestion.correctAnswerIndex)


        val currentScore = "You have $correctAnswersTotal out of $questNum correct"
        correctCountText.text = currentScore

        if (questNum != null) {
            if (currentQuestionIndex < questNum - 1) {

                nextButton.setOnClickListener {
                    val intent = Intent(this, QuestionActivity::class.java)

                    intent.putExtra("currentQuestionIndex", currentQuestionIndex + 1)
                    intent.putExtra("correctAnswersTotal", correctAnswersTotal)
                    intent.putExtra("topic", topic)
                    startActivity(intent)
                }
            } else {
                nextButton.visibility = View.GONE
                finishButton.visibility = View.VISIBLE
                finishButton.setOnClickListener {
                    // Go back to the main activity or perform any other desired action
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}

