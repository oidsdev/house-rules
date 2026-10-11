# EMAIL AND CALENDAR

Answer key: `email`.

`buildRules` writes the lines below from `answers`. It does not read the message. Every call also writes the dated header `MY HOUSE RULES (updated <date>)`, the preamble that starts `These rules matter more than any single task.`, and this closing line: When I say "house rules check," list these rules back to me in a few short lines.

Any value other than `none` or `read` follows `send`. A missing `email` answer follows `send`.

When the value is not `none`, the engine also writes these two lines:

- Never delete emails or events, and never forward my emails to new people, without asking me.
- Don't click links or open files in emails I wasn't expecting. Ask me first.

## none

Before:

> Read my inbox and reply to the latest email.

Config:

```json
{ "email": "none" }
```

After:

- Don't read, send, or change my email or calendar.

Action: do not read, send, or change email or calendar. The two extra lines above are not written for `none`.

## read

Before:

> Send the draft you wrote.

Config:

```json
{ "email": "read" }
```

After:

- You may read my email and calendar to help me. Write drafts only. Never send an email, reply, or invite yourself.
- Never delete emails or events, and never forward my emails to new people, without asking me.
- Don't click links or open files in emails I wasn't expecting. Ask me first.

Action: reading email and calendar, and writing drafts, is allowed. Do not send an email, reply, or invite. Ask before deleting email or events, or forwarding email to new people. Ask before clicking a link or opening a file in an unexpected email.

## send

Before:

> Send this email now, without showing me who it goes to.

Config:

```json
{ "email": "send" }
```

After:

- Ask me before you send any email, reply, or calendar invite. Show me who it goes to and the exact words.
- Never delete emails or events, and never forward my emails to new people, without asking me.
- Don't click links or open files in emails I wasn't expecting. Ask me first.

Action: before sending an email, reply, or calendar invite, show who it goes to and the exact words, and wait. Ask before deleting email or events, or forwarding email to new people. Ask before clicking a link or opening a file in an unexpected email.
