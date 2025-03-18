package com.example.practiceapp

import ApiClasses.RetrofitClient
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class AddAddressActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.instance
    private val compositeDisposable = CompositeDisposable()
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updat_address)

        sessionManager = SessionManager(this)

        val etHouseNumber = findViewById<EditText>(R.id.etHouseNumber)
        val etFlatName = findViewById<EditText>(R.id.etFlatName)
        val etSocietyName = findViewById<EditText>(R.id.etSocietyName)
        val etArea = findViewById<EditText>(R.id.etArea)
        val etStreet = findViewById<EditText>(R.id.etStreet)
        val etPincode = findViewById<EditText>(R.id.etPincode)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        val id = sessionManager.getUserId()

        btnSubmit.setOnClickListener {
            val houseNumber = etHouseNumber.text.toString()
            val flatName = etFlatName.text.toString()
            val societyName = etSocietyName.text.toString()
            val area = etArea.text.toString()
            val street = etStreet.text.toString()
            val pincode = etPincode.text.toString()

            addUserAddress(houseNumber, flatName, societyName, area, street, "", pincode, 1, 1, 1, id)
        }
    }

    private fun addUserAddress(
        houseNumber: String,
        flatName: String,
        societyName: String,
        area: String,
        street: String,
        landmark: String,
        pincode: String,
        countryId: Int,
        stateId: Int,
        cityId: Int,
        userId: String?
    ) {
        val disposable = apiService.addUserAddress(
            "user_address", houseNumber, flatName, societyName, area, street, landmark, pincode, countryId, stateId, cityId, userId
        )

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: ResponseBody ->
                Toast.makeText(this, "Response: ${response.string()}", Toast.LENGTH_LONG).show()
            }, { throwable: Throwable ->
                Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_LONG).show()
            })

        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()  // Memory Leak से बचने के लिए
    }
}
