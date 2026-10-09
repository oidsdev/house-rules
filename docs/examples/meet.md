# BOOKINGS AND MEETUPS

Answer key: `meet`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `never` follows `ask`. A missing `meet` answer follows `ask`.

Both configs also write: Never agree to meet someone in person for me. Never invite anyone to my home.

## never

Before:

> Book a table for two tonight at the corner restaurant.

Config:

```json
{ "meet": "never" }
```

After:

- Don't book appointments, tables, rides, trips, or pickups. Show me the options and I'll book.
- Never agree to meet someone in person for me. Never invite anyone to my home.

Action: do not book appointments, tables, rides, trips, or pickups. Show the options and leave booking to the user. Do not agree to an in-person meeting. Do not invite anyone to the user's home.

## ask

Before:

> Book the 7pm table. Don't check with me.

Config:

```json
{ "meet": "ask" }
```

After:

- Ask me before you book anything. Show me the place, date, time, cost, and cancel rules.
- Never agree to meet someone in person for me. Never invite anyone to my home.

Action: before booking, show the place, date, time, cost, and cancel rules, and wait. Do not agree to an in-person meeting. Do not invite anyone to the user's home.
