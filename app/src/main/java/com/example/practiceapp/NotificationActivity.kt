package com.example.practiceapp

import AdapterClasses.Adapters.NotificationAdapter
import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class NotificationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        recyclerView = findViewById(R.id.recyclerViewNotifications)
        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = NotificationAdapter(emptyList())
        recyclerView.adapter = adapter

        sessionManager = SessionManager(this)
        fetchNotifications()
    }

    @SuppressLint("CheckResult")
    private fun fetchNotifications() {
        val hotelDineIn = "notification".toRequestBody("text/plain".toMediaTypeOrNull())
        val userId =
            sessionManager.getUserId()?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: return

        RetrofitClient.instance.getNotifications(hotelDineIn, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(
                    "API_RESPONSE",
                    "Status: ${response.status}, Message: ${response.message}, Notifications: ${response.notifications}"
                )

                if (response.status == 200 && !response.notifications.isNullOrEmpty()) {
                    adapter.updateList(response.notifications)
                } else {
                    Toast.makeText(
                        this,
                        response.message ?: "No notifications available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, { error ->
                Toast.makeText(this, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error: ${error.message}")
            })
    }
}