# Templates

Ten rule sets for common cases. Paste one into your agent under your house rules.

Each folder has `rules.yaml` and a one-paragraph note on intent and strictness. The YAML matches a section from `buildRules()` in `app.js`: a title, then short plain-English lines. `strict` means stop and ask. `standard` means follow the rule, and ask only when unsure.

Check them with `python3 templates/validate.py`.

- `no-spam` — strict
- `no-self-promo` — strict
- `be-kind` — standard
- `no-politics` — strict
- `spoiler-tags` — standard
- `no-nsfw` — strict
- `new-member-limits` — strict
- `no-external-links` — strict
- `constructive-criticism` — standard
- `off-topic-redirect` — standard
