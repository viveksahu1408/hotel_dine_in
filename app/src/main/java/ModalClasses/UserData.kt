package ModalClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val gender: String,
    val password: String
)



data class LoginResponse(
    val status: String,
    val message: String,
    val user_id: String
) : Serializable




data class UpdateUserResponse(
    val status: String,
    val message: String
) : Serializable



data class UserResponse(
    val status: Int,
    val message: String,
    @SerializedName("data") val user: UserDetails? // "data" ko "user" se map kar diya
) : Serializable


data class UserDetails(
    @SerializedName("user_id") val user_id: String,
    @SerializedName("user_first_name") val first_name: String,
    @SerializedName("user_last_name") val last_name: String,
    @SerializedName("user_middle_name") val middle_name: String,
    @SerializedName("user_email") val email: String,
    @SerializedName("user_gender") val gender: String,
    @SerializedName("user_phone_number") val phone_number: String,
    @SerializedName("user_image") val profile_image: String
): Serializable



data class ForgotPasswordResponse(
    val status: Int,
    val message: String
) : Serializable


data class UpdateUserAddressResponse(
    val status: Int,
    val message: String
)

