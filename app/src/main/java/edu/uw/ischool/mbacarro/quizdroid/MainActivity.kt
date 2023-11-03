package edu.uw.ischool.mbacarro.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var mathBtn : Button
    lateinit var physicsBtn : Button
    lateinit var marvelBtn : Button

    private val TAG: String = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mathBtn = findViewById(R.id.topic_math)
        physicsBtn = findViewById(R.id.topic_physics)
        marvelBtn = findViewById(R.id.topic_marvel)

        val mathQuestions = mathQuestions()
        val physicsQuestions = physicsQuestions()
        val marvelQuestions = marvelQuestions()


        val topics = mapOf(
            "Math" to mathQuestions,
            "Physics" to physicsQuestions,
            "Marvel" to marvelQuestions
        )

        Log.i(TAG, "Math Questions $mathQuestions")
        Log.i(TAG, "Physics Questions $physicsQuestions")
        Log.i(TAG, "Marvel Questions $marvelQuestions")


        mathBtn.setOnClickListener {
            val description = "this is the math quiz"
            Log.i(TAG, "Math was selected")
            startTopicOverview("Math", description, topics["Math"])
        }
        physicsBtn.setOnClickListener {
            val description = "this is the physics quiz"
            Log.i(TAG, "Physics was selected")
            startTopicOverview("Physics", description, topics["Physics"])
        }
        marvelBtn.setOnClickListener {
            val description = "this is the marvel super hero quiz"
            Log.i(TAG, "Marvel was selected")
            startTopicOverview("Mavel Super Heros", description, topics["Marvel"])
        }

    }


    private fun startTopicOverview(topic: String, desc: String, questions: List<String>?) {
        if (questions != null) {
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("topic", topic)
            intent.putExtra("desc", desc)
            intent.putStringArrayListExtra("questions", ArrayList(questions))
            startActivity(intent)
        }
    }

    private fun mathQuestions(): List<String> {
        val questions = ArrayList<String>()
        questions.add(
            Gson().toJson(
                mapOf(
                    "question" to "What is 2 + 2?",
                    "answers" to listOf("3", "4", "5", "6"),
                    "correctAnswerIndex" to 1
                )
            )
        )
        questions.add(
            Gson().toJson(
                mapOf(
                    "question" to "What is 4 + 2?",
                    "answers" to listOf("3", "4", "5", "6"),
                    "correctAnswerIndex" to 3
                )
            )
        )
        // Add more math questions
        return questions
    }

    private fun physicsQuestions(): List<String> {
        val questions = ArrayList<String>()
        questions.add(
            Gson().toJson(
                mapOf(
                    "question" to "What is the speed of light?",
                    "answers" to listOf("100 m/s", "299,792,458 m/s", "1,000 m/s", "50,000 m/s"),
                    "correctAnswerIndex" to 1
                )
            )
        )
        // Add more physics questions
        return questions
    }

    private fun marvelQuestions(): List<String> {
        val questions = ArrayList<String>()
        questions.add(
            Gson().toJson(
                mapOf(
                    "question" to "Who is the leader of the Avengers?",
                    "answers" to listOf("Thor", "Hulk", "Iron Man", "Captain America"),
                    "correctAnswerIndex" to 3
                )
            )
        )
        // Add more Marvel questions
        return questions
    }
}