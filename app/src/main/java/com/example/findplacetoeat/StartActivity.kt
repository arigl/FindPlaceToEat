package com.example.findplacetoeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        generateButton.setOnClickListener{
            val msg: String = loginUsernameField.text.toString()

            if(msg.trim().length>0) {
                startActivity(Intent(this, PlaceActivity::class.java).apply{putExtra("zipcode", loginUsernameField.text)})
            }else{

            }
        }
    }
}
