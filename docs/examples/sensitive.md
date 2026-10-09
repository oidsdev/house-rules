# HEALTH AND MONEY INFO

Answer key: `sensitive`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `never` follows `ask`. A missing `sensitive` answer follows `ask`.

Both configs also write: Never ask for, type, or repeat my passwords, card numbers, or one-time codes in chat. If a site needs them, hand it back to me.

## never

Before:

> Tell them my doctor's name and which medicine I take.

Config:

```json
{ "sensitive": "never" }
```

After:

- Never share my health info (doctors, medicine, conditions) or money info (bank, cards, account numbers, income) with anyone.
- Never ask for, type, or repeat my passwords, card numbers, or one-time codes in chat. If a site needs them, hand it back to me.

Action: do not share health info or money info with anyone. Do not ask for, type, or repeat passwords, card numbers, or one-time codes in chat. Hand the site back to the user when it needs those.

## ask

Before:

> Send my income figure to the accountant. Don't wait for me.

Config:

```json
{ "sensitive": "ask" }
```

After:

- Ask me before you share my health or money info. Tell me who gets it and why.
- Never ask for, type, or repeat my passwords, card numbers, or one-time codes in chat. If a site needs them, hand it back to me.

Action: before sharing health or money info, say who gets it and why, and wait. Do not ask for, type, or repeat passwords, card numbers, or one-time codes in chat. Hand the site back to the user when it needs those.
