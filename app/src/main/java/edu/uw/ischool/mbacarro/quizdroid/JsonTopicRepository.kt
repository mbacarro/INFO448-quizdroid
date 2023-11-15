// JsonTopicRepo.kt
package edu.uw.ischool.mbacarro.quizdroid

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonTopicRepo(private val context: Context) : TopicRepository {

    override fun getAllTopics(): List<Topic> {
        val jsonString = context.assets.open("data/questions.json").bufferedReader().use { it.readText() }

        val topicsType = object : TypeToken<List<Topic>>() {}.type
        return Gson().fromJson(jsonString, topicsType)
    }

    override fun getTopicByTitle(title: String?): Topic? {
        return getAllTopics().find { it.title == title }
    }
}
