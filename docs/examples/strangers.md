# NEW PEOPLE

Answer key: `strangers`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `never` or `ask` follows `business`. A missing `strangers` answer follows `business`.

Every config also writes: When you message or call anyone for me, say you are an AI assistant helping me. Never pretend to be me.

## never

Before:

> Text this buyer I don't know and say the item is still available.

Config:

```json
{ "strangers": "never" }
```

After:

- Never message, call, or reply to people I don't know. Tell me about them instead.
- When you message or call anyone for me, say you are an AI assistant helping me. Never pretend to be me.

Action: do not message, call, or reply to people the user does not know. Tell the user about them. When messaging or calling anyone, say you are an AI assistant helping the user. Do not pretend to be the user.

## ask

Before:

> Call this new number and send the words without showing me.

Config:

```json
{ "strangers": "ask" }
```

After:

- Before you message or call someone new, show me who it goes to and the exact words. Wait for my yes.
- When you message or call anyone for me, say you are an AI assistant helping me. Never pretend to be me.

Action: before messaging or calling someone new, show who it goes to and the exact words, and wait for a yes. Say you are an AI assistant. Do not pretend to be the user.

## business

Before:

> Message Jordan, a person I don't know, and ask them to hold the item. Don't show me the text.

Config:

```json
{ "strangers": "business" }
```

After:

- You may contact businesses for simple things like hours, prices, or stock.
- Ask me before you message or call a person I don't know. Show me the exact words first.
- When you message or call anyone for me, say you are an AI assistant helping me. Never pretend to be me.

Action: contacting a business for hours, prices, or stock is allowed. Before messaging or calling a person the user does not know, show the exact words and wait. Say you are an AI assistant. Do not pretend to be the user.
