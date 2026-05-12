package com.gundambase.portable.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data model representing a Mobile Suit entry in the Gundam Base database.
 * Implements Parcelable so instances can be passed between Activities via Intent extras.
 */
@Parcelize
data class MobileSuit(
    val id: Int,
    val modelNumber: String,        // e.g. "RX-78-2"
    val name: String,               // e.g. "Gundam"
    val fullDesignation: String,    // e.g. "RX-78-2 Gundam"
    val faction: Faction,
    val pilot: String,
    val height: String,             // in meters
    val weight: String,             // in tonnes (dry)
    val powerOutput: String,        // in kW
    val thrustTotal: String,        // in kg
    val drawableResName: String,    // resource name for suit image placeholder
    val primaryWeapon: Weapon,
    val secondaryWeapon: Weapon,
    val specialSystem: String,      // e.g. "Core Block System"
    val combatRating: Int,          // 1–100
    val description: String
) : Parcelable

@Parcelize
data class Weapon(
    val name: String,
    val type: String,               // e.g. "Beam Rifle", "Machine Gun"
    val damage: String,             // e.g. "High", "Medium"
    val range: String               // e.g. "Long", "Short"
) : Parcelable

enum class Faction(val displayName: String) {
    EFSF("Earth Federation Space Force"),
    ZEON("Principality of Zeon"),
    COLONIES("Space Colonies / ESUN"),
    INDEPENDENT("Independent / Other")
}
