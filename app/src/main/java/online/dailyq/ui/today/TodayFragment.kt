package online.dailyq.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import online.dailyq.api.response.Question
import online.dailyq.databinding.FragmentTodayBinding
import online.dailyq.ui.base.BaseFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

class TodayFragment : BaseFragment() {

    var _binding: FragmentTodayBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // REST API with Retrofit 버전
        viewLifecycleOwner.lifecycleScope.launch {
            val qidDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val question: Question = api.getQuestion(LocalDate.now())

            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.KOREA)
            binding.date.text = dateFormat.format(qidDateFormat.parse(question.id))
            binding.question.text = question.text
        }

        /* ‘Hello, world!’ API 버전
        Thread {
            val url = URL("http://10.0.2.2:5000/v1/hello-world")

            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.requestMethod = "GET"

            conn.setRequestProperty(
                "Accept",
                "application/json"
            )   // text/plain -> application/json

            conn.connect()

            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            val questionBody = reader.readText()
            reader.close()
            conn.disconnect()

            /* JSONObject 버전
            val json = JSONObject(questionBody)
            val message = json.getString("message")
            val date = json.getString("date")
             */

            // Gson 버전
            val gson = Gson()
            val helloWorld = gson.fromJson(questionBody, HelloWorld::class.java)

            // Date 타입 반영
            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.KOREA)

            activity?.runOnUiThread {
                /* JSONObject 버전
                binding.question.text = message
                binding.date.text = date
                 */

                // Gson 버전
                binding.question.text = helloWorld.message
                //binding.date.text = helloWorld.date
                binding.date.text = dateFormat.format(helloWorld.date)  // Date 타입 반영
            }
        }.start()
         */
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}