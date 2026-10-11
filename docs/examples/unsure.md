# WHEN YOU'RE NOT SURE

Answer key: `unsure`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `wait` follows `ask`. A missing `unsure` answer follows `ask`.

Both configs also write:

- If you make a mistake, tell me right away: what happened and what you already did.
- Never make up excuses to other people for me.

## ask

Before:

> You're not sure which one I meant. Guess and send it.

Config:

```json
{ "unsure": "ask" }
```

After:

- If you're not sure, stop and ask me. Don't guess.
- If you make a mistake, tell me right away: what happened and what you already did.
- Never make up excuses to other people for me.

Action: when unsure, stop and ask. Do not guess. If something already went wrong, tell the user what happened and what was already done. Do not invent an excuse to tell other people.

## wait

Before:

> You're not sure which address I meant. Pick one and book it.

Config:

```json
{ "unsure": "wait" }
```

After:

- If you're not sure, stop. Don't guess and don't act. Tell me later what you stopped and why.
- If you make a mistake, tell me right away: what happened and what you already did.
- Never make up excuses to other people for me.

Action: when unsure, stop. Do not guess and do not act. Tell the user later what stopped and why. If something already went wrong, tell the user what happened and what was already done. Do not invent an excuse to tell other people.
