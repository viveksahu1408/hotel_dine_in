package AdapterClasses.Adapters

import AdapterClasses.Adapters.FoodCategoryAdapter.FoodViewHolder
import ModalClasses.Booking
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.R

class BookingAdapter (private val bookingList: List<Booking>):
RecyclerView.Adapter<BookingAdapter.ViewHOlder>() {


    class ViewHOlder(view: View): RecyclerView.ViewHolder(view) {

        val restaurantName: TextView = view.findViewById(R.id.tvRestaurantName)
        val bookingDate: TextView = view.findViewById(R.id.tvBookingDate)
        val bookingTime: TextView = view.findViewById(R.id.tvBookingTime)
        val status: TextView = view.findViewById(R.id.tvBookingStatus)
        val amount: TextView = view.findViewById(R.id.tvTotalAmount)
        val paymentStatus: TextView = view.findViewById(R.id.tvPaymentStatus)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingAdapter.ViewHOlder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking_history, parent, false)
        return ViewHOlder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookingAdapter.ViewHOlder, position: Int) {
        val booking = bookingList[position]
        holder.restaurantName.text = booking.restaurantName
        holder.bookingDate.text = "Date: ${booking.bookingDate}"
        holder.bookingTime.text = "Time: ${booking.bookingTime}"
        holder.status.text = "Status: ${booking.status}"
        holder.amount.text = "Amount: ₹${booking.totalAmount}"
        holder.paymentStatus.text = "Payment: ${booking.paymentStatus}"    }

    override fun getItemCount() = bookingList.size


}