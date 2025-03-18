package com.example.practiceapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ApiClasses.RetrofitClient

import android.content.Intent
import android.widget.Button
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var restaurantImage: ImageView
    private lateinit var restaurantName: TextView
    private lateinit var restaurantPrice: TextView
    private lateinit var restaurantTime: TextView
    private lateinit var restaurantRating: TextView
    private lateinit var restaurantDescription: TextView
    private lateinit var restaurantCategories: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)


        restaurantImage = findViewById(R.id.restaurantImage)
        restaurantName = findViewById(R.id.restaurantName)
        restaurantPrice = findViewById(R.id.restaurantPrice)
        restaurantTime = findViewById(R.id.restaurantTime)
        restaurantRating = findViewById(R.id.restaurantRating)
        restaurantDescription = findViewById(R.id.restaurantDescription)
        restaurantCategories = findViewById(R.id.restaurantCategories)
       val  bookBtn = findViewById<Button>(R.id.bookBtn)



        val restaurantId = intent.getStringExtra("restaurant_id")

        if (!restaurantId.isNullOrEmpty()) {
            restaurantId.toIntOrNull()?.let { id ->
                fetchRestaurantDetails(id) // API Call
            } ?: run {
                Toast.makeText(this, "Invalid Restaurant ID", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Error: No Restaurant ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        bookBtn.setOnClickListener {
            val intent = Intent(this@RestaurantDetailsActivity,BookingActivity::class.java)
            intent.putExtra("restaurant_id", restaurantId)
            startActivity(intent)
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchRestaurantDetails(restaurantId: Int) {
        RetrofitClient.instance.getRestaurantDetails("single_restaurant", restaurantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("API Response", response.toString()) // Debugging ke liye

                if (response.status == 200 && response.data != null) {
                    val restaurant = response.data

                    // Set Data in Views
                    restaurantName.text = restaurant.restaurant_name ?: "No Name"
                    restaurantPrice.text = "Price: ₹${restaurant.restaurant_price ?: "N/A"}"
                    restaurantTime.text = "Timing: ${restaurant.restaurant_open_time ?: "N/A"} - ${restaurant.restaurant_close_time ?: "N/A"}"
                    restaurantRating.text = "⭐ ${restaurant.avg_rating ?: "0.0"}"
                    restaurantDescription.text = restaurant.restaurant_description ?: "No Description"
                    restaurantCategories.text = "Categories: ${restaurant.food_categories?.joinToString() ?: "N/A"}"

                    // Load First Image
                    restaurant.restaurant_images?.firstOrNull()?.let { imageUrl ->
                        Glide.with(this)
                            .load(imageUrl)
                            .into(restaurantImage)
                    } ?: restaurantImage.setImageResource(R.drawable.img)


                } else {
                    Toast.makeText(this, "No Restaurant Found!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }, { error ->
                Log.e("RestaurantDetails", "API Error: ${error.message}", error)
                Toast.makeText(this, "Failed to load details!", Toast.LENGTH_LONG).show()
                finish()
            })
    }
}
