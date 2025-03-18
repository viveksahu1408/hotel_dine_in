package AdapterClasses.Adapters


import ModalClasses.RestaurantFC
import android.annotation.SuppressLint
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
import com.example.practiceapp.ResListFCActivity
import com.example.practiceapp.RestaurantDetailsActivity

class RestaurantFCAdapter  (private val context: Context,
                            private val restaurantFCList: List<RestaurantFC>)
    : RecyclerView.Adapter<RestaurantFCAdapter.RestaurantFCViewHolder>()
{


    class RestaurantFCViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        val restaurantName: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val restaurantPrice: TextView = itemView.findViewById(R.id.tvRestaurantPrice)
        val restaurantTime: TextView = itemView.findViewById(R.id.tvRestaurantTime)
        val restaurantRating: TextView = itemView.findViewById(R.id.tvRestaurantRating)
        val restaurantImage: ImageView = itemView.findViewById(R.id.ivRestaurantImage)
        val foodcategory: TextView = itemView.findViewById(R.id.foodcategory)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantFCAdapter.RestaurantFCViewHolder {

 val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant,parent,false)
        return RestaurantFCViewHolder(view)
    }


    override fun onBindViewHolder(holder: RestaurantFCAdapter.RestaurantFCViewHolder, position: Int) {
        val restaurantFCList = restaurantFCList[position]

        holder.restaurantName.text = restaurantFCList.name
        holder.restaurantPrice.text = restaurantFCList.price
        holder.restaurantTime.text = "Timing: ${restaurantFCList.openTime ?: "N/A"} - ${restaurantFCList.closeTime ?: "N/A"}"
        holder.foodcategory.text = restaurantFCList.foodType

//
//        Glide.with(holder.itemView.context)
//            .load(restaurantFCList.images)
//            .into(holder.restaurantImage)

//        Glide.with(context)
//            .load(restaurantFCList.images)
//            .placeholder(R.drawable.img)
//            .into(holder.restaurantImage)

        if (restaurantFCList != null) {
            if (restaurantFCList.images?.isNotEmpty() == true) {
                if (restaurantFCList != null) {
                    Glide.with(holder.itemView.context)
                        .load(restaurantFCList.images?.get(0))
                        .placeholder(R.drawable.img_2)
                        .into(holder.restaurantImage)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return restaurantFCList.size
    }

}