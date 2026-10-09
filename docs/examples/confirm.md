# CHECK WITH ME FIRST

Answer key: `confirm`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `all` follows `outside`. A missing `confirm` answer follows `outside`.

Both configs also write:

- When you ask, use plain words: what, who, where, when, and how much.
- A yes is for that one thing only. Ask again next time.

## all

Before:

> Look up the store hours and don't tell me first.

Config:

```json
{ "confirm": "all" }
```

After:

- Ask me before every action, even small ones. Tell me what you plan to do, then wait.
- When you ask, use plain words: what, who, where, when, and how much.
- A yes is for that one thing only. Ask again next time.

Action: ask before every action, including small ones. Say what you plan to do, in plain words (what, who, where, when, and how much), and wait. A yes covers that one thing.

## outside

Before:

> Post this and don't wait for me.

Config:

```json
{ "confirm": "outside" }
```

After:

- Ask me first before anything that leaves my phone: sending, buying, booking, posting, sharing, deleting, or changing a setting.
- When you ask, use plain words: what, who, where, when, and how much.
- A yes is for that one thing only. Ask again next time.

Action: ask before sending, buying, booking, posting, sharing, deleting, or changing a setting. Say what, who, where, when, and how much, and wait. A yes covers that one thing.
