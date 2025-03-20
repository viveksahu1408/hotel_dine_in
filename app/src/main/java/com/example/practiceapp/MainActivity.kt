package com.example.practiceapp


import AdapterClasses.Adapters.FoodCategoryAdapter
import ApiClasses.RetrofitClient
import AdapterClasses.Adapters.RestaurantAdapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var restaurantAdapter: RestaurantAdapter

    private lateinit var foodCategoryRecyclerView: RecyclerView
    private lateinit var foodCategoryAdapter: FoodCategoryAdapter

    private lateinit var drawerLayout: DrawerLayout  
    private lateinit var navigationView: NavigationView

    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val btnFetchUser = findViewById<Button>(R.id.btnFetchUser)
        val profileIcon = findViewById<ImageView>(R.id.profileIcon)

        recyclerView = findViewById(R.id.hotelRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        val menuButton = findViewById<ImageView>(R.id.menu)

        sessionManager = SessionManager(this)

        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        fetchRestaurants()


        foodCategoryRecyclerView = findViewById(R.id.foodCategoryRecyclerView)
        foodCategoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fetchFoodCategories()



        profileIcon.setOnClickListener {
         val intent = Intent(this@MainActivity,ProfileActivity::class.java)
            startActivity(intent)
        }

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this,UpdateProfileActivity::class.java))
                    Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_bookings -> {
                    startActivity(Intent(this@MainActivity,AddAddressActivity::class.java))
                    Toast.makeText(this, " Add address ", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_ratinHistory -> {
                    startActivity(Intent(this,BookingHistoryActivity::class.java))
                    Toast.makeText(this, "Booking History", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    sessionManager.logoutUser()
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_totalRes -> {
                    startActivity(Intent(this,AllResturentActivity::class.java))
                    Toast.makeText(this, "Total Resturent", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_change_pass -> {

                    Toast.makeText(this, "Change password ", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_notification -> {
                    startActivity(Intent(this,NotificationActivity::class.java))
                    Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                }

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }

    @SuppressLint("CheckResult")
    private fun fetchRestaurants() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getRestaurants()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                progressBar.visibility = View.GONE


                if (response.status == 200) {
                    response.data?.let {
                        restaurantAdapter = RestaurantAdapter(this@MainActivity, it.filterNotNull())
                        recyclerView.adapter = restaurantAdapter
                    }

                } else {
                    Toast.makeText(this, "No Data Found!", Toast.LENGTH_LONG).show()
                }

            }, { error ->
                progressBar.visibility = View.GONE
                Log.e("MainActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Failed to load restaurants!", Toast.LENGTH_LONG).show()
            })
    }


    @SuppressLint("CheckResult")
    private fun fetchFoodCategories() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getFoodCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                progressBar.visibility = View.GONE

                if (response.status == 200) {
                    response.data?.let { foodList ->
                        foodCategoryAdapter = FoodCategoryAdapter(this@MainActivity, foodList.filterNotNull())
                        foodCategoryRecyclerView.adapter = foodCategoryAdapter
                    }
                } else {
                    Toast.makeText(this, "No Food Categories Found!", Toast.LENGTH_LONG).show()
                }

            }, { error ->
                progressBar.visibility = View.GONE
                Log.e("MainActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Failed to load food categories!", Toast.LENGTH_LONG).show()
            })
    }


}