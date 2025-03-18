package AdapterClasses.Adapters

import ModalClasses.NotificationItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.R

class NotificationAdapter(private var notificationList: List<NotificationItem>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvNotificationTitle)
        val message: TextView = view.findViewById(R.id.tvNotificationMessage)
        val date: TextView = view.findViewById(R.id.tvNotificationDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.title.text = notification.title
        holder.message.text = notification.message
        holder.date.text = notification.date
    }

    override fun getItemCount() = notificationList.size

    fun updateList(newList: List<NotificationItem>) {
        notificationList = newList
        notifyDataSetChanged() // 🔹 RecyclerView को Refresh करो
    }
}
