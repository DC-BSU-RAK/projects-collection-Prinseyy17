package com.gundambase.portable.ui

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.gundambase.portable.R
import com.gundambase.portable.data.MobileSuit
import com.gundambase.portable.utils.BaseThemedActivity
import com.gundambase.portable.utils.ThemeManager

/**
 * SuitDetailActivity — "Suit Specs" screen.
 *
 * Receives a [MobileSuit] Parcelable via [EXTRA_MOBILE_SUIT] Intent extra and
 * renders all its detail fields. Themed by the active faction alignment.
 *
 * The "Back to Hangar" button finishes this activity and returns to MainActivity.
 */
class SuitDetailActivity : BaseThemedActivity() {

    companion object {
        const val EXTRA_MOBILE_SUIT = "extra_mobile_suit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suit_detail)

        // ── Retrieve data ─────────────────────────────────────────────────────
        // Use the type-safe overload on API 33+ and fall back on older APIs
        val suit: MobileSuit? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOBILE_SUIT, MobileSuit::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MOBILE_SUIT)
        }

        if (suit == null) {
            finish()
            return
        }

        setupToolbar(suit.fullDesignation)
        bindData(suit)
        applyFactionAccents()
    }

    // ── Toolbar ───────────────────────────────────────────────────────────────

    private fun setupToolbar(title: String) {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { finish() }
    }

    // ── Data binding ──────────────────────────────────────────────────────────

    private fun bindData(suit: MobileSuit) {
        // Header image
        val imgSuit = findViewById<ImageView>(R.id.img_suit_detail)
        val resId = resources.getIdentifier(
            suit.drawableResName, "drawable", packageName
        )
        if (resId != 0) imgSuit.setImageResource(resId)
        else imgSuit.setImageResource(R.drawable.suit_placeholder)

        // Designation row
        findViewById<TextView>(R.id.tv_detail_model_number).text = suit.modelNumber
        findViewById<TextView>(R.id.tv_detail_name).text         = suit.fullDesignation
        findViewById<TextView>(R.id.tv_detail_faction).text      = suit.faction.displayName
        findViewById<TextView>(R.id.tv_detail_pilot).text        = "Pilot: ${suit.pilot}"

        // Specs grid
        findViewById<TextView>(R.id.tv_height).text        = suit.height
        findViewById<TextView>(R.id.tv_weight).text        = suit.weight
        findViewById<TextView>(R.id.tv_power_output).text  = suit.powerOutput
        findViewById<TextView>(R.id.tv_thrust).text        = suit.thrustTotal

        // Weapons
        val pw = suit.primaryWeapon
        findViewById<TextView>(R.id.tv_primary_weapon_name).text   = pw.name
        findViewById<TextView>(R.id.tv_primary_weapon_type).text   = pw.type
        findViewById<TextView>(R.id.tv_primary_weapon_damage).text = "DMG: ${pw.damage}"
        findViewById<TextView>(R.id.tv_primary_weapon_range).text  = "Range: ${pw.range}"

        val sw = suit.secondaryWeapon
        findViewById<TextView>(R.id.tv_secondary_weapon_name).text   = sw.name
        findViewById<TextView>(R.id.tv_secondary_weapon_type).text   = sw.type
        findViewById<TextView>(R.id.tv_secondary_weapon_damage).text = "DMG: ${sw.damage}"
        findViewById<TextView>(R.id.tv_secondary_weapon_range).text  = "Range: ${sw.range}"

        // Special system
        findViewById<TextView>(R.id.tv_special_system).text = suit.specialSystem

        // Combat rating progress bar
        val ratingBar = findViewById<ProgressBar>(R.id.progress_combat_rating)
        ratingBar.progress = suit.combatRating
        val tvRatingValue = findViewById<TextView>(R.id.tv_rating_value)
        tvRatingValue.text = "${suit.combatRating} / 100"

        // Description
        findViewById<TextView>(R.id.tv_suit_description).text = suit.description

        // Back button
        findViewById<Button>(R.id.btn_back_to_hangar).setOnClickListener { finish() }
    }

    // ── Accent theming ────────────────────────────────────────────────────────

    private fun applyFactionAccents() {
        val accentColor  = ThemeManager.getAccentColor(this)
        val primaryColor = ThemeManager.getPrimaryColor(this)

        // Tint the section header dividers
        listOf(
            R.id.divider_weapons,
            R.id.divider_specs,
            R.id.divider_system
        ).forEach { id ->
            try { findViewById<android.view.View>(id).setBackgroundColor(accentColor) }
            catch (_: Exception) { }
        }

        // Tint the Back button
        try {
            val btn = findViewById<Button>(R.id.btn_back_to_hangar)
            btn.setBackgroundColor(primaryColor)
            btn.setTextColor(ThemeManager.getOnSurfaceColor(this))
        } catch (_: Exception) { }
    }
}
