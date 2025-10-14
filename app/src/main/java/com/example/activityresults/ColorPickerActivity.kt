package com.example.activityresults

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ColorPickerActivity : AppCompatActivity() {
    
    private var selectedColor: String? = null
    private lateinit var colorButtons: List<Button>
    private lateinit var submitButton: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_color_picker)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupColorButtons()
        setupSubmitButton()
    }
    
    private fun setupColorButtons() {
        // Initialize color buttons
        colorButtons = listOf(
            findViewById(R.id.btnRed),
            findViewById(R.id.btnOrange),
            findViewById(R.id.btnYellow),
            findViewById(R.id.btnGreen),
            findViewById(R.id.btnBlue),
            findViewById(R.id.btnIndigo),
            findViewById(R.id.btnViolet)
        )
        
        // Set click listeners for each color button
        colorButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                selectColor(button, index)
            }
        }
    }
    
    private fun setupSubmitButton() {
        submitButton = findViewById(R.id.btnSubmit)
        submitButton.setOnClickListener {
            if (selectedColor != null) {
                val message = getString(R.string.color_chosen_message, selectedColor)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                
                // Return result to MainActivity
                returnResultToMainActivity()
            } else {
                Toast.makeText(this, "Please select a color first!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun returnResultToMainActivity() {
        val resultIntent = Intent()
        
        // Get the color value and message
        val backgroundColor = getColorValue(selectedColor!!)
        val colorMessage = getString(R.string.color_chosen_message, selectedColor)
        
        // Put data in intent
        resultIntent.putExtra("backgroundColor", backgroundColor)
        resultIntent.putExtra("colorMessage", colorMessage)
        
        // Set result and finish
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
    
    private fun getColorValue(colorName: String): Int {
        return when (colorName) {
            getString(R.string.red) -> Color.parseColor("#FF0000")
            getString(R.string.orange) -> Color.parseColor("#FFA500")
            getString(R.string.yellow) -> Color.parseColor("#FFFF00")
            getString(R.string.green) -> Color.parseColor("#008000")
            getString(R.string.blue) -> Color.parseColor("#0000FF")
            getString(R.string.indigo) -> Color.parseColor("#4B0082")
            getString(R.string.violet) -> Color.parseColor("#8A2BE2")
            else -> Color.parseColor("#FFFFFF")
        }
    }
    
    private fun selectColor(selectedButton: Button, colorIndex: Int) {
        // Simple selection - just store the color name
        selectedColor = when (colorIndex) {
            0 -> getString(R.string.red)
            1 -> getString(R.string.orange)
            2 -> getString(R.string.yellow)
            3 -> getString(R.string.green)
            4 -> getString(R.string.blue)
            5 -> getString(R.string.indigo)
            6 -> getString(R.string.violet)
            else -> null
        }
    }
}