package com.example.practiceapp

import AdapterClasses.Adapters.RestaurantFCAdapter
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

class ResListFCActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : RestaurantFCAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_res_list_fcactivity)

        recyclerView = findViewById(R.id.recyfoodcategory)
        recyclerView.layoutManager = LinearLayoutManager(this)

      //  val restaurantId = intent.getStringExtra("restaurant_id")
        val foodId = intent.getStringExtra("foodcategory_id")

        fetchRestaurants()

    }

    @SuppressLint("CheckResult")
    private fun fetchRestaurants() {
       // val foodId = 1
        val foodId = intent.getStringExtra("foodcategory_id")

        RetrofitClient.instance.getRestaurantsfc("food_restro", foodId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("API_RESPONSE", "Response: ${response}")

                if (response.status == "success" && response.restaurants != null) {
                    adapter = RestaurantFCAdapter(this,response.restaurants)
                    recyclerView.adapter = adapter
                    Log.d("FOOD_ID", "Response: ${foodId}")
                } else {
                    Toast.makeText(this, response.status, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("API_ERROR", "Error: ${error.message}")
                Toast.makeText(this, "API call failed", Toast.LENGTH_SHORT).show()
            })
    }
}