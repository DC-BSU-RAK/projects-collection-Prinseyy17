package com.gundambase.portable.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gundambase.portable.R
import com.gundambase.portable.adapters.MobileSuitAdapter
import com.gundambase.portable.data.MobileSuit
import com.gundambase.portable.data.MobileSuitRepository
import com.gundambase.portable.utils.BaseThemedActivity
import com.gundambase.portable.utils.ThemeManager

/**
 * MainActivity hosts three "tabs" via a BottomNavigationView:
 *
 *   1. Hangar (nav_hangar)         — RecyclerView of Mobile Suits
 *   2. System Config (nav_config)  — Faction alignment preference dialog
 *   3. Haro's Guide (nav_haro)     — Instruction dialog (also reachable via FAB)
 *
 * Theme is re-applied on every resume so a faction change takes effect immediately.
 */
class MainActivity : BaseThemedActivity() {

    // ── Views ────────────────────────────────────────────────────────────────
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fabHaro: FloatingActionButton
    private lateinit var tvFactionBanner: TextView
    private lateinit var viewHangar: View
    private lateinit var viewEmpty: View

    private lateinit var adapter: MobileSuitAdapter

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setupToolbar()
        setupRecyclerView()
        setupBottomNavigation()
        setupFab()
        applyFactionBanner()
    }

    override fun onResume() {
        super.onResume()

        // Refresh the banner and adapter colours after returning from detail screen.
        applyFactionBanner()

        // Safer RecyclerView refresh
        if (::adapter.isInitialized && adapter.itemCount > 0) {
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }
    }

    // ── Setup helpers ─────────────────────────────────────────────────────────

    private fun bindViews() {
        recyclerView    = findViewById(R.id.recycler_suits)
        bottomNav       = findViewById(R.id.bottom_navigation)
        fabHaro         = findViewById(R.id.fab_haro)
        tvFactionBanner = findViewById(R.id.tv_faction_banner)
        viewHangar      = findViewById(R.id.layout_hangar)
        viewEmpty       = findViewById(R.id.layout_placeholder)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "GUNDAM BASE PORTABLE"
    }

    private fun setupRecyclerView() {
        adapter = MobileSuitAdapter(this, MobileSuitRepository.suits) { suit ->
            navigateToDetail(suit)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter       = adapter
    }

    /**
     * CRITICAL FIX:
     * Avoid recursive BottomNavigation callbacks that cause crashes.
     */
    private fun setupBottomNavigation() {

        bottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_hangar -> {
                    showHangar()
                    true
                }

                R.id.nav_config -> {
                    // Post the dialog show to avoid blocking the listener
                    bottomNav.post {
                        showSystemConfigDialog()
                    }
                    
                    // Don't change selection - keep Hangar selected
                    false
                }

                R.id.nav_haro -> {
                    // Post the dialog show to avoid blocking the listener
                    bottomNav.post {
                        showHarosGuideDialog()
                    }
                    
                    // Don't change selection - keep Hangar selected
                    false
                }

                else -> false
            }
        }

        bottomNav.selectedItemId = R.id.nav_hangar
    }

    private fun setupFab() {
        fabHaro.setOnClickListener {
            showHarosGuideDialog()
        }
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    private fun showHangar() {
        viewHangar.visibility = View.VISIBLE
        viewEmpty.visibility  = View.GONE
    }

    private fun navigateToDetail(suit: MobileSuit) {

        val intent = Intent(this, SuitDetailActivity::class.java).apply {
            putExtra(SuitDetailActivity.EXTRA_MOBILE_SUIT, suit)
        }

        startActivity(intent)
    }

    // ── Faction banner ────────────────────────────────────────────────────────

    private fun applyFactionBanner() {

        val isZeon = ThemeManager.isZeon(this)

        tvFactionBanner.text =
            if (isZeon)
                "⚡  ZEON OS ACTIVE  ⚡  Sieg Zeon!"
            else
                "🌍  EFSF OS ACTIVE  🌍  For the Federation!"

        tvFactionBanner.setBackgroundColor(
            ThemeManager.getPrimaryColor(this)
        )

        tvFactionBanner.setTextColor(
            ThemeManager.getOnSurfaceColor(this)
        )
    }

    // ── System Config Dialog ──────────────────────────────────────────────────

    private fun showSystemConfigDialog() {
        try {
            val dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_system_config, null, false)

            val radioGroup  =
                dialogView.findViewById<RadioGroup>(R.id.radio_group_faction) ?: return

            val rbEfsf =
                dialogView.findViewById<RadioButton>(R.id.rb_efsf) ?: return

            val rbZeon =
                dialogView.findViewById<RadioButton>(R.id.rb_zeon) ?: return

            val btnSave =
                dialogView.findViewById<Button>(R.id.btn_save_config) ?: return

            val btnCancel =
                dialogView.findViewById<Button>(R.id.btn_cancel_config) ?: return

            val tvCurrentOS =
                dialogView.findViewById<TextView>(R.id.tv_current_os) ?: return

            // Pre-select current faction
            val currentFaction = ThemeManager.getCurrentFaction(this)

            if (currentFaction == ThemeManager.FACTION_ZEON)
                rbZeon.isChecked = true
            else
                rbEfsf.isChecked = true

            tvCurrentOS.text =
                "Current OS: ${ThemeManager.getFactionLabel(this)}"

            // Use faction-specific dialog theme
            val dialogTheme = if (ThemeManager.isZeon(this)) {
                R.style.DialogTheme_Zeon
            } else {
                R.style.DialogTheme
            }

            val dialog = AlertDialog.Builder(this, dialogTheme)
                .setView(dialogView)
                .setCancelable(true)
                .create()

            btnSave.setOnClickListener {

                val selected =
                    when (radioGroup.checkedRadioButtonId) {

                        R.id.rb_zeon ->
                            ThemeManager.FACTION_ZEON

                        else ->
                            ThemeManager.FACTION_EFSF
                    }

                ThemeManager.saveFaction(this, selected)

                dialog.dismiss()

                // Recreate activity so the new theme is applied
                recreate()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ── Haro's Guide Dialog ───────────────────────────────────────────────────

    private fun showHarosGuideDialog() {
        try {
            val dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_haros_guide, null, false)

            val btnClose =
                dialogView.findViewById<Button>(R.id.btn_close_guide) ?: return

            // Use faction-specific dialog theme
            val dialogTheme = if (ThemeManager.isZeon(this)) {
                R.style.DialogTheme_Zeon
            } else {
                R.style.DialogTheme
            }

            val dialog = AlertDialog.Builder(this, dialogTheme)
                .setView(dialogView)
                .setCancelable(true)
                .create()

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}