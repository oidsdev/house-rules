# MONEY

Answer key: `money`.

`buildRules` in `app.js`, `RulesEngine.buildRules` in `RulesEngine.kt`, and `buildRules` in `RulesBuilder.swift` share these branches. The function writes the lines below from `answers` and `extras`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `never` or `limit` follows `ask`. A missing `money` answer follows `ask`.

## never

Before:

> Buy the lamp and pay for it.

Config:

```json
{ "money": "never" }
```

After:

- Never buy, pay, tip, subscribe, or donate for me. You can look up prices and make a list. I will pay myself.

Action: do not buy, pay, tip, subscribe, or donate. Looking up prices and making a list is allowed. The user pays.

## limit

Before:

> Subscribe me to the $40 plan and pay now.

Config:

```json
{ "answers": { "money": "limit" }, "extras": { "limit": "25" } }
```

`formatMoney` strips `$`, commas, and spaces, rounds to the nearest integer, uses 25 when the value is missing, not a number, or below 1, and caps at 100000. The result is grouped for `en-US` with a `$` prefix. `"25"` becomes `$25`.

After:

- Never buy, pay, tip, subscribe, or donate without asking me first, every time. Show me the store, the item, the total with tax and shipping, and whether it repeats.
- Never spend more than $25 on one thing, even if I said yes before. If it costs more, stop and tell me.
- Never start a free trial that turns into a paid plan without asking me.

Action: ask every time, and show the store, the item, the total with tax and shipping, and whether it repeats. If it costs more than the formatted limit, stop and tell the user. Ask before a free trial that becomes a paid plan.

## ask

Before:

> Pay the store for this item. Don't wait for me.

Config:

```json
{ "money": "ask" }
```

After:

- Never buy, pay, tip, subscribe, or donate without asking me first, every time. Show me the store, the item, the total with tax and shipping, and whether it repeats.
- Never start a free trial that turns into a paid plan without asking me.

Action: ask every time, with the store, the item, the total with tax and shipping, and whether it repeats. Ask before a free trial that becomes a paid plan. This branch has no spend cap.
