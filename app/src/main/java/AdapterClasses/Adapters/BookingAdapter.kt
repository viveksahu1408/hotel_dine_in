package AdapterClasses.Adapters

import ModalClasses.Booking
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.R

class BookingAdapter(private var bookingList: MutableList<Booking>) :
    RecyclerView.Adapter<BookingAdapter.ViewHOlder>() {

    class ViewHOlder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantName: TextView = view.findViewById(R.id.tvRestaurant2Name)
        val bookingDate: TextView = view.findViewById(R.id.tvBookingDate)
        val bookingTime: TextView = view.findViewById(R.id.tvBookingTime)
        val status: TextView = view.findViewById(R.id.tvBookingStatus)
        val amount: TextView = view.findViewById(R.id.tvTotalAmount)
        val paymentStatus: TextView = view.findViewById(R.id.tvPaymentStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHOlder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking_history, parent, false)
        return ViewHOlder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHOlder, position: Int) {
        val booking = bookingList[position]
        holder.restaurantName.text = booking.restaurantName
        holder.bookingDate.text = "Date: ${booking.bookingDate}"
        holder.bookingTime.text = "Time: ${booking.slotStartTime}"
        holder.status.text = "Status: ${booking.bookingStatus}"
        holder.amount.text = "Number of guest: ${booking.numberOfGuest}"
        holder.paymentStatus.text = " ${booking.specialRequest}"
    }

    override fun getItemCount() = bookingList.size

    // ✅ `updateList()` Method Implement Karo
    fun updateList(newBookings: List<Booking>) {
        bookingList.clear()
        bookingList.addAll(newBookings)
        notifyDataSetChanged()  // RecyclerView Refresh Hoga
    }
}
