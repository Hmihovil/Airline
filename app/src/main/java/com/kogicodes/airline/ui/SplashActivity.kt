package com.kogicodes.airline.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.models.custom.Status
import com.kogicodes.airline.R
import com.kogicodes.airline.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ViewUtils().makeFullScreen(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        viewModel.token()
        viewModel.observeToken().observe(this, Observer {
            ViewUtils.setStatus(this, view, it.status, it.message, false, ViewUtils.ErrorViewTypes.TOAST, it.exception)
            if (it.status == Status.SUCCESS) {

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else if (it.status == Status.ERROR) {
                Toast.makeText(this, "Error loading token", Toast.LENGTH_LONG).show()
            }

        })

    }


}
