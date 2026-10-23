package dev.rushi.morphepatches.patches.speedtest

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.rushi.morphepatches.patches.shared.Constants.COMPATIBILITY_SPEEDTEST

/**
 * Hides every ad surface by forcing the central ad-state gate to report "ads off".
 *
 * `com.ookla.speedtest.ads.b$d.a(I)Z` normally returns `true` only when the ad state is 2.
 * Every ad renderer in the app (banner, native, DFP ad loader, internet-screen renderer)
 * calls it before showing anything, so returning `true` unconditionally suppresses them all
 * without disabling the ad SDK or breaking the purchase flow.
 */
@Suppress("unused")
val disableAdsPatch = bytecodePatch(
    name = "Disable ads",
    description = "Hides banner and native ads by forcing the app's ad-state gate to report ads as disabled.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SPEEDTEST)

    execute {
        // Original: const/4 v0, 0x2 / if-ne v1, v0, ... / return false
        // Patched:  return true
        AdStateFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )
    }
}
