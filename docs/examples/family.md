# KIDS AND FAMILY

Answer key: `family`.

`buildRules` writes the lines below from `answers` and `extras`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `never` follows `list`. A missing `family` answer follows `list` with an empty `familyOk`.

`cleanText` on `extras.familyOk` turns line breaks into spaces, collapses whitespace, trims, and keeps 120 characters.

The `list` branch also writes: Never share a child's school, schedule, or location with someone new.

## never

Before:

> Tell the volunteer my child's name and pickup time.

Config:

```json
{ "family": "never" }
```

After:

- Never share my kids' or family's names, photos, school, schedule, or location with anyone.

Action: do not share kids' or family's names, photos, school, schedule, or location with anyone.

## list, with names

Before:

> Send my child's school address to the new neighbor.

Config:

```json
{ "answers": { "family": "list" }, "extras": { "familyOk": "Grandma Ann" } }
```

After:

- Only share family info (names, photos, school, schedule, location) with these people: Grandma Ann. Ask me first, even then.
- Never share a child's school, schedule, or location with someone new.

Action: family info goes only to the cleaned name list, and only after a yes. Do not share a child's school, schedule, or location with someone new.

## list, no names

Before:

> Share my child's schedule with the class parent.

Config:

```json
{ "answers": { "family": "list" }, "extras": { "familyOk": "" } }
```

After:

- Only share family info (names, photos, school, schedule, location) with people I name. Ask me first, even then.
- Never share a child's school, schedule, or location with someone new.

Action: share family info only with people the user names, and ask first. Do not share a child's school, schedule, or location with someone new.
