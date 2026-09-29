# House Rules — Android app

Native Android port of [Agent House Rules](https://agent-house-rules.pages.dev):
answer 10 plain questions, get a short list of safety rules to copy and paste
into your AI agent (Muse, Grok Bot, or anything else), plus a checklist of the
app's own safety settings to change.

About 3 minutes. Everything stays on the device.

## Privacy

- No network calls made by the app itself.
- No tracking, no analytics, no cookies, no ads.
- No account, no sign-up. Answers live in memory only — close the app and they're gone.
- **Zero Android permissions declared.** The manifest requests nothing.
  Source links open in the user's own browser via `ACTION_VIEW` intents; the
  browser does the networking, not this app.

## Pricing

- **Google Play: $1 (paid, set at publish).** Google Play does not allow
  converting a free listing to paid later, so the paid tier is decided up front.
  The $1 goes to Orbital Desk LLC.
- **F-Droid: free.** Same app, same features.
- **Web version: free** at agent-house-rules.pages.dev.
- No ads, no tracking, no in-app purchases in any version. The paid Play
  listing exists only because Play's rules force the free/paid choice at
  publish time — nothing is withheld from the free builds.

## Open in Android Studio

Requirements: Android Studio Ladybug (2024.2) or newer, JDK 17 (bundled with
Android Studio), Android SDK with API 34.

1. `File → Open…` and pick this folder (`house-rules-android`).
2. Let Gradle sync finish (first sync downloads dependencies from
   Maven Central / Google's Maven — no proprietary SDKs involved).
3. `Run → Run 'app'` on a connected phone or emulator (API 26+).

## Build a debug APK

```sh
./gradlew assembleDebug
# → app/build/outputs/apk/debug/app-debug.apk
adb install app/build/outputs/apk/debug/app-debug.apk
```

The Gradle wrapper (`gradlew` + `gradle/wrapper/gradle-wrapper.jar`) is committed,
so no local Gradle install is needed — the wrapper downloads Gradle 8.7 on
first run.

Release builds need a signing key — generate one in
`Build → Generate Signed Bundle / APK…` (keep the keystore private; never
commit it).

## Dependencies

Everything comes from Maven Central / Google Maven. Zero proprietary
dependencies, no Google Play Services, no Firebase, no crash reporters:

| Artifact | Version | Why |
|---|---|---|
| androidx.core:core-ktx | 1.13.1 | Kotlin extensions |
| androidx.activity:activity-compose | 1.9.2 | `setContent` |
| androidx.compose:compose-bom | 2024.06.00 | Compose UI + Material3 |
| androidx.navigation:navigation-compose | 2.7.7 | Screen navigation |

Build tooling: Android Gradle Plugin 8.5.2, Kotlin 2.0.20, Gradle 8.7,
`compileSdk`/`targetSdk` 34, `minSdk` 26.

This makes the app F-Droid friendly: 100% open-source dependency tree,
no anti-features (no ads, no tracking, no non-free network services).

## Project layout

```
app/src/main/
  AndroidManifest.xml            # zero permissions
  java/com/orbitaldesk/houserules/
    MainActivity.kt              # entry point
    HouseRulesApp.kt             # navigation + in-memory quiz state
    data/
      QuizData.kt                # the 10 questions + cited help pages
      RulesEngine.kt             # rules-text generator (ported from the site)
      Content.kt                 # paste guides, checklists, test prompts, guides, about
    ui/
      Theme.kt                   # black-and-white Material3 theme
      Components.kt              # scaffold, checklist rows, content blocks
      screens/                   # Welcome, Agent, Quiz, Result, GuideDetail, About
  res/                           # launcher icon, app name
```

## Store listing

Suggested Play/F-Droid listing copy lives in [listing.md](listing.md).

## License

MIT — see [LICENSE](LICENSE).
