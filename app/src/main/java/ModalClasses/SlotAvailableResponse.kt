package ModalClasses

import com.google.gson.annotations.SerializedName

data class SlotAvailableResponse(
	val available_slots: available_slots? = null,
	val status: Int? = null
)

data class Lunch(
	val slot_start_time: String? = null,
	val remaining_capacity: Int? = null,
	val slot_id: Int? = null,
	val slot_end_time: String? = null
)

data class Dinner(
	val slot_start_time: String? = null,
	val remaining_capacity: Int? = null,
	val slot_id: Int? = null,
	val slot_end_time: String? = null
)

data class Breakfast(
	val slot_start_time: String? = null,
	val remaining_capacity: Int? = null,
	val slot_id: Int? = null,
	val slot_end_time: String? = null
)

data class available_slots(
	val Breakfast: List<Breakfast?>? = null,
	val Dinner: List<Dinner?>? = null,
	val Lunch: List<Lunch?>? = null
)




data class BookingResponse(
	@SerializedName("status") val status: String,
	@SerializedName("data") val bookings: List<Booking>?
)

data class Booking(
	@SerializedName("booking_id") val bookingId: String,
	@SerializedName("restaurant_id") val restaurantId: String,
	@SerializedName("restaurant_name") val restaurantName: String,
	@SerializedName("user_id") val userId: String,
	@SerializedName("table_id") val tableId: String,
	@SerializedName("booking_date") val bookingDate: String,
	@SerializedName("booking_time") val bookingTime: String,
	@SerializedName("status") val status: String,
	@SerializedName("total_amount") val totalAmount: String,
	@SerializedName("payment_status") val paymentStatus: String,
	@SerializedName("created_at") val createdAt: String
)


