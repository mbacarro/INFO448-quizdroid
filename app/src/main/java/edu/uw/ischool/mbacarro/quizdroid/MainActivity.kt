package edu.uw.ischool.mbacarro.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var mathBtn : Button
    lateinit var physicsBtn : Button
    lateinit var marvelBtn : Button
    lateinit var topicRepository: TopicRepository

    private val TAG: String = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topicRepository = (application as QuizApp).topicRepository

        mathBtn = findViewById(R.id.topic_math)
        physicsBtn = findViewById(R.id.topic_physics)
        marvelBtn = findViewById(R.id.topic_marvel)


        mathBtn.setOnClickListener {
            Log.i(TAG, "Math was selected")
            startTopicOverview("Math")
        }
        physicsBtn.setOnClickListener {
            Log.i(TAG, "Physics was selected")
            startTopicOverview("Physics")
        }
        marvelBtn.setOnClickListener {
            Log.i(TAG, "Marvel was selected")
            startTopicOverview("Mavel Super Heros")
        }

    }

    private fun startTopicOverview(topic: String?) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("topic", topic)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_preferences -> {
                startActivity(Intent(this, PreferencesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}