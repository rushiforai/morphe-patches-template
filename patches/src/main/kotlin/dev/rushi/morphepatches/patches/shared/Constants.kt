package dev.rushi.morphepatches.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY_SPEEDTEST = Compatibility(
        name = "Speedtest",
        packageName = "org.zwanoo.android.speedtest",
        apkFileType = ApkFileType.APK,
        // Speedtest brand blue.
        appIconColor = 0x00A0E0,
        targets = listOf(
            // Version this patch set was developed and verified against.
            AppTarget(version = "7.1.2"),
        )
    )
}
