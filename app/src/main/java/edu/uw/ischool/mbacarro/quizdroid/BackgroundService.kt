package edu.uw.ischool.mbacarro.quizdroid

import android.app.AlertDialog
import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class BackgroundService : IntentService("BackgroundService") {

    private val TAG: String = "BackgroundService"

    override fun onHandleIntent(intent: Intent?) {
        Log.i(TAG, "Background service started")

        // Get the URL from preferences
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val url = preferences.getString("url_preference", "")

        if (url.isNullOrEmpty()) {
            Log.e(TAG, "URL is not specified in preferences")
            return
        }

        // Display a Toast message with the URL
        showToast("Downloading from: $url")

        // Check if the device is online
        if (isOnline()) {
            // Download the JSON file
            downloadJsonFile(url)
        } else {
            // Display appropriate message based on network state
            if (isAirplaneModeOn()) {
                showAirplaneModeDialog()
            } else {
                showToast("No internet access")
            }
        }
    }

    private fun showToast(message: String) {
        // Display a Toast message on the main thread
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadJsonFile(url: String) {
        try {
            Log.i(TAG, "Attempting Download")

            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()

            // Check if the download was successful
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = BufferedInputStream(connection.inputStream)
                val file = File(filesDir, "questions.json")
                val outputStream = FileOutputStream(file)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                Log.i(TAG, "JSON file downloaded successfully")

            } else {
                Log.e(TAG, "Failed to download JSON file. Response code: ${connection.responseCode}")
                showRetryOrQuitDialog()
            }

            connection.disconnect()
        } catch (e: Exception) {
            Log.e(TAG, "Error downloading JSON file", e)
            showRetryOrQuitDialog()
        }
    }

    private fun isOnline(): Boolean {
        // Check if the device is online
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun isAirplaneModeOn(): Boolean {
        // Check if the device is in Airplane mode
        return Settings.Global.getInt(
            contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    private fun showAirplaneModeDialog() {
        AlertDialog.Builder(this)
            .setTitle("Airplane Mode Detected")
            .setMessage("You are currently in Airplane Mode. Do you want to turn it off?")
            .setPositiveButton("Yes") { _, _ -> navigateToAirplaneModeSettings() }
            .setNegativeButton("No") { _, _ -> /* Handle user choosing not to turn off Airplane Mode */ }
            .show()
    }

    private fun navigateToAirplaneModeSettings() {
        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    private fun showRetryOrQuitDialog() {
        showToast("Failed to download questions. Retry or quit the application.")
    }
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Start the BackgroundService
        context?.startService(Intent(context, BackgroundService::class.java))
    }
}

