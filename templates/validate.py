#!/usr/bin/env python3
"""Check templates/*/rules.yaml against schema.json. Stdlib only."""

import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent

EXPECTED = [
    "no-spam",
    "no-self-promo",
    "be-kind",
    "no-politics",
    "spoiler-tags",
    "no-nsfw",
    "new-member-limits",
    "no-external-links",
    "constructive-criticism",
    "off-topic-redirect",
]


def load_schema():
    return json.loads((ROOT / "schema.json").read_text(encoding="utf-8"))


def parse_rules(text, path):
    """Parse the small YAML subset these templates use."""
    if "\t" in text:
        raise ValueError(f"{path}: tabs are not allowed")
    if not text.endswith("\n"):
        raise ValueError(f"{path}: missing final newline")
    lines = text.splitlines()
    data = {}
    sections = None
    section = None
    in_rules = False
    for n, raw in enumerate(lines, 1):
        where = f"{path}:{n}"
        if raw.strip() == "":
            raise ValueError(f"{where}: blank line")
        if raw != raw.rstrip(" "):
            raise ValueError(f"{where}: trailing space")
        indent = len(raw) - len(raw.lstrip(" "))
        line = raw.strip()
        if indent == 0 and line == "sections:":
            if sections is not None:
                raise ValueError(f"{where}: duplicate sections")
            sections = []
            data["sections"] = sections
            section = None
            in_rules = False
        elif indent == 0 and ":" in line:
            key, val = line.split(":", 1)
            val = val.strip()
            if not val:
                raise ValueError(f"{where}: {key} needs a value")
            if key in data:
                raise ValueError(f"{where}: duplicate {key}")
            data[key] = val
            section = None
            in_rules = False
        elif indent == 2 and line.startswith("- title: "):
            if sections is None:
                raise ValueError(f"{where}: section before sections")
            title = line[len("- title: "):].strip()
            if not title:
                raise ValueError(f"{where}: empty section title")
            section = {"title": title, "rules": []}
            sections.append(section)
            in_rules = False
        elif indent == 4 and line == "rules:" and section is not None:
            in_rules = True
        elif indent == 6 and line.startswith("- ") and in_rules:
            rule = line[2:]
            if rule == "" or rule.startswith(" "):
                raise ValueError(f"{where}: empty rule")
            section["rules"].append(rule)
        else:
            raise ValueError(f"{where}: unexpected line")
    if sections is None:
        raise ValueError(f"{path}: missing sections")
    return data


def check_string(value, spec, where, errors):
    if not isinstance(value, str):
        errors.append(f"{where}: expected a string")
        return
    min_len = spec.get("minLength")
    max_len = spec.get("maxLength")
    if min_len is not None and len(value) < min_len:
        errors.append(f"{where}: shorter than {min_len}")
    if max_len is not None and len(value) > max_len:
        errors.append(f"{where}: longer than {max_len}")
    pattern = spec.get("pattern")
    if pattern and re.fullmatch(pattern, value) is None:
        errors.append(f"{where}: does not match {pattern}")


def check_doc(data, schema, path, errors):
    required = schema["required"]
    if list(data.keys()) != required:
        errors.append(f"{path}: keys must be {', '.join(required)} in that order")
    props = schema["properties"]
    for key in required:
        if key not in data:
            errors.append(f"{path}: missing {key}")
    if "id" in data:
        check_string(data["id"], props["id"], f"{path}: id", errors)
    if "title" in data:
        check_string(data["title"], props["title"], f"{path}: title", errors)
    if "strictness" in data and data["strictness"] not in props["strictness"]["enum"]:
        errors.append(f"{path}: strictness must be {' or '.join(props['strictness']['enum'])}")
    sec_spec = props["sections"]
    sections = data.get("sections")
    if not isinstance(sections, list):
        errors.append(f"{path}: sections must be a list")
        return
    if not sec_spec["minItems"] <= len(sections) <= sec_spec["maxItems"]:
        errors.append(f"{path}: need {sec_spec['minItems']} to {sec_spec['maxItems']} sections")
    item = sec_spec["items"]
    rule_spec = item["properties"]["rules"]
    for i, sec in enumerate(sections):
        where = f"{path}: section {i + 1}"
        if list(sec.keys()) != item["required"]:
            errors.append(f"{where}: keys must be title, rules")
            continue
        check_string(sec["title"], item["properties"]["title"], f"{where} title", errors)
        rules = sec["rules"]
        if not rule_spec["minItems"] <= len(rules) <= rule_spec["maxItems"]:
            errors.append(f"{where}: need {rule_spec['minItems']} to {rule_spec['maxItems']} rules")
        for j, rule in enumerate(rules):
            check_string(rule, rule_spec["items"], f"{where} rule {j + 1}", errors)


def check_readme(folder, strictness, errors):
    path = folder / "README.md"
    if not path.is_file():
        errors.append(f"{path}: missing")
        return
    text = path.read_text(encoding="utf-8")
    if not text.endswith("\n") or "\n\n" in text or text.startswith("#"):
        errors.append(f"{path}: must be one paragraph")
        return
    body = text.strip()
    if "\n" in body or not 80 <= len(body) <= 400:
        errors.append(f"{path}: one paragraph, 80 to 400 characters")
    if re.search(rf"\b{re.escape(strictness)}\b", body, re.IGNORECASE) is None:
        errors.append(f"{path}: must name the strictness ({strictness})")


def main():
    schema = load_schema()
    errors = []
    found = sorted(p.name for p in ROOT.iterdir() if p.is_dir())
    if found != sorted(EXPECTED):
        errors.append(f"folders: expected {', '.join(EXPECTED)}")
    for name in EXPECTED:
        folder = ROOT / name
        rules_path = folder / "rules.yaml"
        if not rules_path.is_file():
            errors.append(f"{rules_path}: missing")
            continue
        try:
            data = parse_rules(rules_path.read_text(encoding="utf-8"), rules_path)
        except ValueError as exc:
            errors.append(str(exc))
            continue
        check_doc(data, schema, rules_path, errors)
        if data.get("id") != name:
            errors.append(f"{rules_path}: id must be {name}")
        if "strictness" in data:
            check_readme(folder, data["strictness"], errors)
    if errors:
        print("\n".join(errors), file=sys.stderr)
        return 1
    print(f"ok: {len(EXPECTED)} templates")
    return 0


if __name__ == "__main__":
    sys.exit(main())
