package com.ppedrosa.android.experimentos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ppedrosa.android.experimentos.ui.CategoryView

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton =findViewById(R.id.start_button)

        startButton.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryView::class.java)
            startActivity(intent)
        }

    }
}