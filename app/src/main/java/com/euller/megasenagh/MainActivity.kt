package com.euller.megasenagh

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val editNumber: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val buttonGenerate: Button = findViewById(R.id.btn_generate)

        val result = prefs.getString("result", null)

        result?.let {
            txtResult.text = "Última aposta = $it"
        }

        buttonGenerate.setOnClickListener {

            val text = editNumber.text.toString()

            numberGenerator(text, txtResult)

        }
    }

    private fun numberGenerator(editNumber: String, txtResult: TextView) {

        if (editNumber.isEmpty()) {
            Toast.makeText(this, "Por favor digite um número de 6 até 15", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val qtd = editNumber.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Por favor digite um número de 6 até 15", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }

        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

    }
}
