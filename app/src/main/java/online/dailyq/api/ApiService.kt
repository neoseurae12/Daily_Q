package online.dailyq.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import online.dailyq.api.response.Question
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    companion object {

        // 싱글톤 인스턴스를 보관할 변수
        private var API: ApiService? = null

        // ApiService 인스턴스를 Retrofit으로 생성
        private fun create(): ApiService {  // private => 외부에서 호출 불가
            val gson: Gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val api = retrofit.create(ApiService::class.java)
            return api
        }

        // API 변수를 `초기화`하는 init() 메서드
        fun init() = API ?: synchronized(this) {
            API ?: create().also { API = it }
        }

        // API 변수에 `접근`하는 getApi() 메서드
        fun getApi(): ApiService = API!!    // 이미 API가 생성 완료된 것으로 가정
    }

    @GET("v1/questions/{qid}")
    suspend fun getQuestion(@Path("qid") qid: String): Question
}