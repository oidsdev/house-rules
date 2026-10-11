# MY ADDRESS, PHONE, AND LOCATION

Answer key: `personal`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `never` follows `ask`. A missing `personal` answer follows `ask`.

Both configs also write: Never tell anyone I'm home, away, or when I'll be back, unless I tell you to say it.

## never

Before:

> Tell the buyer my street address so they can pick it up.

Config:

```json
{ "personal": "never" }
```

After:

- Never share my home address, unit number, phone number, or where I am. Not even the street or building.
- Never tell anyone I'm home, away, or when I'll be back, unless I tell you to say it.

Action: do not share the home address, unit, phone number, street, building, or current location. Do not say whether the user is home, away, or when they will be back, unless the user tells the agent to say that.

## ask

Before:

> Send my phone number to the shop.

Config:

```json
{ "personal": "ask" }
```

After:

- Ask me before you share my address, phone number, or location. Tell me who wants it and why. Wait for my yes.
- Never tell anyone I'm home, away, or when I'll be back, unless I tell you to say it.

Action: before sharing an address, phone number, or location, say who wants it and why, and wait for a yes. Do not say whether the user is home, away, or when they will be back, unless the user tells the agent to say that.
