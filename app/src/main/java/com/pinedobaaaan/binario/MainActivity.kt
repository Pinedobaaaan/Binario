package com.pinedobaaaan.binario

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var inputText: EditText
    private lateinit var binaryOutput: EditText
    private lateinit var convertButton: Button
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinner = findViewById(R.id.sp_origen)
        inputText = findViewById(R.id.et_input_text)
        binaryOutput = findViewById(R.id.et_binary_code)
        convertButton = findViewById(R.id.btnCalc)
        saveButton = findViewById(R.id.tv_save)

        // Updated options in English
        val options = arrayOf("Number", "Letter", "String")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        convertButton.setOnClickListener {
            val input = inputText.text.toString()
            val selectedOption = spinner.selectedItem.toString()

            if (input.isNotEmpty()) {
                binaryOutput.setText(convertToBinary(input, selectedOption))
            } else {
                Toast.makeText(this, "Please enter valid text.", Toast.LENGTH_SHORT).show()
            }
        }

        saveButton.setOnClickListener {
            val binaryText = binaryOutput.text.toString()

            if (binaryText.isNotEmpty()) {
                saveConversionResult(binaryText)
            } else {
                Toast.makeText(this, "There is no text to save.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertToBinary(input: String, option: String): String {
        return when (option) {
            "Number" -> {
                if (input.toIntOrNull() != null) {
                    input.toInt().toString(2) // Convert number to binary
                } else {
                    "Error: Not a valid number."
                }
            }
            "Letter" -> {
                if (input.length == 1) {
                    input[0].code.toString(2) // Convert letter to binary
                } else {
                    "Error: Please enter only one letter."
                }
            }
            "String" -> {
                input.toCharArray().joinToString(" ") { it.code.toString(2) } // Convert string to binary
            }
            else -> "Error: Invalid option."
        }
    }

    private fun saveConversionResult(binaryText: String) {
        Toast.makeText(this, "Result saved: $binaryText", Toast.LENGTH_SHORT).show()
    }
}
