package com.example.furrishta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this);

        // In Register and Login activities, after successful login or registration
        // In Register and Login activities, after successful login or registration
        val intent: Intent = Intent(
            this,
            CreateProfileActivity::class.java
        )
        startActivity(intent)
        finish()

    }
}
