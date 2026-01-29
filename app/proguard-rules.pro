# Harry Potter App ProGuard Rules

# 1. Basic debugging info
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 2. Kotlinx Serialization
# Keep the @Serializable classes and their companion objects (serializers)
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable <fields>;
}
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keep class * {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class * extends kotlinx.serialization.KSerializer {
    public static ** INSTANCE;
}

# 3. Ktor
-keep class io.ktor.** { *; }
# Ignore JVM-only classes that Ktor references but aren't present on Android
-dontwarn java.lang.management.**
-dontwarn kotlinx.coroutines.debug.**
-dontwarn kotlinx.coroutines.instrumentation.**

# 4. Room
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao

# 5. Koin
-keep class org.koin.** { *; }

# 6. Navigation 3 Keys
# We must keep the navigation keys as they are serialized/deserialized
-keep @kotlinx.serialization.Serializable class com.romanpolach.harrypotter.navigation.** { *; }