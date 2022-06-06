#This is just example 3th party proguard rules definition
# Room is automatically bundled with rules
# https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:room/room-runtime/proguard-rules.pro

-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**