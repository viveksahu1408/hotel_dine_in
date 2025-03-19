package com.example.practiceapp
import AdapterClasses.Adapters.SlotAdapter
import ApiClasses.RetrofitClient
import ModalClasses.BookingApiResponse
import ModalClasses.SlotAvailableResponse
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class BookingActivity : AppCompatActivity() {

    private lateinit var dateTextView: TextView
    private lateinit var btnSelectDate: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var slotRecyclerView: RecyclerView
    private lateinit var slotAdapter: SlotAdapter

    private var selectedDate: String = ""
    private var restaurantId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        dateTextView = findViewById(R.id.dateTextView)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        progressBar = findViewById(R.id.progressBar)
        slotRecyclerView = findViewById(R.id.slotRecyclerView)

       // slotRecyclerView.layoutManager = LinearLayoutManager(this,)
        slotRecyclerView.layoutManager = GridLayoutManager(this, 2)

        restaurantId = intent.getStringExtra("restaurant_id")

        if (restaurantId.isNullOrEmpty()) {
            Toast.makeText(this, "Error: No Restaurant ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSelectDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val formattedMonth = "%02d".format(month + 1)
                val formattedDay = "%02d".format(dayOfMonth)
                selectedDate = "$year-$formattedMonth-$formattedDay"
                dateTextView.text = "Selected Date: $selectedDate"
                fetchAvailableSlots(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


    @SuppressLint("CheckResult")
    private fun fetchAvailableSlots(date: String) {
        Log.d("BookingActivity", "Fetching slots for restaurantId: $restaurantId, date: $date")

        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getAvailableSlots(
            restaurantId = restaurantId!!,
            bookingDate = date
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                progressBar.visibility = View.GONE
                handleApiResponse(response)
            }, { error ->
                progressBar.visibility = View.GONE
                Log.e("BookingActivity", "API Error: ${error.message}", error)
                Toast.makeText(this, "Failed to load slots!", Toast.LENGTH_LONG).show()
            })
    }


    private fun handleApiResponse(response: SlotAvailableResponse) {
        if (response.status == 200) {
            val slots = mutableListOf<Any>()

            response.available_slots?.Breakfast?.filterNotNull()?.let { slots.addAll(it) }
            response.available_slots?.Lunch?.filterNotNull()?.let { slots.addAll(it) }
            response.available_slots?.Dinner?.filterNotNull()?.let { slots.addAll(it) }

            if (slots.isNotEmpty()) {
                slotAdapter = SlotAdapter(slots, this, restaurantId!!) { slotId ->
                    showBottomSheetDialog(slotId)
                }
                slotRecyclerView.adapter = slotAdapter
            } else {
                Toast.makeText(this, "No slots available!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("API_RESPONSE", "Full Response: $response")
           // Log.d("RESDEL","$response")
            Toast.makeText(this, "No Data Found!", Toast.LENGTH_LONG).show()
        }
    }

    private fun showBottomSheetDialog(slotId: String) {
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.dialog_booking, null)
        val bottomSheetDialog = BottomSheetDialog(this)

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.setCancelable(true) // Ensure dialog is cancelable

        val etGuests = bottomSheetView.findViewById<EditText>(R.id.etNumberOfGuests)
        val etSpecialDish = bottomSheetView.findViewById<EditText>(R.id.etSpecialRequest)
        val btnConfirmBooking = bottomSheetView.findViewById<Button>(R.id.btnConfirmBooking)

        btnConfirmBooking.setOnClickListener {
            val guests = etGuests.text.toString().trim()
            val specialDish = etSpecialDish.text.toString().trim()

            if (guests.isEmpty()) {
                Toast.makeText(this, "Please enter number of guests!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            bottomSheetDialog.dismiss()
            confirmBooking(slotId, guests, specialDish)
        }

        bottomSheetDialog.show() // Ensure this is called
    }


    @SuppressLint("CheckResult")
    private fun confirmBooking(slotId: String, guests: String, specialDish: String) {
        if (restaurantId.isNullOrEmpty() || slotId.isEmpty() || guests.isEmpty() || selectedDate.isEmpty()) {
            Log.e("BookingActivity", "Error: Missing required parameters")
            Toast.makeText(this, "Error: Missing required parameters!", Toast.LENGTH_LONG).show()
            return
        }

        Log.d("BookingActivity", "Confirming booking with:")
        Log.d("BookingActivity", "hotel_dine_in: booking")
        Log.d("BookingActivity", "restaurant_id: $restaurantId")
        Log.d("BookingActivity", "slot_id: $slotId")
        Log.d("BookingActivity", "user_id: 1")
        Log.d("BookingActivity", "number_of_guests: $guests")
        Log.d("BookingActivity", "booking_date: $selectedDate")
        Log.d("BookingActivity", "special_request: $specialDish")

        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.bookSlot(
            "booking",
            restaurantId!!,
             slotId,
             "1",
            guests,
            selectedDate,
             specialDish
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                progressBar.visibility = View.GONE
                handleBookingResponse(response)
            }, { error ->
                progressBar.visibility = View.GONE
                Log.e("BookingActivity", "Booking API Error: ${error.message}", error)
                Toast.makeText(this, "Booking failed!", Toast.LENGTH_LONG).show()
            })
    }


    private fun handleBookingResponse(response: BookingApiResponse) {
        if (response.status == "200") {
            Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@BookingActivity,MainActivity::class.java)
            startActivity(intent)
           // Log.d("API Message", response.message ?: "No message")

            finish()
        } else {
            Toast.makeText(this, "Booking Failed: ${response.message}", Toast.LENGTH_LONG).show()
        }
    }
}

