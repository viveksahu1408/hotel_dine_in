package com.example.practiceapp

import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.instance
    private val compositeDisposable = CompositeDisposable()
    private lateinit var ivProfileImage: ImageView
    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var tvGender: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var tvAddress:TextView

    private lateinit var sessionManager: SessionManager

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        sessionManager = SessionManager(this)


     val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        logoutBtn.setOnClickListener {
            sessionManager.logoutUser()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        ivProfileImage = findViewById(R.id.ivProfileImage)
        tvFullName = findViewById(R.id.tvFullName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)
        tvGender = findViewById(R.id.tvGender)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        tvAddress = findViewById(R.id.tvAddress)

//        val userId = sessionManager.getUserId()
//        fetchUserAddress(userId)

        val id = sessionManager.getUserId().toString()
        fetchUserDetails(id)
        fetchUserAddress(id)

        Log.d("id1", "$id")

        btnEditProfile.setOnClickListener {
            startActivity(Intent(this, UpdateProfileActivity::class.java))
        }

    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun fetchUserDetails(userId: String) {
//        val userIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), userId)

        RetrofitClient.instance.getUserDetails(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.status == 200) {
                    val user = response.user
                    tvFullName.text = " Name : ${user?.first_name} ${user?.middle_name} ${user?.last_name}"
                    tvEmail.text =  " Email : ${user?.email} "
                    tvPhoneNumber.text = "Mobile : ${user?.phone_number}"
                    tvGender.text = "Gender : ${user?.gender}"

                    Glide.with(this).load(user?.profile_image).into(ivProfileImage)

                } else {
                    Toast.makeText(this, "Failed to fetch user details", Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("API_ERROR", "Error: ${error.message}")
                Toast.makeText(this, "Error fetching user details", Toast.LENGTH_SHORT).show()
            })

    }



    private fun fetchUserAddress(user_id: String) {

      //  val textAddress = findViewById<TextView>(R.id.textAddress)
        val disposable = apiService.getUserAddress("user_details", user_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("API_RESPONSE", "Full Response: $response")

                if (response.status == 200 && response.userAddress != null) {
                    val address = response.userAddress
                    val fullAddress = " Address : ${address.houseNumber}, ${address.flatName}\n" +
                            "${address.society}, ${address.area}\n" +
                            "${address.street}, ${address.landmark}\n" +
                            "Pincode: ${address.pincode}\n" +
                            "${address.city}, ${address.state}, ${address.country}"

                    Log.d("API_RESPONSE", "Address: $fullAddress")

                   // tvAddress.text = fullAddress

                        tvAddress.text = fullAddress
                        Log.d("DEBUG", "Updated Address TextView: $fullAddress")


                } else {
                    Log.e("API_ERROR", response.message)
                    tvAddress.text = "Address not found"
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("API_ERROR", error.message ?: "Unknown Error")
                tvAddress.text = "Error fetching address"
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        compositeDisposable.add(disposable)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    }