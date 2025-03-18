package ApiClasses



import ModalClasses.AllRestaurantResponse
import ModalClasses.BookingApiResponse
import ModalClasses.BookingResponse
import ModalClasses.FoodCategoryResponse
import ModalClasses.ForgotPasswordResponse
import ModalClasses.LoginResponse
import ModalClasses.NotificationListResponse
import ModalClasses.RestaurantFCResponse
import ModalClasses.ResturentListResponse
import ModalClasses.SingleRestaurantResponse
import ModalClasses.SlotAvailableResponse
import ModalClasses.UpdateUserAddressResponse
import ModalClasses.UpdateUserResponse
import ModalClasses.UserAddressResponse
import ModalClasses.UserResponse

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface ApiService {
        @Multipart
        @POST("registration_controller.php")
        fun registerUser(
            @Part("hotel_dine_in") hotelDineIn: RequestBody,
            @Part("first_name") firstName: RequestBody,
            @Part("middle_name") middleName: RequestBody,
            @Part("last_name") lastName: RequestBody,
            @Part("email") email: RequestBody,
            @Part("phone_number") phoneNumber: RequestBody,
            @Part("address") address: RequestBody,
            @Part("gender") gender: RequestBody,
            @Part("password") password: RequestBody,

        ): Single<ResponseBody> // RxJava2 Single<ResponseBody>



    @FormUrlEncoded
    @POST("login_controller.php")
    fun loginUser1(
        @Field("hotel_dine_in") hotelDineIn: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<LoginResponse>



    @POST("login_controller.php")
    fun loginUser(@Body requestBody: RequestBody): Single<LoginResponse>


    @POST("restaurant_display_controller.php")
    fun getRestaurants(): Single<ResturentListResponse>

//    @POST("restaurant_display_controller.php")
//    fun getAllRestaurants(): Single<AllResturentListResponse>

    @FormUrlEncoded
    @POST("single_restaurant_disply.php")
    fun getRestaurantDetails(
        @Field("hotel_dine_in") hotel_dine_in: String,
        @Field("restaurant_id") restaurant_id: Int
    ): Single<SingleRestaurantResponse>


    @FormUrlEncoded
    @POST("food_category.php")
    fun getFoodCategories(
        @Field("hotel_dine_in") hotelDineIn: String = "food_category"
    ): Single<FoodCategoryResponse>


    @FormUrlEncoded
    @POST("available_slot_controller.php")
    fun getAvailableSlots(
        @Field("hotel_dine_in") hotelDineIn: String = "available_slot",
        @Field("restaurant_id") restaurantId: String,
        @Field("booking_date") bookingDate: String
    ): Single<SlotAvailableResponse>


    @FormUrlEncoded
    @POST("booking_slot.php")
    fun bookSlot(
        @Field("hotel_dine_in") hotel_dine_in : String,
        @Field("restaurant_id") restaurant_id: String,
        @Field("slot_id") slot_id: String,
        @Field("user_id") user_id: String,
        @Field("number_of_guest") number_of_guests: String,
        @Field("booking_date") booking_date: String,
        @Field("special_request") special_request: String
    ): Single<BookingApiResponse>


    @Multipart
    @POST("update_user_controller.php")
    fun updateUser(
        @Part("hotel_dine_in") update: RequestBody,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("middle_name") middle_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("user_id") user_id: RequestBody,
        @Part profile_image: MultipartBody.Part?
    ): Single<UpdateUserResponse>


    @FormUrlEncoded
    @POST("select_sigle_user_details.php")
    fun getUserDetails(
        @Field("user_id") userId: String
    ): Single<UserResponse>


    @Multipart
    @POST("forgot.php")
    fun forgotPassword(
        @Part("email") email: RequestBody
    ): Single<ForgotPasswordResponse>



    @Multipart
    @POST("all_restaurant.php")
    fun getAllRestaurants(
        @Part("hotel_dine_in") hotel_dine_in: RequestBody
    ): Single<AllRestaurantResponse>




    @FormUrlEncoded
    @POST("get_user_address.php") // API का सही URL डालें
    fun getUserAddress(
        @Field("hotel_dine_in") hotelDineIn: String,
        @Field("user_id") userId: String
    ): Single<UserAddressResponse>


    @Multipart
    @POST("update_user_address.php")
    fun updateUserAddress(
        @Part("hotel_dine_in") hotelDineIn: RequestBody,
        @Part("house_number") houseNumber: RequestBody,
        @Part("society_name") societyName: RequestBody,
        @Part("area") area: RequestBody,
        @Part("street") street: RequestBody,
        @Part("pincode") pincode: RequestBody,
        @Part("country_id") countryId: RequestBody,
        @Part("state_id") stateId: RequestBody,
        @Part("city_id") cityId: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part("flat_name") flatName: RequestBody,
        @Part("landmark") landmark: RequestBody
    ): Single<UpdateUserAddressResponse>


    @FormUrlEncoded
    @POST("user_address.php")
    fun addUserAddress(
        @Field("hotel_dine_in") hotelDineIn: String,
        @Field("house_number") houseNumber: String,
        @Field("flat_name") flatName: String,
        @Field("society_name") societyName: String,
        @Field("area") area: String,
        @Field("street") street: String,
        @Field("landmark") landmark: String,
        @Field("pincode") pincode: String,
        @Field("country_id") countryId: Int,
        @Field("state_id") stateId: Int,
        @Field("city_id") cityId: Int,
        @Field("user_id") userId: String?
    ): Single<ResponseBody>

    @Multipart
    @POST("notification.php")
    fun getNotifications(
        @Part("hotel_dine_in") hotelDineIn: RequestBody,
        @Part("user_id") userId: RequestBody
    ): Single<NotificationListResponse>



    @POST("food_restro.php")
    fun getRestaurantsfc(
        @Query("hotel_dine_in") hotelDineIn: String,
        @Query("food_id") foodId: String?
    ): Single<RestaurantFCResponse>

//    @Multipart
//    @POST("booking_details.php")
//    fun getUserBookings(
//        @Part hotelDineIn: MultipartBody.Part,  // ✅ Correct
//        @Part userId: String  // ✅ Correct
//    ): Single<BookingResponse>

    @FormUrlEncoded
    @POST("booking_details.php")
    fun getUserBookings(
        @Field("hotel_dine_in") hotelDineIn: String,
        @Field("user_id") userId: String
    ): Single<BookingResponse>



}


