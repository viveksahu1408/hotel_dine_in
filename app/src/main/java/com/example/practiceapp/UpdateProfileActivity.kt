package com.example.practiceapp

import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var middleName: EditText
    private lateinit var email: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var gender: EditText
    private lateinit var updateButton: Button
    private lateinit var profileImage: ImageView
    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var sessionManager: SessionManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        firstName = findViewById(R.id.etFirstName)
        lastName = findViewById(R.id.etLastName)
        middleName = findViewById(R.id.etMiddleName)
        email = findViewById(R.id.etEmail)
        phoneNumber = findViewById(R.id.etPhoneNumber)
        gender = findViewById(R.id.etGender)
        updateButton = findViewById(R.id.btnUpdate)
        profileImage = findViewById(R.id.ivProfileImage)


        sessionManager = SessionManager(this)

        profileImage.setOnClickListener {
            pickImageFromGallery()
        }

        updateButton.setOnClickListener {
            updateUserProfile()
            startActivity(Intent(this@UpdateProfileActivity,MainActivity::class.java))
        }


    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            profileImage.setImageURI(imageUri)
        }
    }




    @SuppressLint("CheckResult")
    private fun updateUserProfile() {
        val id = sessionManager.getUserId() ?: ""

        val hotelDineIn = "update".toRequestBody("text/plain".toMediaTypeOrNull())
        val firstName = firstName.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val lastName = lastName.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val middleName = middleName.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val email = email.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val gender = gender.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumber = phoneNumber.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val userId = id.toRequestBody("text/plain".toMediaTypeOrNull())

        Log.d("API_DEBUG", "hotel_dine_in: hotel_dine_in")
        Log.d("API_DEBUG", "first_name: ${firstName}")

        var profileImagePart: MultipartBody.Part? = null

        RetrofitClient.instance.updateUser(hotelDineIn, firstName, lastName, middleName, email, gender, phoneNumber, userId, profileImagePart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            }, { error ->
                Log.e("API_ERROR", error.message ?: "Unknown Error")
                Toast.makeText(this, "Profile Update Failed", Toast.LENGTH_SHORT).show()
            })
    }

}




