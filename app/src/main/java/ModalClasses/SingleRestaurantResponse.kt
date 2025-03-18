package ModalClasses

import com.google.gson.annotations.SerializedName

data class SingleRestaurantResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: Int? = null
)

data class Data(
	val restaurant_active_status: String? = null,
	val restaurant_open_time: String? = null,
	val restaurant_id: String? = null,
	val restaurant_description: String? = null,
	val restaurant_images: List<String?>? = null,
	val restaurant_price: String? = null,
	val restaurant_email: String? = null,
	val restaurantLicenceNo: String? = null,
	val restaurant_phone_number: String? = null,
	val restaurant_updated_date: String? = null,
	val food_categories: List<String?>? = null,
	val restaurant_website_link: String? = null,
	val restaurant_name: String? = null,
	val restaurant_user_name: String? = null,
	val restaurant_food_type: String? = null,
	val avg_rating: String? = null,
	val restaurant_approved_date: Any? = null,
	val restaurant_updatedby: String? = null,
	val isdelete: String? = null,
	val restaurant_close_time: String? = null,
	val restaurant_added_date: String? = null
)


data class NotificationListResponse(
	val status: Int,
	val message: String,
	@SerializedName("data") val notifications: List<NotificationItem>
)

data class NotificationItem(

	@SerializedName("notification_id") val notification_id: String,
	@SerializedName("title") val title: String,
	@SerializedName("message") val message: String,
	@SerializedName("created_at") val date: String,

//	//val id: String,
//	val title: String,
//	val message: String,
//	val date: String
)
