package AdapterClasses.Adapters

import ModalClasses.AllRestaurant
import ModalClasses.Restaurant
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceapp.R

class AllRestaurantAdapter(private val allrestuent: List<AllRestaurant>?
):RecyclerView.Adapter<AllRestaurantAdapter.ViewHolder>()
 {

         class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
             val name: TextView = view.findViewById(R.id.textViewRestaurantName)

            val image: ImageView = view.findViewById(R.id.imgRecycle)
             val foodtype : TextView = view.findViewById(R.id.foodtype)

          //   val email: TextView = itemView.findViewById(R.id.restaurantEmail)
          //   val phone: TextView = itemView.findViewById(R.id.restaurantPhone)
             val price: TextView = itemView.findViewById(R.id.restaurantPrice1)
//             val description: TextView = itemView.findViewById(R.id.restaurantDescription1)
//             val openTime: TextView = itemView.findViewById(R.id.restaurantOpenTime)
//             val closeTime: TextView = itemView.findViewById(R.id.restaurantCloseTime)
//             val status: TextView = itemView.findViewById(R.id.restaurantStatus)
             val rating: TextView = itemView.findViewById(R.id.restaurantRating1)
           //  val image: ImageView = itemView.findViewById(R.id.restaurantImage)
     }

     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): AllRestaurantAdapter.ViewHolder {


         val view = LayoutInflater.from(parent.context)
             .inflate(R.layout.item_restaurant_list, parent, false)
         return ViewHolder(view)
     }

     override fun onBindViewHolder(holder: AllRestaurantAdapter.ViewHolder, position: Int) {
         val restaurant = allrestuent?.get(position)
         if (restaurant != null) {
             holder.name.text = restaurant.restaurantName
           //  holder.email.text = restaurant.restaurantEmail
           //  holder.phone.text = restaurant.restaurantPhone
             holder.price.text = "₹${restaurant.restaurantPrice}"
             //holder.description.text = restaurant.restaurantDescription
             holder.foodtype.text = "Food Type: ${restaurant.restaurantFoodType}"
         //    holder.openTime.text = "Opens at: ${restaurant.restaurantOpenTime}"
         //    holder.closeTime.text = "Closes at: ${restaurant.restaurantCloseTime}"
         //    holder.status.text = "Status: ${restaurant.restaurantStatus}"
             holder.rating.text = "Rating: ${restaurant.avgRating}"

         }



         if (restaurant != null) {
             if (restaurant.restaurantImages.isNotEmpty()) {
                 if (restaurant != null) {
                     Glide.with(holder.itemView.context)
                         .load(restaurant.restaurantImages[0])
                         .placeholder(R.drawable.img_2)
                         .into(holder.image)
                 }
             }
         }



     }

     override fun getItemCount(): Int = allrestuent?.size!!


 }