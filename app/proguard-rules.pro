# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/HDD/android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclassmembers class * extends com.svyat.sample.alienclock.data.AlienDataIntegrator {
    public static *;
}
-keepattributes Signature
-keepattributes Exceptions
-keepnames class * implements com.svyat.sample.alienclock.controller.AlienController
-keepnames class * implements com.svyat.sample.alienclock.controller.AlienControlled
-keepclassmembers class * implements com.svyat.sample.alienclock.content.AlienContentBrick {
    @com.google.gson.annotations.Expose <fields>;
}