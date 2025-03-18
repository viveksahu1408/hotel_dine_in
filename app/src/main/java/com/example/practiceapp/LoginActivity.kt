package com.example.practiceapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ApiClasses.RetrofitClient

import ForgotPasswordFragment
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        loginBtn = findViewById(R.id.btnLogin)
        registerBtn = findViewById(R.id.btnRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // ✅ Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginBtn.setOnClickListener {
            loginUser()
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
//
//        tvForgotPassword.setOnClickListener {
//            startActivity(Intent(this@LoginActivity,ForgotPasswordFragment::class.java))
//        }
//    }

        tvForgotPassword.setOnClickListener {
            openFragment(ForgotPasswordFragment())
        }
    }

    private fun openFragment(fragment: ForgotPasswordFragment) {  // ✅ Fix: Use Fragment type
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // ✅ Back button support
        transaction.commit()
    }


    @SuppressLint("CheckResult")
    private fun loginUser() {
        val apiService = RetrofitClient.instance

        val emailValue = email.text.toString().trim()
        val passwordValue = password.text.toString().trim()

        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            Toast.makeText(this, "Email and Password are required!", Toast.LENGTH_LONG).show()
            return
        }


        apiService.loginUser1("login", email = emailValue, password = passwordValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
              //  val loginResponse = response.body() // ✅ Directly LoginResponse ko access karo

                if (response.status == "200") {
                    Log.d("LoginActivity", "User ID: ${response.user_id}")


                    sessionManager.saveUserId(response.user_id)

                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                    sessionManager.setLoginStatus(true)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.e("LoginActivity", "Response body is null")
                    Toast.makeText(this, "Invalid response from server!", Toast.LENGTH_LONG).show()
                }
            }, { error ->
                Log.e("LoginActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_LONG).show()
            })
    }
}










/*
package com.example.practiceapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ApiClasses.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        loginBtn = findViewById(R.id.btnLogin)
        registerBtn = findViewById(R.id.btnRegister)


        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginBtn.setOnClickListener {
            loginUser1()
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    @SuppressLint("CheckResult")
    private fun loginUser1(){
        val apiService = RetrofitClient.instance

        val emailValue = email.text.toString().trim()
        val passwordValue = password.text.toString().trim()

        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            Toast.makeText(this, "Email and Password are required!", Toast.LENGTH_LONG).show()
            return
        }

        apiService.loginUser1("login" , emailValue , passwordValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                /*
                sessionManager.saveUserData(
                    response.user_id,
                    response.first_name,
                    response.middle_name ?: "",
                    response.last_name,
                    response.email,
                    response.phone,

                )


                 */


                Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                sessionManager.setLoginStatus(true)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            { error ->
                Log.e("LoginActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_LONG).show()
            })
    }
}


 */