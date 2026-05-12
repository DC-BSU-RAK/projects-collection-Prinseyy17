package com.example.tennoelementsynthesizer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val selectedElements = mutableListOf<String>()

    private lateinit var tvSlot1: TextView
    private lateinit var tvSlot2: TextView
    private lateinit var tvResult: TextView
    private lateinit var cardResult: MaterialCardView

    private lateinit var btnHeat: MaterialButton
    private lateinit var btnCold: MaterialButton
    private lateinit var btnToxin: MaterialButton
    private lateinit var btnElectricity: MaterialButton

    private lateinit var btnClear: MaterialButton
    private lateinit var btnSynthesize: MaterialButton

    private lateinit var fabInstructions: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        setupClickListeners()
    }

    private fun bindViews() {
        tvSlot1        = findViewById(R.id.tv_slot1)
        tvSlot2        = findViewById(R.id.tv_slot2)
        tvResult       = findViewById(R.id.tv_result)
        cardResult     = findViewById(R.id.card_result)

        btnHeat        = findViewById(R.id.btn_heat)
        btnCold        = findViewById(R.id.btn_cold)
        btnToxin       = findViewById(R.id.btn_toxin)
        btnElectricity = findViewById(R.id.btn_electricity)

        btnClear      = findViewById(R.id.btn_clear)
        btnSynthesize = findViewById(R.id.btn_synthesize)

        fabInstructions = findViewById(R.id.fab_instructions)
    }

    private fun setupClickListeners() {
        btnHeat.setOnClickListener        { onElementSelected("Heat") }
        btnCold.setOnClickListener        { onElementSelected("Cold") }
        btnToxin.setOnClickListener       { onElementSelected("Toxin") }
        btnElectricity.setOnClickListener { onElementSelected("Electricity") }

        btnClear.setOnClickListener      { onClearClicked() }
        btnSynthesize.setOnClickListener { onSynthesizeClicked() }

        fabInstructions.setOnClickListener { showInstructionsDialog() }
    }

    private fun onElementSelected(element: String) {
        if (selectedElements.size >= 2) {
            Snackbar.make(
                findViewById(R.id.root_layout),
                getString(R.string.snackbar_slots_full),
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(ContextCompat.getColor(this, R.color.tenno_surface))
                .setTextColor(ContextCompat.getColor(this, R.color.tenno_gold))
                .show()
            return
        }

        selectedElements.add(element)

        when (selectedElements.size) {
            1 -> {
                tvSlot1.text = element.uppercase()
                animateSlotUpdate(tvSlot1)
            }
            2 -> {
                tvSlot2.text = element.uppercase()
                animateSlotUpdate(tvSlot2)
            }
        }

        resetResultDisplay()
    }

    private fun onClearClicked() {
        selectedElements.clear()
        tvSlot1.text = getString(R.string.slot_empty)
        tvSlot2.text = getString(R.string.slot_empty)
        resetResultDisplay()
    }

    private fun onSynthesizeClicked() {
        if (selectedElements.size < 2) {
            showErrorResult(getString(R.string.error_insufficient))
            return
        }

        if (selectedElements[0] == selectedElements[1]) {
            showErrorResult(getString(R.string.error_duplicate))
            return
        }

        val sortedPair = selectedElements.sorted()
        val first  = sortedPair[0]
        val second = sortedPair[1]

        val result: String = when (Pair(first, second)) {
            Pair("Cold", "Heat")         -> getString(R.string.result_blast)
            Pair("Cold", "Toxin")        -> getString(R.string.result_viral)
            Pair("Cold", "Electricity")  -> getString(R.string.result_magnetic)
            Pair("Heat", "Toxin")        -> getString(R.string.result_gas)
            Pair("Electricity", "Heat")  -> getString(R.string.result_radiation)
            Pair("Electricity", "Toxin") -> getString(R.string.result_corrosive)
            else                         -> getString(R.string.result_unknown)
        }

        showSuccessResult(result)
    }

    private fun showSuccessResult(result: String) {
        tvResult.text     = result.uppercase()
        tvResult.textSize = 28f
        tvResult.setTextColor(ContextCompat.getColor(this, R.color.tenno_cyan))
        animateResultReveal()
    }

    private fun showErrorResult(message: String) {
        tvResult.text     = message
        tvResult.textSize = 14f
        tvResult.setTextColor(ContextCompat.getColor(this, R.color.text_error))
        animateResultReveal()
    }

    private fun resetResultDisplay() {
        tvResult.text     = getString(R.string.result_awaiting)
        tvResult.textSize = 26f
        tvResult.setTextColor(ContextCompat.getColor(this, R.color.tenno_cyan))
    }

    private fun animateSlotUpdate(view: TextView) {
        view.animate()
            .scaleX(1.15f)
            .scaleY(1.15f)
            .setDuration(120)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(120)
                    .start()
            }
            .start()
    }

    private fun animateResultReveal() {
        cardResult.alpha = 0f
        cardResult.animate()
            .alpha(1f)
            .setDuration(350)
            .start()
    }

    private fun showInstructionsDialog() {
        AlertDialog.Builder(this, R.style.Theme_TennoElementSynthesizer)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(getString(R.string.dialog_acknowledge)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }
}