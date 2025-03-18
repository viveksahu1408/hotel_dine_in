package com.example.practiceapp

import AdapterClasses.Adapters.BookingAdapter
import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class BookingHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookingAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_history)

        sessionManager = SessionManager(this)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBookings)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize empty adapter
        adapter = BookingAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Fetch bookings
        fetchBooking()
    }

    @SuppressLint("CheckResult")
    fun fetchBooking() {
        val hotelDineIn = "user_booking_details"
        val userId = sessionManager.getUserId() ?: "0"

        RetrofitClient.instance.getUserBookings(hotelDineIn.toString(), userId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.status == "200" && !response.bookings.isNullOrEmpty()) {
                    if (!::adapter.isInitialized) {
                        adapter = BookingAdapter(response.bookings.toMutableList()) // Mutable List Pass Karo
                        recyclerView.adapter = adapter
                    } else {
                        adapter.updateList(response.bookings) // Yeh list update karega
                    }
                    Log.d("API_RESPONSE", "Booking List Size: ${response.bookings.size}")
                } else {
                    Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("API_ERROR", "Error: ${error.message}")
                Toast.makeText(this, "API call failed", Toast.LENGTH_SHORT).show()
            })
    }

}
