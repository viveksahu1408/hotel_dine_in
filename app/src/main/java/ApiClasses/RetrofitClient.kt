package ApiClasses


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
   // private const val BASE_URL = "http://192.168.10.208/Hotel_DineIn_API/Dine_in_php/API/Dine_in_php-sagar/controllers/"
    //private const val BASE_URL = "http://192.168.182.181/Hotel_DineIn_API/Dine_in_php/API/Dine_in_php-sagar/controllers/"
   // private const val BASE_URL = "http://192.168.4.239/Hotel_DineIn_API/Dine_in_php/API/Dine_in_php-sagar/controllers/"  //mylaptop with support 5g
      private const val BASE_URL = "http://192.168.4.212/api/API/Dine_in_php-sagar/controllers/"  //change api of lenovo
  // private const val BASE_URL = "http://192.168.10.208/api/API/Dine_in_php-sagar/controllers/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Use RxJava2
            .build()
            .create(ApiService::class.java)
    }
}
