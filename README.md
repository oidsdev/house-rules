# House Rules

Answer 10 questions. Get a set of safety rules to paste into your AI agent.

**Live:** https://agent-house-rules.pages.dev

AI agents like Muse and Grok Bot can send messages, buy things, and book stuff for you. They follow instructions — so give them good ones. House Rules asks 10 plain questions (can your agent spend money? share your address? message strangers?) and hands you a short list of rules to copy and paste into your agent, plus a checklist of the app's own safety settings to change.

About 3 minutes. Free. Everything stays on your device — no sign-up, no tracking, no network calls.

Rules help, but they are not a lock. An agent can still get things wrong. That's why you also get a settings checklist and test prompts. The app's own settings do more to stop actions than any text you paste.

## What it covers

- **Quiz → rules.** 10 questions, plain-English rules generated on-device.
- **Settings checklist.** The permission switches that actually matter, with where-to-tap steps checked against each company's own help pages.
- **Test prompts.** A 1-minute test to see whether your agent respects the rules.
- **Guides.** Where to paste rules, per-agent setup (Muse, Grok Bot, others).

## Run the web version

No build step. It's static HTML/CSS/JS:

1. Clone the repo.
2. Open `index.html` in a browser — or serve the folder with `python3 -m http.server` and visit `http://localhost:8000`.
3. That's it. No dependencies, no build tools. Works offline once loaded.

## Native apps

- `ios/` — native SwiftUI app, iOS 17+. Open `HouseRules.xcodeproj` in Xcode 16+. Not yet compiled for release.
- `android/` — native Kotlin/Compose app. Debug APK builds clean; see `android/BUILD-NOTES.md`.

Pricing: web free, F-Droid free, Google Play $1 (Play forces the free/paid choice at publish — nothing is withheld from the free builds). No ads, no tracking, no in-app purchases anywhere.

## Repo layout

- `index.html`, `app.js`, `style.css` — the web tool
- `about.html` — about + privacy page
- `guides/` — short setup guides (plain HTML, work without JS)
- `ios/` — iOS app source
- `android/` — Android app source
- `android/listing.md` — store listing draft

## License

MIT — see LICENSE.

## Contact

Bug reports and corrections: https://github.com/oidsdev/house-rules/issues

Not affiliated with Meta, xAI, or Cursor.
