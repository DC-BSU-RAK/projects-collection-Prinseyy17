# Gundam Base Portable
### Android Mobile Suit Combat Database

A Gundam-themed Android app with dynamic faction-based theming, built in Kotlin with Material 3.

---

## Project Structure

```
GundamBasePortable/
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/gundambase/portable/
│       │   ├── adapters/
│       │   │   └── MobileSuitAdapter.kt      ← RecyclerView adapter
│       │   ├── data/
│       │   │   ├── MobileSuit.kt             ← Data model + Weapon + Faction enum
│       │   │   └── MobileSuitRepository.kt   ← 8 pre-loaded Mobile Suits
│       │   ├── ui/
│       │   │   ├── SplashActivity.kt         ← Launch screen
│       │   │   ├── MainActivity.kt           ← Hangar + bottom nav + dialogs
│       │   │   └── SuitDetailActivity.kt     ← Suit Specs screen
│       │   └── utils/
│       │       ├── ThemeManager.kt           ← SharedPreferences + colour helpers
│       │       └── BaseThemedActivity.kt     ← Auto-applies faction theme
│       └── res/
│           ├── anim/                         ← Slide transition animations
│           ├── color/                        ← Bottom nav color selector
│           ├── drawable/                     ← Icons, placeholders, gradients
│           ├── font/                         ← Fonts for app
│           ├── layout/                       ← All XML layouts
│           ├── menu/                         ← Bottom navigation menu
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               └── themes.xml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## Quick Start

1. **Open in Android Studio** → File → Open → select the `GundamBasePortable` folder.
2. **Sync Gradle** — Android Studio will prompt automatically.
3. **Add fonts** (see section below).
4. **Run** on a device/emulator running Android 8.0+ (API 26+).

---

### Mobile Suit Images
Each suit has a `drawableResName` in `MobileSuitRepository.kt`. Add matching PNG/WebP files:

| Suit                    | File name (res/drawable/)       |
|-------------------------|---------------------------------|
| RX-78-2 Gundam          | `suit_rx78_2.png`               |
| RGM-79 GM               | `suit_rgm79.png`                |
| RX-93 ν Gundam          | `suit_rx93.png`                 |
| MS-06F Zaku II          | `suit_ms06f.png`                |
| MS-09B Dom              | `suit_ms09b.png`                |
| MSN-02 Perfect Zeong    | `suit_msn02.png`                |
| XXXG-00W0 Wing Gundam Zero | `suit_wing_zero.png`         |
| GN-001 Gundam Exia      | `suit_gn001.png`                |

The adapter uses `resources.getIdentifier()` to find them automatically.
If not found, `suit_placeholder.xml` is shown.

## Feature Overview

### View 1 — The Hangar (RecyclerView)
- Lists all 8 Mobile Suits in a scrollable card list
- Each card shows: suit thumbnail, model number, name, pilot, faction badge, and combat rating bar
- Tap any card → navigates to Suit Specs

### View 2 — Suit Specs (SuitDetailActivity)
- Receives `MobileSuit` via `Intent` extra (Parcelable)
- Collapsing toolbar with suit hero image
- Displays: model number, full designation, faction, pilot, height, weight, power output, thrust
- Weapons: primary and secondary with type, damage rating, and range
- Special systems, combat rating progress bar, full unit profile
- **"Back to Hangar"** button (also tappable via toolbar back arrow / system back)

### System Config (BottomNav → System Config)
- AlertDialog with two RadioButton options: **EFSF** / **Zeon**
- Saves selection to `SharedPreferences` via `ThemeManager`
- Calls `recreate()` to immediately apply the new theme across all views

### Haro's Guide (FAB / BottomNav → Haro's Guide)
- Informational modal with step-by-step pilot instructions
- Accessible from both the FAB (bottom-right) and the bottom navigation tab

### Dynamic Theming
| Faction | Background     | Primary Colour | Accent / Secondary |
|---------|---------------|----------------|--------------------|
| EFSF    | `#0D1B2E` (navy) | `#1A3A6B` (Federation blue) | `#4FC3F7` (sky blue) |
| Zeon    | `#0C1A0F` (dark green) | `#1B3A24` (military green) | `#CC1122` (Zeon red) |

Theme is applied in `BaseThemedActivity.getTheme()` before layout inflation — zero flicker.

---

## SharedPreferences Reference

**File name:** `gundam_base_prefs`  
**Key:** `faction_alignment`  
**Values:** `"EFSF"` (default) | `"ZEON"`

Access anywhere via:
```kotlin
ThemeManager.getCurrentFaction(context)   // → "EFSF" or "ZEON"
ThemeManager.saveFaction(context, "ZEON") // persist
ThemeManager.isZeon(context)              // → Boolean
```

---

## Adding More Mobile Suits

Open `MobileSuitRepository.kt` and append a new `MobileSuit(...)` entry to the `suits` list.
Add the matching drawable, and it will automatically appear in the Hangar.

---

## Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| `androidx.core:core-ktx` | 1.12.0 | Kotlin extensions |
| `androidx.appcompat:appcompat` | 1.6.1 | ActionBar / Toolbar |
| `com.google.android.material:material` | 1.11.0 | BottomNav, FAB, CardView, CollapsingToolbar |
| `androidx.recyclerview:recyclerview` | 1.3.2 | Hangar list |
| `kotlin-parcelize` | (plugin) | `@Parcelize` for Intent data |

---

*"A mobile suit is only as good as the pilot within it."*  
*— Haro, Gundam Base Portable AI*
