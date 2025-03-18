package ModalClasses

import com.google.gson.annotations.SerializedName

data class FoodCategoryResponse(
	val data: List<FoodCategoryItem?>? = null,
	val message: String? = null,
	val status: Int? = null
)

data class FoodCategoryItem(
	val food_name: String? = null,
	val addeddate: String? = null,
	val food_id: String? = null,
	val food_image: String? = null
)

data class ApiResponse(
	val status: String,
	val message: String,
	val data: List<MenuItem>
)

data class MenuItem(
	val id: String,
	val name: String,
	val price: String
)

data class Restaurant(
	val restaurant_id: String,
	val restaurant_name: String,
	val restaurant_email: String,
	val restaurant_phone_number: String,
	val restaurant_licence_no: String,
	val restaurant_user_name: String,
	val restaurant_website_link: String,
	val restaurant_price: String,
	val restaurant_description: String,
	val restaurant_food_type: String,
	val restaurant_open_time: String,
	val restaurant_close_time: String,
	val restaurant_active_status: String,
	val restaurant_images: List<String>,
	val avg_rating: String,
	val food_categories: List<String>
)


data class RestaurantResponse(
	val status: Int,
	val message: String,
	val restaurants: List<Restaurant>?
)


data class RestaurantFCResponse(
	@SerializedName("status") val status: String,
	@SerializedName("data") val restaurants: List<RestaurantFC>?
)

data class RestaurantFC(
	@SerializedName("restaurant_id") val restaurantId: String,
	@SerializedName("restaurant_name") val name: String,
	@SerializedName("restaurant_email") val email: String,
	@SerializedName("restaurant_phone_number") val phoneNumber: String,
	@SerializedName("restaurant_licence_no") val licenceNo: String,
	@SerializedName("restaurant_user_name") val userName: String,
	@SerializedName("restaurant_website_link") val websiteLink: String?,
	@SerializedName("restaurant_Price") val price: String,
	@SerializedName("restaurant_description") val description: String,
	@SerializedName("restaurant_food_type") val foodType: String,
	@SerializedName("restaurant_open_time") val openTime: String,
	@SerializedName("restaurant_close_time") val closeTime: String,
	@SerializedName("restauarnt_approved_status") val approvedStatus: String,
	@SerializedName("restaurant_active_status") val activeStatus: String,
	@SerializedName("restaurant_added_date") val addedDate: String,
	@SerializedName("restaurant_updated_date") val updatedDate: String,
	@SerializedName("restaurant_updatedby") val updatedBy: String?,
	@SerializedName("restaurant_approved_date") val approvedDate: String?,
	@SerializedName("images") val images: List<String>?
)

