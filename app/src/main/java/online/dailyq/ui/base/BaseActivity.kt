package online.dailyq.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import online.dailyq.api.ApiService

abstract class BaseActivity : AppCompatActivity() {
    val api: ApiService by lazy { ApiService.getApi() }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}