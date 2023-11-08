package edu.uw.ischool.mbacarro.quizdroid

import android.app.Application
import android.util.Log


class QuizApp : Application() {

    var topicRepository : TopicRepository = InMemoryTopicRepo()

    private val TAG = "QuizApp"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "QuizApp created")
    }
}