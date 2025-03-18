package AdapterClasses.Adapters

import ModalClasses.Breakfast
import ModalClasses.Dinner
import ModalClasses.Lunch
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.R


class SlotAdapter(
    private val slots: List<Any>,
    private val context: Context,
    private val restaurantId: String,
    private val onSlotClick: (String) -> Unit
) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slotTime: TextView = itemView.findViewById(R.id.slotTime)
        val remainingCapacity: TextView = itemView.findViewById(R.id.remainingCapacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slot, parent, false)
        return SlotViewHolder(view)
    }



    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val slot = slots[position]

        when (slot) {
            is Breakfast -> {
                holder.slotTime.text = "${slot.slot_start_time} - ${slot.slot_end_time}"
                holder.remainingCapacity.text = "Seats Available: ${slot.remaining_capacity}"
                holder.itemView.setOnClickListener {
                    onSlotClick(slot.slot_id.toString()) // Correct way to trigger bottom sheet
                }
            }
            is Lunch -> {
                holder.slotTime.text = "${slot.slot_start_time} - ${slot.slot_end_time}"
                holder.remainingCapacity.text = "Seats Available: ${slot.remaining_capacity}"
                holder.itemView.setOnClickListener {
                    onSlotClick(slot.slot_id.toString())
                }
            }
            is Dinner -> {
                holder.slotTime.text = "${slot.slot_start_time} - ${slot.slot_end_time}"
                holder.remainingCapacity.text = "Seats Available: ${slot.remaining_capacity}"
                holder.itemView.setOnClickListener {
                    onSlotClick(slot.slot_id.toString())
                }
            }
        }
    }



    override fun getItemCount(): Int = slots.size
}

