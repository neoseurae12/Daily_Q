package online.dailyq

import android.app.Application
import online.dailyq.api.ApiService

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        ApiService.init()   // API 초기화
    }
}