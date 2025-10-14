package com.example.activityresults

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var mainButton: Button
    private lateinit var selectedColorButton: Button
    
    // Activity result launcher
    private val colorPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        handleColorPickerResult(result.resultCode, result.data)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize buttons
        mainButton = findViewById(R.id.mainButton)
        selectedColorButton = findViewById(R.id.selectedColorButton)
        
        // Set button click listener
        mainButton.setOnClickListener {
            launchColorPicker()
        }
    }
    
    private fun launchColorPicker() {
        val intent = Intent(this, ColorPickerActivity::class.java)
        colorPickerLauncher.launch(intent)
    }
    
    private fun handleColorPickerResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let { intent ->
                    val backgroundColor = intent.getIntExtra("backgroundColor", Color.WHITE)
                    val colorMessage = intent.getStringExtra("colorMessage") ?: "No color selected"
                    
                    // Show everything in the button
                    showSelectedColorInButton(backgroundColor, colorMessage)
                }
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this, getString(R.string.no_color_chosen), Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun showSelectedColorInButton(backgroundColor: Int, colorMessage: String) {
        // Show the selected color button with color and message
        selectedColorButton.setBackgroundColor(backgroundColor)
        selectedColorButton.text = colorMessage
        selectedColorButton.visibility = Button.VISIBLE
        
        // Set text color for contrast
        val textColor = getContrastColor(backgroundColor)
        selectedColorButton.setTextColor(textColor)
    }
    
    private fun getContrastColor(backgroundColor: Int): Int {
        // Calculate luminance of the background color
        val red = Color.red(backgroundColor)
        val green = Color.green(backgroundColor)
        val blue = Color.blue(backgroundColor)
        
        // Calculate relative luminance
        val luminance = (0.299 * red + 0.587 * green + 0.114 * blue) / 255
        
        // Return white for dark backgrounds, black for light backgrounds
        return if (luminance < 0.5) Color.WHITE else Color.BLACK
    }
}