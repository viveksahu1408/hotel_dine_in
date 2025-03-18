package ModalClasses

import com.google.gson.annotations.SerializedName

data class BookingApiResponse(
	val message: String? = null,
	val status: String? = null
)
//
//data class UserAddressResponse(
//	val status: Int,
//	val message: String,
//	val address_details: List<AddressDetails>?
//)
//
//data class AddressDetails(
//	val id: String,
//	val user_id: String,
//	val address: String,
//	val city: String,
//	val state: String,
//	val country: String,
//	val postal_code: String
//)

//data class UserAddressResponse(
//	val status: Int,
//	val message: String,
//	@SerializedName("user address") val userAddress: AddressDetails?
//)
//
//data class AddressDetails(
//	@SerializedName("user_address_house_number") val houseNumber: String,
//	@SerializedName("user_address_flat_name") val flatName: String,
//	@SerializedName("user_address_society") val society: String,
//	@SerializedName("user_address_area") val area: String,
//	@SerializedName("user_address_street") val street: String,
//	@SerializedName("user_address_landmark") val landmark: String,
//	@SerializedName("user_address_pincode") val pincode: String,
//	@SerializedName("city_name") val city: String,
//	@SerializedName("state_name") val state: String,
//	@SerializedName("country_name") val country: String
//)

data class UserAddressResponse(
	@SerializedName("user address") val userAddress: AddressDetails?,
	@SerializedName("message") val message: String,
	@SerializedName("status") val status: Int
)

data class AddressDetails(
	@SerializedName("user_address_house_number") val houseNumber: String?,
	@SerializedName("user_address_flat_name") val flatName: String?,
	@SerializedName("user_address_society") val society: String?,
	@SerializedName("user_address_area") val area: String?,
	@SerializedName("user_address_street") val street: String?,
	@SerializedName("user_address_landmark") val landmark: String?,
	@SerializedName("user_address_pincode") val pincode: String?,
	@SerializedName("city_name") val city: String?,
	@SerializedName("state_name") val state: String?,
	@SerializedName("country_name") val country: String?
)
