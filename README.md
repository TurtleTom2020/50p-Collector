# 50p Collector 🪙

An Android collection tracker for special UK circulating 50p designs, excluding everyday Britannia and Shield coins.

## Get the APK

Go to **Actions → Build Android APK**, open a successful run and download the **50p-Collector-debug-apk** artifact. Unzip it and install the `.apk` on your Android phone. This is a debug build, not a Play Store release.

## Features

- Search and filter by collected or missing
- Keep a count of duplicate coins
- Local on-device storage
- JSON export and import
- Attempts to load *actual coin photographs* from Wikimedia Commons, linking to the original source and licence. Internet is required to retrieve images, and some images may be unavailable. No generic silver coin artwork is substituted.

## Important limitations

This is an initial **incomplete catalogue**, not yet verified against the complete Royal Mint circulation dataset. Mintage numbers and rarity rankings are intentionally omitted rather than guessed. Images may not be available for every coin; Wikimedia Commons photos are not Royal Mint photographs and individual reuse conditions vary. Images are not bundled for offline use.

Official Royal Mint reference: https://www.royalmint.com/collect/collector-resources/uk-coin-mintage-figures/50p-mintage-figures/

## Build locally

Install JDK 17, Android SDK platform 35 and Gradle 8.9, then run `gradle assembleDebug` from the repository root.
