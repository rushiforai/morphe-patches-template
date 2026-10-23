package dev.rushi.morphepatches.patches.speedtest

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.rushi.morphepatches.patches.shared.Constants.COMPATIBILITY_SPEEDTEST

/**
 * Stops the app from sending analytics events to Firebase.
 *
 * `com.ookla.tools.logging.android.FirebaseAnalyticsEventLogger` is the single funnel the
 * Ookla logging layer uses to reach `com.google.firebase.analytics.FirebaseAnalytics`.
 * Every public entry point is stubbed to a bare `return-void`, so no event, user property
 * or counter ever reaches the SDK. The SDK itself is left initialised, which avoids
 * null-pointer crashes in code that expects a live FirebaseAnalytics instance.
 */
@Suppress("unused")
val disableFirebaseAnalyticsPatch = bytecodePatch(
    name = "Disable Firebase analytics",
    description = "Stops the app from logging events, user properties and counters to Firebase Analytics.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SPEEDTEST)

    execute {
        val stub = """
            return-void
        """

        // h(String eventName, Map attrs) -> FirebaseAnalytics.logEvent
        FirebaseLogEventFingerprint.method.addInstructions(0, stub)

        // f(String attrName, String value) -> FirebaseAnalytics.setUserProperty
        FirebaseLogAttributeFingerprint.method.addInstructions(0, stub)

        // g(String attrName) -> increments a counter, delegates to f(...)
        FirebaseLogCounterFingerprint.method.addInstructions(0, stub)

        // a(String name) -> FirebaseAnalytics.setUserId
        FirebaseLogUserPropertyFingerprint.method.addInstructions(0, stub)
    }
}
