package com.example.practiceapp

import AdapterClasses.Adapters.BookingAdapter
import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class BookingHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_history)

        recyclerView = findViewById(R.id.recyclerViewBookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchBooking()

    }
    @SuppressLint("CheckResult")
    fun fetchBooking(){
        val hotelDineIn = MultipartBody.Part.createFormData("hotel_dine_in", "user_booking_details")
        val userId = MultipartBody.Part.createFormData("user_id", "1")

        RetrofitClient.instance.getUserBookings(hotelDineIn, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {response ->

                if (response.status == "200" && response.bookings != null) {
                    adapter = BookingAdapter(response.bookings)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("API_ERROR", "Error: ${error.message}")
                Toast.makeText(this, "API call failed", Toast.LENGTH_SHORT).show()
            })

}}