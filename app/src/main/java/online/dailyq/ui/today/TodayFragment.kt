package online.dailyq.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import online.dailyq.api.response.HelloWorld
import online.dailyq.databinding.FragmentTodayBinding
import online.dailyq.ui.base.BaseFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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

        Thread {
            val url = URL("http://10.0.2.2:5000/v1/hello-world")

            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.requestMethod = "GET"

            conn.setRequestProperty("Accept", "application/json")   // text/plain -> application/json

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

            activity?.runOnUiThread {
                /* JSONObject 버전
                binding.question.text = message
                binding.date.text = date
                 */

                // Gson 버전
                binding.question.text = helloWorld.message
                binding.date.text = helloWorld.date
            }
        }.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}