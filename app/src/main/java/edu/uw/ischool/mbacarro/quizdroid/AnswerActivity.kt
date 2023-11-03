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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        Log.i(TAG, "AnswerActivity was launched")

        val yourAnswerText = findViewById<TextView>(R.id.your_answer)
        val correctAnswerText = findViewById<TextView>(R.id.correct_answer)
        val correctCountText = findViewById<TextView>(R.id.correct_count)
        val nextButton = findViewById<Button>(R.id.next_button)
        val finishButton = findViewById<Button>(R.id.finish_button)

        val userSelectedAnswer = intent.getStringExtra("userSelectedAnswer")
        val correctAnswer = intent.getStringExtra("correctAnswer")
        val correctAnswersTotal = intent.getIntExtra("correctAnswersTotal", 0)
        val questNum = intent.getIntExtra("questNum", 0)
        val currentQuestionIndex = intent.getIntExtra("currentQuestionIndex", 0)
        var questions = intent.getStringArrayListExtra("questions")


        Log.i(TAG, "CurrentQuestionIndex: $currentQuestionIndex")

        yourAnswerText.text = userSelectedAnswer
        correctAnswerText.text = correctAnswer
        val currentScore = "You have $correctAnswersTotal out of $questNum correct"
        correctCountText.text = currentScore
//
        if (currentQuestionIndex < questNum - 1) {
            nextButton.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)

                intent.putStringArrayListExtra("questions", questions)
                intent.putExtra("currentQuestionIndex", currentQuestionIndex + 1)
                intent.putExtra("correctAnswersTotal", correctAnswersTotal)

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

