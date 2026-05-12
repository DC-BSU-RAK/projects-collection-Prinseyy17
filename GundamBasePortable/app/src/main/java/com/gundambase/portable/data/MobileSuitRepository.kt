package com.gundambase.portable.data

/**
 * Static repository of Mobile Suit data.
 * In a production app this would come from a local Room database or remote API.
 * Drawable resource names reference placeholders — drop in your own assets with matching names.
 */
object MobileSuitRepository {

    val suits: List<MobileSuit> = listOf(

        // ── Earth Federation ─────────────────────────────────────────────────
        MobileSuit(
            id = 1,
            modelNumber = "RX-78-2",
            name = "Gundam",
            fullDesignation = "RX-78-2 Gundam",
            faction = Faction.EFSF,
            pilot = "Amuro Ray",
            height = "18.0 m",
            weight = "60.0 t",
            powerOutput = "1,380 kW",
            thrustTotal = "55,500 kg",
            drawableResName = "suit_rx78_2",        // → res/drawable/suit_rx78_2.png
            primaryWeapon = Weapon(
                name = "BOWA XBR-M-79-07G Beam Rifle",
                type = "Beam Rifle",
                damage = "Very High",
                range = "Long"
            ),
            secondaryWeapon = Weapon(
                name = "RX·M-Sh-008/S-01025 Shield",
                type = "Defensive Shield",
                damage = "Medium (bash)",
                range = "Short"
            ),
            specialSystem = "Core Block System / Luna Titanium Alloy Armour",
            combatRating = 95,
            description = "The legendary prototype mobile suit developed by the Earth Federation's " +
                    "Project V. Its revolutionary design — featuring a learning computer and the " +
                    "Core Block escape system — changed the course of the One Year War. Piloted " +
                    "by civilian Amuro Ray, it became a symbol of Federation resolve."
        ),

        MobileSuit(
            id = 2,
            modelNumber = "RGM-79",
            name = "GM",
            fullDesignation = "RGM-79 GM",
            faction = Faction.EFSF,
            pilot = "Various Federation Pilots",
            height = "18.5 m",
            weight = "41.2 t",
            powerOutput = "976 kW",
            thrustTotal = "45,500 kg",
            drawableResName = "suit_rgm79",           // → res/drawable/suit_rgm79.png
            primaryWeapon = Weapon(
                name = "BLASH·HB-L-03/N-STD-MS Beam Spray Gun",
                type = "Beam Spray Gun",
                damage = "Medium",
                range = "Medium"
            ),
            secondaryWeapon = Weapon(
                name = "Federation Standard Saber",
                type = "Beam Saber",
                damage = "High",
                range = "Close"
            ),
            specialSystem = "Titanium Alloy Composite Armour",
            combatRating = 68,
            description = "Mass-produced backbone of the Earth Federation Forces. Simplified from " +
                    "RX-78 data, the GM sacrificed raw performance for producibility. Thousands " +
                    "were fielded in the final months of the One Year War, turning the tide " +
                    "against Zeon through sheer numbers."
        ),

        MobileSuit(
            id = 3,
            modelNumber = "RX-93",
            name = "ν Gundam",
            fullDesignation = "RX-93 ν Gundam",
            faction = Faction.EFSF,
            pilot = "Amuro Ray",
            height = "23.0 m",
            weight = "75.4 t",
            powerOutput = "2,980 kW",
            thrustTotal = "88,600 kg",
            drawableResName = "suit_rx93",             // → res/drawable/suit_rx93.png
            primaryWeapon = Weapon(
                name = "Beam Rifle",
                type = "Beam Rifle",
                damage = "Very High",
                range = "Long"
            ),
            secondaryWeapon = Weapon(
                name = "Fin Funnel System",
                type = "Remote Funnel Weapons",
                damage = "Extreme (x6 units)",
                range = "Omni-directional"
            ),
            specialSystem = "Fin Funnels / Psycoframe Cockpit",
            combatRating = 99,
            description = "Designed by Amuro Ray himself for use against Neo Zeon in UC 0093. " +
                    "Equipped with the revolutionary Psycoframe technology woven into the cockpit, " +
                    "allowing a Newtype pilot's psychokinetic energy to directly resonate with the " +
                    "mobile suit. Its Fin Funnels can generate an I-Field barrier."
        ),

        // ── Principality of Zeon ─────────────────────────────────────────────
        MobileSuit(
            id = 4,
            modelNumber = "MS-06F",
            name = "Zaku II",
            fullDesignation = "MS-06F Zaku II",
            faction = Faction.ZEON,
            pilot = "Char Aznable / Various Zeon Pilots",
            height = "17.5 m",
            weight = "56.2 t",
            powerOutput = "976 kW",
            thrustTotal = "43,300 kg",
            drawableResName = "suit_ms06f",            // → res/drawable/suit_ms06f.png
            primaryWeapon = Weapon(
                name = "ZMP-50D/120mm Machine Gun",
                type = "Projectile Machine Gun",
                damage = "Medium",
                range = "Medium"
            ),
            secondaryWeapon = Weapon(
                name = "H&L-SB25K/280mmA-N Bazooka",
                type = "Rocket Bazooka",
                damage = "High (AoE)",
                range = "Medium"
            ),
            specialSystem = "Mono-Eye Sensor System / Zaku Machine Gun Drum Magazine",
            combatRating = 72,
            description = "The iconic workhorse of the Principality of Zeon. The Zaku II was " +
                    "the world's first true mass-production mobile suit, and its shock deployment " +
                    "during the One Week Battle rendered the Earth Federation's conventional " +
                    "forces obsolete overnight. Instantly recognisable by its single Mono-Eye."
        ),

        MobileSuit(
            id = 5,
            modelNumber = "MS-09B",
            name = "Dom",
            fullDesignation = "MS-09B Dom",
            faction = Faction.ZEON,
            pilot = "Black Tri-Stars",
            height = "18.6 m",
            weight = "81.8 t",
            powerOutput = "1,269 kW",
            thrustTotal = "None (hover jets)",
            drawableResName = "suit_ms09b",            // → res/drawable/suit_ms09b.png
            primaryWeapon = Weapon(
                name = "H&L-GB05R/360mm Giant Bazooka",
                type = "Giant Bazooka",
                damage = "Very High (AoE)",
                range = "Medium"
            ),
            secondaryWeapon = Weapon(
                name = "Scattering Mega Particle Gun",
                type = "Particle Cannon",
                damage = "High",
                range = "Short-Medium"
            ),
            specialSystem = "Ground-Effect Hover Propulsion / Heat Saber",
            combatRating = 80,
            description = "A heavy assault mobile suit designed for terrestrial combat, utilising " +
                    "thermonuclear jet engines for near-frictionless hovering across any surface. " +
                    "Infamous as the weapon of the Black Tri-Stars, who executed the devastating " +
                    "Jet Stream Attack that cost the Federation dearly early in the war."
        ),

        MobileSuit(
            id = 6,
            modelNumber = "MSN-02",
            name = "Zeong",
            fullDesignation = "MSN-02 Perfect Zeong",
            faction = Faction.ZEON,
            pilot = "Char Aznable",
            height = "74.5 m",
            weight = "415.0 t",
            powerOutput = "9,400 kW",
            thrustTotal = "360,000 kg",
            drawableResName = "suit_msn02",            // → res/drawable/suit_msn02.png
            primaryWeapon = Weapon(
                name = "Sturm Faust (×5 per arm)",
                type = "Wired Mega Particle Cannons",
                damage = "Extreme",
                range = "Omni-directional"
            ),
            secondaryWeapon = Weapon(
                name = "Head Mega Particle Cannon",
                type = "Fixed Particle Cannon",
                damage = "Very High",
                range = "Long"
            ),
            specialSystem = "Newtype-use Psycommu System / Detachable Waist Unit",
            combatRating = 97,
            description = "Zeon's terrifying Newtype-use mobile armour, completed — controversially " +
                    "without its legs — just in time for A Baoa Qu. The Psycommu system allows " +
                    "its wired arms to be guided by a Newtype's brainwaves for all-angle attacks " +
                    "that no conventional pilot could counter."
        ),

        // ── A.C. / Colonies ──────────────────────────────────────────────────
        MobileSuit(
            id = 7,
            modelNumber = "XXXG-00W0",
            name = "Wing Gundam Zero",
            fullDesignation = "XXXG-00W0 Wing Gundam Zero",
            faction = Faction.COLONIES,
            pilot = "Heero Yuy / Quatre Raberba Winner",
            height = "16.7 m",
            weight = "8.0 t",
            powerOutput = "3,732 kW",
            thrustTotal = "75,934 kg",
            drawableResName = "suit_wing_zero",        // → res/drawable/suit_wing_zero.png
            primaryWeapon = Weapon(
                name = "Twin Buster Rifle",
                type = "Mega-Buster Rifle",
                damage = "Catastrophic",
                range = "Extreme"
            ),
            secondaryWeapon = Weapon(
                name = "Beam Saber (×2)",
                type = "Beam Saber",
                damage = "High",
                range = "Close"
            ),
            specialSystem = "Zero System Combat Computer / Neo-Bird Mode Transform",
            combatRating = 98,
            description = "The original Gundam design from which all five Gundams of Operation " +
                    "Meteor were derived. Its Zero System feeds predictive battlefield data " +
                    "directly to the pilot — but at the cost of mental stability. Its Twin " +
                    "Buster Rifle is powerful enough to destroy a space colony in a single blast."
        ),

        MobileSuit(
            id = 8,
            modelNumber = "GN-001",
            name = "Gundam Exia",
            fullDesignation = "GN-001 Gundam Exia",
            faction = Faction.INDEPENDENT,
            pilot = "Setsuna F. Seiei",
            height = "18.3 m",
            weight = "57.2 t",
            powerOutput = "GN Drive (unlimited)",
            thrustTotal = "Classification: Black",
            drawableResName = "suit_gn001",            // → res/drawable/suit_gn001.png
            primaryWeapon = Weapon(
                name = "GN Sword",
                type = "GN Particle Sword/Rifle Combo",
                damage = "Very High",
                range = "Short-Medium"
            ),
            secondaryWeapon = Weapon(
                name = "GN Blades (×2)",
                type = "Pair Particle Blades",
                damage = "High",
                range = "Close"
            ),
            specialSystem = "GN Drive / Seven Sword System / Trans-Am (S2 onwards)",
            combatRating = 92,
            description = "Celestial Being's close-combat specialist Gundam, powered by an " +
                    "eternal GN Drive. Designed as a melee-focused unit with a seven-sword " +
                    "loadout, Exia became the instrument of Setsuna's mission to eradicate " +
                    "war — by fighting. Its Trans-Am system triples output for short bursts."
        )
    )

    fun findById(id: Int): MobileSuit? = suits.firstOrNull { it.id == id }

    fun getByFaction(faction: Faction): List<MobileSuit> = suits.filter { it.faction == faction }
}
