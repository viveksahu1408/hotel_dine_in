package com.example.practiceapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ApiClasses.RetrofitClient
import android.content.Intent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var firstName: EditText
    private lateinit var middleName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var gender: EditText
    private lateinit var adress: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sessionManager = SessionManager(this)
        firstName = findViewById(R.id.etFirstName)
        middleName = findViewById(R.id.etMiddleName)
        lastName = findViewById(R.id.etLastName)
        email = findViewById(R.id.etEmail)
        phone = findViewById(R.id.etPhone)
        adress = findViewById(R.id.etadress)
        gender = findViewById(R.id.etGender)
        password = findViewById(R.id.etPassword)
        registerBtn = findViewById(R.id.btnRegister)

        registerBtn.setOnClickListener {
            registerUser()
        }
    }

    @SuppressLint("CheckResult")
    private fun registerUser() {
        val apiService = RetrofitClient.instance

        val hotelDineIn = "register".toRequestBody("text/plain".toMediaTypeOrNull())
        val firstNameValue = firstName.text.toString().trim()
        val middleNameValue = middleName.text.toString().trim()
        val lastNameValue = lastName.text.toString().trim()
        val emailValue = email.text.toString().trim()
        val phoneNumberValue = phone.text.toString().trim()
        val addressValue = adress.text.toString().trim()
        val genderValue = gender.text.toString().trim()
        val passwordValue = password.text.toString().trim()

        // ✅ Validation check
        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty() ||
            phoneNumberValue.isEmpty()|| addressValue.isEmpty() || genderValue.isEmpty() || passwordValue.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show()
            return
        }

        // ✅ Create RequestBody for API request
        val firstName = firstNameValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val middleName = middleNameValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastName = lastNameValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = emailValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumber = phoneNumberValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val address = addressValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val gender = genderValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val password = passwordValue.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.registerUser(hotelDineIn, firstName, middleName, lastName, email, phoneNumber,address, gender, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val jsonObject = JSONObject(response.string())  // ✅ Convert API response to JSON

                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show()

                    // ✅ Sirf success hone pe hi LoginActivity open karega
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    val errorMessage = jsonObject.optString("message", "Registration failed")
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }, { error ->
                Log.e("RegisterActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            })
    }
}


















/*
class RegisterActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var firstName: EditText
    private lateinit var middleName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var gender: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sessionManager = SessionManager(this)
        firstName = findViewById(R.id.etFirstName)
        middleName = findViewById(R.id.etMiddleName)
        lastName = findViewById(R.id.etLastName)
        email = findViewById(R.id.etEmail)
        phone = findViewById(R.id.etPhone)
        gender = findViewById(R.id.etGender)
        password = findViewById(R.id.etPassword)
        registerBtn = findViewById(R.id.btnRegister)

        registerBtn.setOnClickListener {
            registerUser()

                startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @SuppressLint("CheckResult")
    private fun registerUser() {
        val apiService = RetrofitClient.instance


        val hotelDineIn = "register".toRequestBody("text/plain".toMediaTypeOrNull())
        val firstNameValue = firstName.text.toString().trim()
        val middleNameValue = middleName.text.toString().trim()
        val lastNameValue = lastName.text.toString().trim()
        val emailValue = email.text.toString().trim()
        val phoneNumberValue = phone.text.toString().trim()
        val genderValue = gender.text.toString().trim()
        val passwordValue = password.text.toString().trim()

        // Validate inputs before making API call
        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty() ||
            phoneNumberValue.isEmpty() || genderValue.isEmpty() || passwordValue.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show()
            return
        }

        // Create RequestBody objects for API request
        val firstName = firstNameValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val middleName = middleNameValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastName = lastNameValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = emailValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumber = phoneNumberValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val gender = genderValue.toRequestBody("text/plain".toMediaTypeOrNull())
        val password = passwordValue.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.registerUser(hotelDineIn, firstName, middleName, lastName, email, phoneNumber, gender, password
        )

            .subscribeOn(Schedulers.io())  // API call on background thread
            .observeOn(AndroidSchedulers.mainThread())  // Response on main thread
            .subscribe({ response ->

                sessionManager.saveUserData("1", firstNameValue,middleNameValue, lastNameValue, emailValue, phoneNumberValue, genderValue)


                val responseString = response.string() // Convert ResponseBody to String
                Log.d("RegisterActivity", "Success Response: $responseString")
                Toast.makeText(this, "Success: $responseString", Toast.LENGTH_LONG).show()
            }, { error ->
                Log.e("RegisterActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            })
    }
}

 */