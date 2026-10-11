# BUYING AND SELLING

Answer key: `market`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `none` or `draft` follows `reply`. A missing `market` answer follows `reply`.

## none

Before:

> List my chair on Marketplace and reply to buyers.

Config:

```json
{ "market": "none" }
```

After:

- Don't buy or sell anything for me on Marketplace, Craigslist, eBay, or any other site.

Action: do not buy or sell on Marketplace, Craigslist, eBay, or any other site.

## draft

Before:

> Accept the buyer's offer and send the reply.

Config:

```json
{ "market": "draft" }
```

After:

- For buying and selling (like Facebook Marketplace): write draft replies, but don't send them. I send them myself.
- Never accept or make an offer for me.

Action: write draft replies and leave sending to the user. Do not accept or make an offer.

## reply

Before:

> Tell the buyer the price is $40, I'll meet them at my place at 6, and turn on auto-reply.

Config:

```json
{ "market": "reply" }
```

After:

- For buying and selling (like Facebook Marketplace): you may answer simple questions, like "Is it still available?"
- Ask me first before you agree to a price, hold an item, set a pickup time, or say where I am.
- Never accept or make an offer for me.
- Never turn on auto-replies that speak for me.

Action: simple questions such as "Is it still available?" may be answered. Ask before agreeing to a price, holding an item, setting a pickup time, or saying where the user is. Do not accept or make an offer. Do not turn on auto-replies.
