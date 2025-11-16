package com.byatara.aplikasipertama

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class Kalkulator : AppCompatActivity() {

    private lateinit var display: EditText

    // Variabel untuk menyimpan operasi
    private var currentNumber = ""
    private var previousNumber = ""
    private var operator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalkulator)

        // Inisialisasi display
        display = findViewById(R.id.angka)
        display.isFocusable = false
        display.isCursorVisible = false

        // Tombol angka
        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)

        // Tombol operator
        val btnTambah: Button = findViewById(R.id.ps)
        val btnKurang: Button = findViewById(R.id.ms)
        val btnKali: Button = findViewById(R.id.x)
        val btnBagi: Button = findViewById(R.id.gr)
        val btnSamaDengan: Button = findViewById(R.id.sm)

        // Tombol lainnya
        val btnClear: Button = findViewById(R.id.btnc)
        val btnDelete: Button = findViewById(R.id.del)
        val btnTitik: Button = findViewById(R.id.titik)
        val btnPersen: Button = findViewById(R.id.persen)
        val btnAkar: Button = findViewById(R.id.plusminus) // Tombol akar kuadrat

        // Set listener untuk tombol angka
        val numberButtons = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
        for (button in numberButtons) {
            button.setOnClickListener {
                onNumberClick(button.text.toString())
            }
        }

        // Set listener untuk tombol operator
        btnTambah.setOnClickListener { onOperatorClick("+") }
        btnKurang.setOnClickListener { onOperatorClick("-") }
        btnKali.setOnClickListener { onOperatorClick("×") }
        btnBagi.setOnClickListener { onOperatorClick("÷") }

        // Tombol sama dengan
        btnSamaDengan.setOnClickListener { onEqualsClick() }

        // Tombol Clear (AC)
        btnClear.setOnClickListener { onClearClick() }

        // Tombol Delete
        btnDelete.setOnClickListener { onDeleteClick() }

        // Tombol titik/koma
        btnTitik.setOnClickListener { onDecimalClick() }

        // Tombol persen
        btnPersen.setOnClickListener { onPercentClick() }

        // Tombol akar kuadrat
        btnAkar.setOnClickListener { onSquareRootClick() }
    }

    // Fungsi ketika tombol angka ditekan
    private fun onNumberClick(number: String) {
        if (isNewOperation) {
            currentNumber = number
            isNewOperation = false
        } else {
            // Batasi panjang input maksimal 15 digit
            if (currentNumber.replace(",", "").length < 15) {
                currentNumber += number
            }
        }
        updateDisplay()
    }

    // Fungsi ketika tombol operator ditekan
    private fun onOperatorClick(op: String) {
        if (currentNumber.isNotEmpty()) {
            if (previousNumber.isNotEmpty() && operator.isNotEmpty() && !isNewOperation) {
                calculate()
            }
            previousNumber = currentNumber
            operator = op
            isNewOperation = true
        } else if (previousNumber.isNotEmpty()) {
            // Ganti operator jika belum input angka baru
            operator = op
        }
    }

    // Fungsi ketika tombol sama dengan ditekan
    private fun onEqualsClick() {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty() && operator.isNotEmpty()) {
            calculate()
            operator = ""
            previousNumber = ""
            isNewOperation = true
        }
    }

    // Fungsi untuk melakukan perhitungan
    private fun calculate() {
        try {
            val num1 = previousNumber.replace(",", ".").toDouble()
            val num2 = currentNumber.replace(",", ".").toDouble()
            var result = 0.0

            when (operator) {
                "+" -> result = num1 + num2
                "-" -> result = num1 - num2
                "×" -> result = num1 * num2
                "÷" -> {
                    if (num2 != 0.0) {
                        result = num1 / num2
                    } else {
                        display.setText("Tidak dapat dibagi nol")
                        resetCalculator()
                        return
                    }
                }
            }

            // Format hasil
            currentNumber = formatResult(result)
            updateDisplay()
        } catch (e: Exception) {
            display.setText("Error")
            resetCalculator()
        }
    }

    // Fungsi untuk format hasil
    private fun formatResult(result: Double): String {
        return if (result.isInfinite() || result.isNaN()) {
            "Error"
        } else if (result % 1.0 == 0.0 && result <= Long.MAX_VALUE && result >= Long.MIN_VALUE) {
            // Bilangan bulat
            result.toLong().toString()
        } else {
            // Bilangan desimal - batasi 8 digit di belakang koma
            val formatted = String.format("%.8f", result)
                .trimEnd('0')
                .trimEnd('.')

            // Jika terlalu panjang, gunakan scientific notation
            if (formatted.length > 15) {
                String.format("%.6e", result).replace(".", ",")
            } else {
                formatted.replace(".", ",")
            }
        }
    }

    // Fungsi untuk tombol Clear (AC)
    private fun onClearClick() {
        resetCalculator()
        display.setText("0")
    }

    // Fungsi untuk reset kalkulator
    private fun resetCalculator() {
        currentNumber = ""
        previousNumber = ""
        operator = ""
        isNewOperation = true
    }

    // Fungsi untuk tombol Delete
    private fun onDeleteClick() {
        if (currentNumber.isNotEmpty() && !isNewOperation) {
            currentNumber = currentNumber.dropLast(1)
            updateDisplay()
        }
    }

    // Fungsi untuk tombol desimal
    private fun onDecimalClick() {
        if (isNewOperation) {
            currentNumber = "0,"
            isNewOperation = false
        } else {
            // Cek apakah sudah ada koma
            if (!currentNumber.contains(",")) {
                if (currentNumber.isEmpty()) {
                    currentNumber = "0,"
                } else {
                    currentNumber += ","
                }
            }
        }
        updateDisplay()
    }

    // Fungsi untuk tombol persen
    private fun onPercentClick() {
        if (currentNumber.isNotEmpty() && currentNumber != "0") {
            try {
                val num = currentNumber.replace(",", ".").toDouble()
                val result = num / 100

                currentNumber = formatResult(result)
                updateDisplay()
                isNewOperation = true
            } catch (e: Exception) {
                display.setText("Error")
                resetCalculator()
            }
        }
    }

    // Fungsi untuk tombol akar kuadrat
    private fun onSquareRootClick() {
        if (currentNumber.isNotEmpty()) {
            try {
                val num = currentNumber.replace(",", ".").toDouble()

                if (num < 0) {
                    display.setText("Error: Bilangan negatif")
                    resetCalculator()
                    return
                }

                val result = sqrt(num)
                currentNumber = formatResult(result)
                updateDisplay()
                isNewOperation = true
            } catch (e: Exception) {
                display.setText("Error")
                resetCalculator()
            }
        }
    }

    // Fungsi untuk update display
    private fun updateDisplay() {
        if (currentNumber.isEmpty()) {
            display.setText("0")
        } else {
            // Batasi panjang tampilan
            val displayText = if (currentNumber.length > 15) {
                currentNumber.substring(0, 15)
            } else {
                currentNumber
            }
            display.setText(displayText)
        }
    }
}