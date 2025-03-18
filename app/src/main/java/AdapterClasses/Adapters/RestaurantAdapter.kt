package AdapterClasses.Adapters

import ModalClasses.DataItem
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.practiceapp.R
import com.example.practiceapp.RestaurantDetailsActivity

class RestaurantAdapter(
    private val context: Context,
    private val restaurantList: List<DataItem>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]

        holder.restaurantName.text = restaurant.restaurantName ?: "No Name"
        holder.restaurantPrice.text = "Price: ₹${restaurant.restaurantPrice ?: "N/A"}"
        holder.restaurantTime.text =
            "Timing: ${restaurant.restaurantOpenTime ?: "N/A"} - ${restaurant.restaurantCloseTime ?: "N/A"}"
        holder.restaurantRating.text = "⭐ ${restaurant.avgRating ?: "0.0"}"

        Glide.with(holder.itemView.context)
            .load(restaurant.restaurantImageUrl)
            .into(holder.restaurantImage)


        holder.restaurantImage.setOnClickListener {
            val intent = Intent(context, RestaurantDetailsActivity::class.java)


            if (!restaurant.restaurantId.isNullOrEmpty()) {
                intent.putExtra("restaurant_id", restaurant.restaurantId)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Error: Restaurant ID is missing", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantName: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val restaurantPrice: TextView = itemView.findViewById(R.id.tvRestaurantPrice)
        val restaurantTime: TextView = itemView.findViewById(R.id.tvRestaurantTime)
        val restaurantRating: TextView = itemView.findViewById(R.id.tvRestaurantRating)
        val restaurantImage: ImageView = itemView.findViewById(R.id.ivRestaurantImage)
    }


}
