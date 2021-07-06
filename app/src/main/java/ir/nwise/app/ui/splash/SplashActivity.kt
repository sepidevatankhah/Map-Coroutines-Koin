package ir.nwise.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ir.nwise.app.ui.MainActivity
import ir.nwise.app.R
import ir.nwise.app.databinding.FragmentSplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_splash)


        Handler().postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                intent.fillIn(this.intent, 0)
                startActivity(intent)
                finish()
            },
            700
        )
    }


}