package com.gundambase.portable.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.gundambase.portable.R

/**
 * ThemeManager handles reading and writing the Faction Alignment preference,
 * and exposes helper colours/styles driven by that preference.
 *
 * SharedPreferences key-value pairs stored:
 *   KEY_FACTION  →  "EFSF" | "ZEON"   (String)
 */
object ThemeManager {

    private const val PREFS_NAME   = "gundam_base_prefs"
    const val KEY_FACTION          = "faction_alignment"

    const val FACTION_EFSF  = "EFSF"
    const val FACTION_ZEON  = "ZEON"

    // ── Public API ───────────────────────────────────────────────────────────

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** Returns the currently saved faction, defaulting to EFSF. */
    fun getCurrentFaction(context: Context): String =
        getPrefs(context).getString(KEY_FACTION, FACTION_EFSF) ?: FACTION_EFSF

    /** Persists the selected faction. */
    fun saveFaction(context: Context, faction: String) {
        getPrefs(context).edit().putString(KEY_FACTION, faction).apply()
    }

    fun isZeon(context: Context): Boolean =
        getCurrentFaction(context) == FACTION_ZEON

    // ── Theme Style Attribute ────────────────────────────────────────────────

    /**
     * Returns the correct AppTheme style resource to apply via
     * setTheme() BEFORE setContentView() in each Activity.
     */
    fun getActivityTheme(context: Context): Int =
        if (isZeon(context)) R.style.Theme_GundamBase_Zeon
        else R.style.Theme_GundamBase_EFSF

    // ── Colour Helpers (for programmatic tinting) ───────────────────────────

    fun getPrimaryColor(context: Context): Int =
        if (isZeon(context))
            ContextCompat.getColor(context, R.color.zeon_primary)
        else
            ContextCompat.getColor(context, R.color.efsf_primary)

    fun getAccentColor(context: Context): Int =
        if (isZeon(context))
            ContextCompat.getColor(context, R.color.zeon_accent)
        else
            ContextCompat.getColor(context, R.color.efsf_accent)

    fun getBackgroundColor(context: Context): Int =
        if (isZeon(context))
            ContextCompat.getColor(context, R.color.zeon_background)
        else
            ContextCompat.getColor(context, R.color.efsf_background)

    fun getSurfaceColor(context: Context): Int =
        if (isZeon(context))
            ContextCompat.getColor(context, R.color.zeon_surface)
        else
            ContextCompat.getColor(context, R.color.efsf_surface)

    fun getOnSurfaceColor(context: Context): Int =
        if (isZeon(context))
            ContextCompat.getColor(context, R.color.zeon_on_surface)
        else
            ContextCompat.getColor(context, R.color.efsf_on_surface)

    fun getFactionLabel(context: Context): String =
        if (isZeon(context)) "Principality of Zeon" else "Earth Federation Space Force"

    fun getFactionTagline(context: Context): String =
        if (isZeon(context)) "\"Sieg Zeon!\"" else "\"For the Earth Federation!\""
}
