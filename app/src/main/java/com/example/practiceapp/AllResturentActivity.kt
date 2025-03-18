package com.example.practiceapp

import AdapterClasses.Adapters.AllRestaurantAdapter
import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class AllResturentActivity : AppCompatActivity() {


    private lateinit var restaurantAdapter: AllRestaurantAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_resturent2)

        recyclerView = findViewById(R.id.allresrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchRestaurants()

    }

    @SuppressLint("CheckResult")
    private fun fetchRestaurants() {
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "all_restaurants")

        RetrofitClient.instance.getAllRestaurants(requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.status == 200 && response.restaurants != null) {
                    restaurantAdapter = AllRestaurantAdapter(response.restaurants)
                    recyclerView.adapter = restaurantAdapter
                } else {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

    }
    }


