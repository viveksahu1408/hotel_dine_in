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
	@SerializedName("message") val message: String,
	@SerializedName("user_booking_details") val bookings: List<Booking>?
)


data class Booking(
	@SerializedName("restaurant_name") val restaurantName: String,
	@SerializedName("booking_date") val bookingDate: String,
	@SerializedName("meal_type") val mealType: String,
	@SerializedName("slot_start_time") val slotStartTime: String,
	@SerializedName("slot_end_time") val slotEndTime: String,
	@SerializedName("number_of_guest") val numberOfGuest: Int,
	@SerializedName("booking_status") val bookingStatus: String,
	@SerializedName("special_request") val specialRequest: String?
)



