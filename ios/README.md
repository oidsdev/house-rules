# House Rules (iOS)

A free, open-source iOS app that helps you write simple "house rules" for an
AI agent (Muse, Grok Bot, or another agent). Answer 10 easy questions, get a
short list of rules to copy and paste into your agent, plus a settings
checklist, paste instructions, test prompts, and short guides.

This is a native port of the free web tool at
https://agent-house-rules.pages.dev — it produces the exact same rules for
the exact same answers.

## Privacy

- Everything stays on your device. No network calls, no accounts, no
  analytics, no tracking.
- Your quiz answers are kept only in memory while the app runs.
- Your checklist ticks are stored in `UserDefaults` on the phone only.
- The only links that ever leave the app are the help-page and news-story
  citations, and they open only if you tap them.

## Open it in Xcode

Requirements: a Mac with Xcode 16+ installed, and an iPhone running iOS 17+.

1. Download or clone this repository onto your Mac.
2. Double-click `HouseRules.xcodeproj`. (Open the **.xcodeproj**, not a
   folder — that is what tells Xcode this is an app project.)
3. Xcode may ask you to pick a development team the first time:
   **Signing & Capabilities → Team → your Apple ID**. (A free Apple ID works
   for running on your own phone.)
4. Plug in your iPhone with a cable, unlock it, and tap **Trust** if asked.
5. In Xcode's toolbar, pick your iPhone from the run-destination dropdown
   (it appears next to the "HouseRules" scheme).
6. Press **⌘R** (Product → Run). The first install may ask you on the phone
   to trust the developer under **Settings → General → VPN & Device
   Management**.

No CocoaPods, no Swift Package dependencies, no build scripts — it just
builds.

## Project layout

```
HouseRules.xcodeproj/      The real Xcode project (open this)
HouseRules/
  HouseRulesApp.swift      App entry point (SwiftUI lifecycle, no storyboards)
  Models.swift             Data types: questions, options, blocks, guides
  RulesBuilder.swift       Exact port of the web app's buildRules()
  Content.swift            The 10 questions, sources, paste steps, checklists,
                           test prompts, short guides, and About text
  Views.swift              All SwiftUI screens
  Assets.xcassets/         App icon + accent color
LICENSE                    MIT
```

## Notes for contributors

- The rule text in `RulesBuilder.swift` is intentionally kept identical to
  the web app's `buildRules()`. If you change wording there, change it in
  both places so identical answers always produce identical rules.
- Steps marked **Checked** cite the company's own help pages (linked in the
  app). Anything we could not confirm is marked **Not checked** — keep that
  honest labeling if you add new advice.
- Keep it offline: this app must never gain network calls, accounts,
  analytics, or tracking. That is the whole point.
