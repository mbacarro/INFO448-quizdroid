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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        Log.i(TAG, "Question Activity was launched")

        currentQuestionIndex = intent.getIntExtra("currentQuestionIndex", 0)
        correctAnswersTotal = intent.getIntExtra("correctAnswersTotal", 0)


        var questions = intent.getStringArrayListExtra("questions")
        val questNum = questions?.size
        Log.i(TAG, "Question: $questions")

        val currentQuestion = Gson().fromJson(questions!![currentQuestionIndex], Map::class.java) as Map<String, Any>
        var answerIndex = currentQuestion["correctAnswerIndex"]
        Log.i(TAG, "Current Question: $currentQuestion")
        Log.i(TAG, "Correct Index: $answerIndex")

        val answerGroup = findViewById<RadioGroup>(R.id.answer_group)
        val submitButton = findViewById<Button>(R.id.submit_button)

        if (questions != null && currentQuestionIndex < questions!!.size) {
            displayQuestion(currentQuestionIndex, questions)

            answerGroup.setOnCheckedChangeListener { group, checkedId ->
                // Log the selected answer's ID when it changes
                Log.i(TAG, "Selected Answer ID: $checkedId")
            }

            submitButton.setOnClickListener {
                var selectedAnswerIndex = answerGroup.checkedRadioButtonId

                if (selectedAnswerIndex == -1) {
                    // No answer selected, do nothing
                    Log.i(TAG, "No answer selected")

                    return@setOnClickListener
                }
                // convert to double to check with correct answer
                var selectedAnswerDouble = selectedAnswerIndex.toDouble()
                var correct = (selectedAnswerDouble == answerIndex)
                Log.i(TAG, "Selected Answer ID on Button Press: $selectedAnswerDouble")
                Log.i(TAG, "Correct index = $answerIndex")
                Log.i(TAG, "Is correct: $correct")

                if (correct) {
                    correctAnswersTotal++
                }

                // display the answer user gave
                val answers = currentQuestion["answers"] as List<String>
                val userSelectedAnswer = answers[selectedAnswerIndex]
                Log.i(TAG, "User selected: $userSelectedAnswer")
                // the correct answer

                val correctAnswer = answers[answerIndex.toString().toDouble().toInt()]
                Log.i(TAG, "Correct Answer: $correctAnswer")

                // current score (correct / incorrect)
                Log.i(TAG, "Current Score: $correctAnswersTotal out of $questNum")

                // next button


                val intent = Intent(this, AnswerActivity::class.java).apply {
                    putExtra("userSelectedAnswer", userSelectedAnswer)
                    putExtra("correctAnswer", correctAnswer)
                    putExtra("correctAnswersTotal", correctAnswersTotal)
                    putExtra("currentQuestionIndex", currentQuestionIndex)

                    putExtra("questNum", questNum)

                    putStringArrayListExtra("questions", questions)


                }
               startActivity(intent)
            }
        }
    }

    private fun displayQuestion(questionIndex: Int, questions: ArrayList<String>?) {
        val currentQuestionMap = Gson().fromJson(questions!![questionIndex], Map::class.java) as Map<String, Any>
        val questionText = findViewById<TextView>(R.id.question_text)
        val answerGroup = findViewById<RadioGroup>(R.id.answer_group)

        questionText.text = currentQuestionMap["question"] as String
        answerGroup.removeAllViews()

        val answers = currentQuestionMap["answers"] as List<String>

        for ((index, answer) in answers.withIndex()) {
            val radioButton = RadioButton(this)
            radioButton.text = answer
            radioButton.id = index
            answerGroup.addView(radioButton)
        }
    }
}
