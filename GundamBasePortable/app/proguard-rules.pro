# Gundam Base Portable ProGuard rules

# Keep data models (Parcelable)
-keep class com.gundambase.portable.data.** { *; }

# Keep Kotlin Parcelize generated code
-keep class **$$Creator { *; }

# Material / AndroidX
-keep class com.google.android.material.** { *; }
-keep class androidx.** { *; }

# Keep enum names for SharedPreferences string storage
-keepnames enum com.gundambase.portable.data.Faction
