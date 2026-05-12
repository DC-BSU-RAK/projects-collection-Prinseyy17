package com.gundambase.portable.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gundambase.portable.R
import com.gundambase.portable.data.MobileSuit
import com.gundambase.portable.utils.ThemeManager

/**
 * RecyclerView adapter for the Hangar (home) screen.
 * Each card shows: faction badge, model number, suit name, pilot, and combat rating.
 *
 * Swap in real artwork by placing images named after each suit's `drawableResName`
 * in res/drawable/ — the adapter will automatically pick them up via getIdentifier().
 */
class MobileSuitAdapter(
    private val context: Context,
    private val suits: List<MobileSuit>,
    private val onSuitClick: (MobileSuit) -> Unit
) : RecyclerView.Adapter<MobileSuitAdapter.SuitViewHolder>() {

    class SuitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView           = itemView.findViewById(R.id.card_suit)
        val imgSuit: ImageView       = itemView.findViewById(R.id.img_suit_thumbnail)
        val tvModelNumber: TextView  = itemView.findViewById(R.id.tv_model_number)
        val tvSuitName: TextView     = itemView.findViewById(R.id.tv_suit_name)
        val tvPilot: TextView        = itemView.findViewById(R.id.tv_pilot)
        val tvFaction: TextView      = itemView.findViewById(R.id.tv_faction_badge)
        val tvRating: TextView       = itemView.findViewById(R.id.tv_combat_rating)
        val ratingBar: View          = itemView.findViewById(R.id.view_rating_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuitViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mobile_suit, parent, false)
        return SuitViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuitViewHolder, position: Int) {
        val suit = suits[position]

        // ── Text ─────────────────────────────────────────────────────────────
        holder.tvModelNumber.text  = suit.modelNumber
        holder.tvSuitName.text     = suit.name
        holder.tvPilot.text        = "Pilot: ${suit.pilot}"
        holder.tvFaction.text      = suit.faction.displayName
        holder.tvRating.text       = "Combat Rating: ${suit.combatRating}/100"

        // ── Image (drop in your custom drawable with the matching name) ───────
        val resId = context.resources.getIdentifier(
            suit.drawableResName, "drawable", context.packageName
        )
        if (resId != 0) {
            holder.imgSuit.setImageResource(resId)
        } else {
            // Fallback placeholder — replace @drawable/suit_placeholder with your asset
            holder.imgSuit.setImageResource(R.drawable.suit_placeholder)
        }

        // ── Faction badge colour ──────────────────────────────────────────────
        val badgeColor = when (suit.faction) {
            com.gundambase.portable.data.Faction.EFSF       ->
                ContextCompat.getColor(context, R.color.efsf_primary)
            com.gundambase.portable.data.Faction.ZEON       ->
                ContextCompat.getColor(context, R.color.zeon_primary)
            com.gundambase.portable.data.Faction.COLONIES   ->
                ContextCompat.getColor(context, R.color.colony_primary)
            com.gundambase.portable.data.Faction.INDEPENDENT ->
                ContextCompat.getColor(context, R.color.independent_primary)
        }
        holder.tvFaction.setBackgroundColor(badgeColor)

        // ── Rating bar fill (scales 0–100 → 0–full width, done in post) ──────
        holder.ratingBar.post {
            val maxWidth = (holder.ratingBar.parent as View).width
            val fillColor = ThemeManager.getAccentColor(context)
            holder.ratingBar.setBackgroundColor(fillColor)
            val params = holder.ratingBar.layoutParams
            params.width = (maxWidth * suit.combatRating / 100.0).toInt()
                .coerceAtLeast(8)
            holder.ratingBar.layoutParams = params
        }

        // ── Card tint to reflect current faction alignment ────────────────────
        holder.card.setCardBackgroundColor(ThemeManager.getSurfaceColor(context))

        // ── Click ─────────────────────────────────────────────────────────────
        holder.card.setOnClickListener { onSuitClick(suit) }
    }

    override fun getItemCount(): Int = suits.size
}
