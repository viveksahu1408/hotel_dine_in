package AdapterClasses.Adapters

import ModalClasses.FoodCategoryItem
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

class FoodCategoryAdapter(
    private val context: Context,
    private val foodList: List<FoodCategoryItem>
) : RecyclerView.Adapter<FoodCategoryAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.foodImage)
        val foodName: TextView = view.findViewById(R.id.foodName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food_category, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]

        holder.foodName.text = foodItem.food_name ?: "No Name"
        Glide.with(context)
            .load(foodItem.food_image)
            .placeholder(R.drawable.img)
            .into(holder.foodImage)

        holder.foodImage.setOnClickListener {
            val intent = Intent(context, ResListFCActivity::class.java)

            if (!foodItem.food_id.isNullOrEmpty()) {
                intent.putExtra("foodcategory_id", foodItem.food_id)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Error: FoodCategory ID is missing", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = foodList.size
}
