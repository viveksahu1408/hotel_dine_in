package ModalClasses

import com.google.gson.annotations.SerializedName

data class ResturentListResponse(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: Int? = null
)
data class DataItem(
	@SerializedName("restaurant_id") val restaurantId: String? = null,
	@SerializedName("restaurant_name") val restaurantName: String? = null,
	@SerializedName("restaurant_price") val restaurantPrice: String? = null,
	@SerializedName("restaurant_open_time") val restaurantOpenTime: String? = null,
	@SerializedName("restaurant_close_time") val restaurantCloseTime: String? = null,
	@SerializedName("restaurant_image_url") val restaurantImageUrl: String? = null,
	@SerializedName("avg_rating") val avgRating: String? = null
)

//this is for all restaurant list
data class AllRestaurantResponse(
	val status: Int,
	val message: String,
	val restaurants: List<AllRestaurant>?
)

data class AllRestaurant(
	@SerializedName("restaurant_id") val restaurantId: String,
	@SerializedName("restaurant_name") val restaurantName: String,
	@SerializedName("restaurant_email") val restaurantEmail: String,
	@SerializedName("restaurant_phone_number") val restaurantPhone: String,
	@SerializedName("restaurant_licence_no") val restaurantLicence: String,
	@SerializedName("restaurant_user_name") val restaurantUserName: String,
	@SerializedName("restaurant_website_link") val restaurantWebsite: String,
	@SerializedName("restaurant_price") val restaurantPrice: String,
	@SerializedName("restaurant_description") val restaurantDescription: String,
	@SerializedName("restaurant_food_type") val restaurantFoodType: String,
	@SerializedName("restaurant_open_time") val restaurantOpenTime: String,
	@SerializedName("restaurant_close_time") val restaurantCloseTime: String,
	@SerializedName("restaurant_active_status") val restaurantStatus: String,
	@SerializedName("isdelete") val isDelete: String,
	@SerializedName("restaurant_added_date") val restaurantAddedDate: String,
	@SerializedName("restaurant_updated_date") val restaurantUpdatedDate: String,
	@SerializedName("restaurant_updatedby") val restaurantUpdatedBy: String,
	@SerializedName("restaurant_approved_date") val restaurantApprovedDate: String?,
	@SerializedName("restaurant_images") val restaurantImages: List<String>,
	@SerializedName("avg_rating") val avgRating: String,
	@SerializedName("food_categories") val foodCategories: List<String>
)





