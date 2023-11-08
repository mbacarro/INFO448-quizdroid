package edu.uw.ischool.mbacarro.quizdroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.ArrayList

class QuestionActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var correctAnswersTotal = 0
    private val TAG: String = "TopicsOverviewActivity"
    lateinit var topicRepository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        Log.i(TAG, "Question Activity was launched")

        val answerGroup = findViewById<RadioGroup>(R.id.answer_group)
        val submitButton = findViewById<Button>(R.id.submit_button)


        currentQuestionIndex = intent.getIntExtra("currentQuestionIndex", 0)
        correctAnswersTotal = intent.getIntExtra("correctAnswersTotal", 0)
        Log.i(TAG, "Current Index: ${currentQuestionIndex} and Current Correct: $correctAnswersTotal")

        val topic = intent.getStringExtra("topic")
        topicRepository = (application as QuizApp).topicRepository

        val topicDetails = topicRepository.getTopicByTitle(topic)

        Log.i(TAG, "Current Set of Questions: ${topicDetails?.questions}")
        Log.i(TAG, "Number of Questions: ${topicDetails?.questions?.size}")



        if (topicDetails != null && currentQuestionIndex < topicDetails.questions.size) {
            displayQuestion(topicDetails, currentQuestionIndex)
            val currentQuestion = topicDetails?.questions?.get(currentQuestionIndex)

            Log.i(TAG, "Correct Index: ${currentQuestion?.correctAnswerIndex}")

            // Log the selected answer's ID when it changes
            answerGroup.setOnCheckedChangeListener { group, checkedId ->
                Log.i(TAG, "Selected Answer ID: $checkedId")
            }


            submitButton.setOnClickListener {
                var selectedAnswerIndex = answerGroup.checkedRadioButtonId

                if (selectedAnswerIndex == -1) {
                    // No answer selected, do nothing
                    Log.i(TAG, "No answer selected")

                    return@setOnClickListener
                }
                var correct = (selectedAnswerIndex == currentQuestion?.correctAnswerIndex )
                if (correct) {
                    correctAnswersTotal++
                }

                val intent = Intent(this, AnswerActivity::class.java).apply {
                    putExtra("selectedAnswerIndex", selectedAnswerIndex)
                    putExtra("topic", topic)
                    putExtra("correctAnswersTotal", correctAnswersTotal)
                    putExtra("currentQuestionIndex", currentQuestionIndex)
                }
               startActivity(intent)


            }
        }
    }

    private fun displayQuestion(topicDetails: Topic?, currentQuestionIndex: Int) {
        val currentQuestion = topicDetails?.questions?.get(currentQuestionIndex)

        val questionText = findViewById<TextView>(R.id.question_text)
        val answerGroup = findViewById<RadioGroup>(R.id.answer_group)

        if (currentQuestion != null) {
            questionText.text = currentQuestion.question
        }
        answerGroup.removeAllViews()

        val answers = currentQuestion?.answers

        if (answers != null) {
            for ((index, answer) in answers.withIndex()) {
                val radioButton = RadioButton(this)
                radioButton.text = answer
                radioButton.id = index
                answerGroup.addView(radioButton)
            }
        }
    }
}
