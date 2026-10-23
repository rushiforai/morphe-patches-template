package dev.rushi.morphepatches.patches.speedtest

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

/**
 * Central ad-state gate.
 *
 * `com.ookla.speedtest.ads.b$d.a(I)Z` is a static helper that maps the ad-state int
 * (0 = unknown, 1 = ads on, 2 = ads off) to a boolean. It returns `true` only when the
 * state is 2, i.e. "ads should be hidden".
 *
 * Verified callers (all ad surfaces):
 *  - com.ookla.speedtest.bannerad.a -> q()V
 *  - com.ookla.speedtest.nativead.j -> p(Lcom/ookla/speedtest/ads/b;)V
 *  - com.ookla.speedtest.ads.h -> a()V
 *  - com.ookla.speedtest.ads.p -> a()V
 *  - com.ookla.speedtest.ads.c -> a(Lcom/ookla/speedtest/ads/b;)Z
 *
 * Forcing it to `true` hides every ad surface without touching the ad SDK.
 */
object AdStateFingerprint : Fingerprint(
    definingClass = "Lcom/ookla/speedtest/ads/b\$d;",
    name = "a",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("I"),
)

/**
 * Firebase event logger used by the Ookla tools logging layer.
 *
 * All four public entry points funnel into `com.google.firebase.analytics.FirebaseAnalytics`:
 *  - a(Ljava/lang/String;)V        -> FirebaseAnalytics.d(String)
 *  - f(Ljava/lang/String;Ljava/lang/String;)V -> FirebaseAnalytics.e(String, String)
 *  - g(Ljava/lang/String;)V        -> delegates to f(...)
 *  - h(Ljava/lang/String;Ljava/util/Map;)V -> builds a Bundle and logs an event
 */
object FirebaseLogEventFingerprint : Fingerprint(
    definingClass = "Lcom/ookla/tools/logging/android/FirebaseAnalyticsEventLogger;",
    name = "h",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    parameters = listOf("Ljava/lang/String;", "Ljava/util/Map;"),
)

object FirebaseLogAttributeFingerprint : Fingerprint(
    definingClass = "Lcom/ookla/tools/logging/android/FirebaseAnalyticsEventLogger;",
    name = "f",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    parameters = listOf("Ljava/lang/String;", "Ljava/lang/String;"),
)

object FirebaseLogCounterFingerprint : Fingerprint(
    definingClass = "Lcom/ookla/tools/logging/android/FirebaseAnalyticsEventLogger;",
    name = "g",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    parameters = listOf("Ljava/lang/String;"),
)

object FirebaseLogUserPropertyFingerprint : Fingerprint(
    definingClass = "Lcom/ookla/tools/logging/android/FirebaseAnalyticsEventLogger;",
    name = "a",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    parameters = listOf("Ljava/lang/String;"),
)
