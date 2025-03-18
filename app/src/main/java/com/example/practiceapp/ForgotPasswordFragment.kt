import ApiClasses.RetrofitClient
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.practiceapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ForgotPasswordFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        emailEditText = view.findViewById(R.id.emailEditText)  // ✅ findViewById() ka sahi use
        sendButton = view.findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isNotEmpty()) {
                sendForgotPasswordRequest(email)
            } else {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    @SuppressLint("CheckResult")
    private fun sendForgotPasswordRequest(email: String) {
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())  // ✅ Correct way

        RetrofitClient.instance.forgotPassword(emailRequestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Toast.makeText(requireContext(), " ${response.message}", Toast.LENGTH_SHORT).show()  // ✅ requireContext() use kiya
            }, { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })
    }
}