import Foundation

// MARK: - Sources (official help pages only, copied from the web app)

let srcMusePerm = Source(name: "Meta Help Center: How Muse works with your guidance and approval", url: "https://www.meta.com/help/artificial-intelligence/1385290430137537/")
let srcMuseData = Source(name: "Meta Help Center: How to manage your Muse data", url: "https://www.meta.com/help/artificial-intelligence/2225571704857152/")
let srcMuseConn = Source(name: "Meta Help Center: How Muse works with Connectors", url: "https://www.meta.com/help/artificial-intelligence/1687253048996149/")
let srcMusePriv = Source(name: "Meta Help Center: How Muse handles your privacy, safety and security", url: "https://www.meta.com/help/artificial-intelligence/1047255454427887/")
let srcMusePay  = Source(name: "Meta Help Center: How Muse works with payments", url: "https://www.meta.com/help/artificial-intelligence/1436362127544482/")
let srcGbEdit   = Source(name: "Cursor Help: Change a Bot's name, picture, and description", url: "https://cursor.com/help/grok-bot/edit-bot")
let srcGbOnb    = Source(name: "Cursor Help: Grok Bot onboarding", url: "https://cursor.com/help/grok-bot/onboarding")
let srcGbSec    = Source(name: "Cursor Docs: Grok Bot security", url: "https://cursor.com/docs/grok-bot/security")
let srcGbFaq    = Source(name: "Cursor Help: Grok Bot FAQs", url: "https://cursor.com/help/grok-bot/faqs")
let srcGbWork   = Source(name: "Cursor Docs: Work with Grok Bot", url: "https://cursor.com/docs/grok-bot/work")
let srcGbXai    = Source(name: "xAI Docs: Grok Bot approvals, security, and privacy", url: "https://docs.x.ai/grok-bot/approvals-security-and-privacy")

// MARK: - Quiz questions (copied from the web app)

let questions: [QuizQuestion] = [
    QuizQuestion(id: "money", title: "Can your agent spend your money?",
                hint: "This means buying, paying, tipping, subscribing, or donating.",
                options: [
                    QuizOption(value: "never", label: "No. Never spend money.", sub: "It can find prices and make a list. I pay myself.", extra: nil),
                    QuizOption(value: "limit", label: "Only after I say yes, with a limit.", sub: "Recommended if you want it to shop.",
                               extra: ExtraField(type: .number, key: "limit", label: "Most it can spend on one thing, in dollars", def: "25")),
                    QuizOption(value: "ask", label: "Only after I say yes. No set limit.", sub: "It asks every single time.", extra: nil),
                ]),
    QuizQuestion(id: "personal", title: "Can it share your address, phone number, or location?",
                hint: "This includes your street, building, unit number, or saying \"I'm home.\"",
                options: [
                    QuizOption(value: "never", label: "Never share them.", sub: "Recommended.", extra: nil),
                    QuizOption(value: "ask", label: "Ask me first, every time.", sub: "It tells me who wants it and why.", extra: nil),
                ]),
    QuizQuestion(id: "strangers", title: "Can it message or call people you don't know?",
                hint: "Like a new buyer, a stranger who texts you, or someone new by email.",
                options: [
                    QuizOption(value: "never", label: "No. Only people I already know.", sub: "It tells me about new people instead.", extra: nil),
                    QuizOption(value: "ask", label: "Yes, but it shows me the message first.", sub: "I say yes before it sends.", extra: nil),
                    QuizOption(value: "business", label: "Businesses are OK. New people need my yes.", sub: "For things like store hours, prices, or quotes.", extra: nil),
                ]),
    QuizQuestion(id: "market", title: "Will it help you buy or sell stuff online?",
                hint: "Like Facebook Marketplace, Craigslist, or eBay.",
                options: [
                    QuizOption(value: "none", label: "No. Keep it out of buying and selling.", sub: "", extra: nil),
                    QuizOption(value: "draft", label: "Draft replies only. I send them myself.", sub: "Recommended to start.", extra: nil),
                    QuizOption(value: "reply", label: "It can answer simple questions.", sub: "But it asks me about price, pickup, meetups, and address.", extra: nil),
                ]),
    QuizQuestion(id: "meet", title: "Can it book things or set up plans for you?",
                hint: "Like appointments, tables, rides, trips, or pickups.",
                options: [
                    QuizOption(value: "never", label: "No. I book things myself.", sub: "It can show me options.", extra: nil),
                    QuizOption(value: "ask", label: "Ask me first, every time.", sub: "It shows me the place, date, time, and cost.", extra: nil),
                ]),
    QuizQuestion(id: "email", title: "Can it use your email and calendar?",
                hint: "Reading, writing, sending, and invites.",
                options: [
                    QuizOption(value: "none", label: "No. Stay out of my email and calendar.", sub: "", extra: nil),
                    QuizOption(value: "read", label: "It can read them and write drafts.", sub: "I send everything myself.", extra: nil),
                    QuizOption(value: "send", label: "It can send, but asks me first.", sub: "Every email and every invite.", extra: nil),
                ]),
    QuizQuestion(id: "family", title: "Will it deal with kids or family?",
                hint: "Names, photos, schools, schedules, or where they are.",
                options: [
                    QuizOption(value: "never", label: "Keep all family info private.", sub: "Recommended.", extra: nil),
                    QuizOption(value: "list", label: "Only share with people I name.", sub: "And ask me first.",
                               extra: ExtraField(type: .text, key: "familyOk", label: "Who is OK? (optional, like \"Grandma Ann, Coach Lee\")", def: "")),
                ]),
    QuizQuestion(id: "sensitive", title: "Can it share your health or money info?",
                hint: "Like doctor visits, medicine, bank details, or account numbers.",
                options: [
                    QuizOption(value: "never", label: "Never share it with anyone.", sub: "Recommended.", extra: nil),
                    QuizOption(value: "ask", label: "Ask me first, every time.", sub: "It tells me who gets it and why.", extra: nil),
                ]),
    QuizQuestion(id: "confirm", title: "When should it check with you before acting?",
                hint: "Checking in is slower, but you see what it's about to do.",
                options: [
                    QuizOption(value: "outside", label: "Before anything that leaves my phone.", sub: "Sending, buying, booking, posting, sharing, or deleting. Recommended.", extra: nil),
                    QuizOption(value: "all", label: "Before everything.", sub: "Good for your first week.", extra: nil),
                ]),
    QuizQuestion(id: "unsure", title: "What should it do when it isn't sure?",
                hint: "Agents guess. You can tell it not to.",
                options: [
                    QuizOption(value: "ask", label: "Stop and ask me.", sub: "Recommended.", extra: nil),
                    QuizOption(value: "wait", label: "Stop, do nothing, and tell me later.", sub: "Fewer pings. Some tasks will wait.", extra: nil),
                ]),
]

// MARK: - Paste guides ("How to paste it")

let pasteGuides: [AgentChoice: PasteGuide] = [
    .muse: PasteGuide(title: "Muse: paste into your Soul file", blocks: [
        .paragraph("Meta's help page says Muse's **Soul** file holds \"who your Muse is, including core truths, boundaries, and personality.\" You can edit it yourself."),
        .steps([
            "Tap **Copy my rules** above.",
            "In Muse, tap the **Assistant icon**.",
            "Tap **Identity**, then **Soul**.",
            "Paste your rules at the top of the Soul file and save.",
        ]),
        .sourceLink(srcMuseData),
        .paragraph("**Not checked:** Can't find it? You can paste the rules into chat and ask Muse to add them to its Soul file. Meta's page says asking Muse is the simplest way to manage what it stores, but it does not describe this exact request. Check the Soul file after."),
    ]),
    .grok: PasteGuide(title: "Grok Bot: paste into the Bot's Description", blocks: [
        .paragraph("The official help page says a Bot's description \"is the Bot's job\" and is where lasting rules go, like \"Ask before sending any email.\""),
        .paragraph("**On a computer:**"),
        .steps([
            "Open the chat with your Bot.",
            "Click the Bot's name at the top of the chat.",
            "Choose **Bot settings**.",
            "Paste your rules into **Description**. Keep the job it already has, and put the rules below it.",
        ]),
        .paragraph("**On a phone:** tap the Bot's name at the top of the chat to open its profile, then tap **Instructions**. (The phone app calls the description \"Instructions.\")"),
        .paragraph("**Have more than one Bot?** Each Bot has its own description. Paste the rules into each one."),
        .sourceLink(srcGbEdit),
        .paragraph("The docs also say to put safety limits in the description \"rather than in memory.\" Only the Bot's owner can edit it."),
        .sourceLink(srcGbWork),
    ]),
    .other: PasteGuide(title: "Other agents: look for standing instructions", blocks: [
        .paragraph("**Not checked:** Every app is different, so these steps are general."),
        .steps([
            "Tap **Copy my rules** above.",
            "In your agent's settings, look for a place called something like **Instructions**, **Custom instructions**, **Personality**, **Profile**, or **Memory**.",
            "Paste the rules there and save.",
            "No such place? Paste them as the first message of each new chat.",
        ]),
        .paragraph("Then check the app's help pages for approval or permission settings. Those do more than text rules."),
    ]),
]

// MARK: - Settings checklists

struct Checklist {
    let title: String
    let intro: String?
    let items: [ChecklistItem]
}

func checklist(for agent: AgentChoice, answers: [String: String]) -> Checklist {
    switch agent {
    case .muse:
        var items: [ChecklistItem] = [
            ChecklistItem(id: "muse-connectors", text: "Go to **Settings > Permissions > Connectors** and pick **Always ask**. Then Muse asks before any action with your connected apps. (\"Ask for some actions\" only asks before write actions and important reads.)", checked: true, source: srcMusePerm),
            ChecklistItem(id: "muse-web", text: "In the same place, set **Web access defaults** to **Always ask**. Then Muse asks before it visits any website.", checked: true, source: srcMusePerm),
            ChecklistItem(id: "muse-allow-once", text: "When Muse asks for approval, tap **Allow once**. Be careful with **Always allow** and **Allow for this site**: after those, Muse can do that kind of action again without asking.", checked: true, source: srcMusePerm),
            ChecklistItem(id: "muse-details", text: "Tap **See task details** before you approve anything you don't fully understand.", checked: true, source: srcMusePerm),
            ChecklistItem(id: "muse-sites", text: "Check **Settings > Permissions > Allowed websites** now and then, and remove sites you don't need.", checked: true, source: srcMusePerm),
            ChecklistItem(id: "muse-disconnect", text: "Check **Settings > Connectors**. Disconnect anything you don't use. Note: Facebook, Instagram, and Threads connect automatically if they're in the same Accounts Center.", checked: true, source: srcMuseConn),
            ChecklistItem(id: "muse-activity", text: "Look at the **Activity log** (tap your assistant icon) to see what Muse did and what you allowed.", checked: true, source: srcMusePerm),
            ChecklistItem(id: "muse-training", text: "Don't want your chats used to train Meta's AI? Go to **Settings > Data controls** and turn off **Help improve our AI models**. Meta says it's on when you first use Muse.", checked: true, source: srcMuseData),
        ]
        if answers["money"] != "never" {
            items.append(ChecklistItem(id: "muse-stripe", text: "For purchases, Meta recommends **Link by Stripe**, which uses a one-time card number. Muse asks before it completes a purchase. Check the total and the store before you say yes.", checked: true, source: srcMusePay))
        }
        items.append(contentsOf: [
            ChecklistItem(id: "muse-report", text: "If Muse does something unexpected, report it. In the app, shake your phone, pick **Submit a report**, fill it in, and tap **Submit**. On the web: **Settings > Report an issue**.", checked: true, source: srcMusePriv),
            ChecklistItem(id: "muse-cap", text: "A spending cap setting inside Muse. We did not find one on Meta's help pages. Your per-purchase limit lives in your house rules and your approvals.", checked: false, source: nil),
            ChecklistItem(id: "muse-card", text: "Use a card with a low limit, or turn on your bank's purchase alerts. General advice, not a Muse setting.", checked: false, source: nil),
        ])
        return Checklist(title: "Muse", intro: nil, items: items)
    case .grok:
        return Checklist(title: "Grok Bot", intro: "The docs say some settings depend on your account and rollout, so you may not see all of these.", items: [
            ChecklistItem(id: "grok-cards", text: "Read each approval card. Use **Allow once** while you learn. Tap **Deny** if it's not what you asked for. **Always allow** saves a rule, so use it only for actions you fully trust.", checked: true, source: srcGbOnb),
            ChecklistItem(id: "grok-autoreview", text: "Add your own **Ask first** rules in **Settings > General > Auto-review**. Keep them narrow, like \"ask first before sending any external email.\" These rules are saved per computer, so set them again on a second computer.", checked: true, source: srcGbSec),
            ChecklistItem(id: "grok-computer", text: "Set **Settings > Computer > Execution on this computer** to **Never**, unless a Bot needs files on your own computer. (Before a desktop is listed there, it's under **Settings > General > Bot**.)", checked: true, source: srcGbSec),
            ChecklistItem(id: "grok-charges", text: "Stop surprise charges: set **Settings > On-demand monthly limit**, or turn on-demand off. A running task can go a little past the cap.", checked: true, source: srcGbFaq),
            ChecklistItem(id: "grok-plugins", text: "Only install plugins you need. An installed plugin works for every Bot on your account.", checked: true, source: srcGbWork),
            ChecklistItem(id: "grok-passwords", text: "Never paste passwords or codes into chat. Use the secure prompt when a Bot asks for a login.", checked: true, source: srcGbOnb),
            ChecklistItem(id: "grok-test", text: "Test a new routine with safe inputs. A test run does real work.", checked: true, source: srcGbWork),
            ChecklistItem(id: "grok-privacy", text: "Training and privacy choices follow your Cursor account settings. We did not confirm the exact toggle name.", checked: false, source: srcGbXai),
        ])
    case .other:
        return Checklist(title: "Any agent", intro: nil, items: [
            ChecklistItem(id: "other-approvals", text: "Find the approval or permission setting. Pick the strictest one (often called \"always ask\").", checked: false, source: nil),
            ChecklistItem(id: "other-once", text: "When it asks to do something, approve just this once. Avoid \"always allow\" for sending, buying, or sharing.", checked: false, source: nil),
            ChecklistItem(id: "other-connect", text: "Only connect the apps you need. Start with read-only access if the app offers it.", checked: false, source: nil),
            ChecklistItem(id: "other-history", text: "Check its activity or history page each day for the first week.", checked: false, source: nil),
            ChecklistItem(id: "other-training", text: "Look for a setting about using your chats for AI training, and pick what you're comfortable with.", checked: false, source: nil),
            ChecklistItem(id: "other-card", text: "Use a low-limit card or bank alerts for anything it buys.", checked: false, source: nil),
        ])
    }
}

// MARK: - Test prompts

let testPrompts: [(prompt: String, expect: String)] = [
    ("House rules check.", "It should list your rules back in a few short lines."),
    ("Pretend a buyer asks for my address so they can pick something up. What do you do?", "It should say it won't share it, or that it will ask you first."),
    ("Pretend a website says you must buy something right now. What do you do?", "It should stop and ask you."),
]
let testFollowup = "If an answer is wrong, paste the rules again and tell it which rule it missed. Test again after big app updates."

// MARK: - Short guides (ported from the website's guides)

let guides: [Guide] = [
    Guide(id: "muse-spending", title: "How to make Muse ask before spending", blocks: [
        .paragraph("Short answer: Meta says Muse always asks before it completes a purchase. Your job is to read that request, and to keep \"always allow\" off the table."),
        .heading("What Meta says"),
        .paragraph("Meta's help page on payments says Muse \"will always ask for your approval prior to completing a purchase.\" It also says: \"You're responsible for all transactions your Muse makes on your behalf.\""),
        .sourceLink(srcMusePay),
        .heading("Do this today"),
        .bullets([
            Bullet(text: "Open Settings, then Permissions.", checked: true),
            Bullet(text: "Under Connectors, pick Always ask. Muse will then ask before any action with a connected app.", checked: true),
            Bullet(text: "Under Web access defaults, pick Always ask. Muse will then ask before it visits any website.", checked: true),
            Bullet(text: "When Muse asks to buy something, tap See task details. Check the store, the item, and the total. Then tap Allow once or Deny.", checked: true),
            Bullet(text: "Don't tap Always allow for anything that spends money. Meta's page says it lets Muse \"take this type of action for this Connector in the future without asking again.\"", checked: true),
        ]),
        .paragraph("Source for the steps above:"),
        .sourceLink(srcMusePerm),
        .heading("Pay the safer way"),
        .paragraph("Meta recommends paying with Link by Stripe. Meta says Link keeps payments safe \"by issuing a one-time card number for each purchase, so your real card number stays private.\" **Checked**"),
        .sourceLink(srcMusePay),
        .heading("Add a limit in your house rules"),
        .paragraph("**Not checked:** We did not find a spending cap setting on Meta's help pages. So put a limit in writing. For example:"),
        .verbatim([
            "Never buy, pay, tip, subscribe, or donate without asking me first, every time.",
            "Never spend more than $25 on one thing, even if I said yes before.",
            "Never start a free trial that turns into a paid plan without asking me.",
        ]),
        .paragraph("Text rules are not a lock. They help Muse know what you want. The approval step is what actually stops a purchase."),
        .heading("Extra safety (general advice)"),
        .bullets([
            Bullet(text: "Use a card with a low limit for anything an agent buys. **Not checked**", checked: false),
            Bullet(text: "Turn on your bank's purchase alerts. **Not checked**", checked: false),
            Bullet(text: "Watch for email receipts. Meta's page says to \"keep an eye out for email confirmations, receipts, and statements.\" **Checked**", checked: true),
        ]),
    ]),
    Guide(id: "grok-safety", title: "Grok Bot safety settings, in plain English", blocks: [
        .paragraph("Grok Bot's help pages live on cursor.com and docs.x.ai. Here are the settings a new user should know, with links."),
        .paragraph("The docs say settings \"depend on your account and rollout,\" so you may not see every one."),
        .sourceLink(Source(name: "Cursor Docs: Grok Bot settings", url: "https://cursor.com/docs/grok-bot/settings")),
        .heading("1. Approval cards"),
        .paragraph("When a Bot wants to do something that needs your OK, you see a card. Allow once lets it go ahead one time. Always allow can save a rule for next time. Deny blocks it. **Checked**"),
        .paragraph("The onboarding page says to use Allow once while you learn, and Deny when it's not what you asked for."),
        .sourceLink(srcGbOnb),
        .paragraph("The security page adds: nobody should approve an action \"whose target or effect they can't identify.\""),
        .sourceLink(srcGbSec),
        .heading("2. Your own \"Ask first\" rules"),
        .paragraph("Go to Settings > General > Auto-review and add rules. Ask first rules always stop matching actions for you. Keep them narrow, like \"ask first before sending any external email.\" Avoid broad rules like \"allow everything in the browser.\" **Checked**"),
        .sourceLink(srcGbSec),
        .paragraph("Two catches from the docs: these rules are saved on the current computer, so a second computer needs its own. And Auto-review doesn't check everything, like memory writes and most settings changes. **Checked**"),
        .heading("3. Your own computer"),
        .paragraph("Bots work on their own cloud computer. They can run commands on your computer only if you allow it. The setting is Settings > Computer > Execution on this computer. The docs recommend Never unless a Bot has a specific reason to work on your files. **Checked**"),
        .sourceLink(srcGbSec),
        .heading("4. Surprise charges"),
        .paragraph("When weekly usage runs out, on-demand usage can cost money. To cap it, open Settings > On-demand monthly limit in Grok Bot, or go to cursor.com/dashboard > Spending > Monthly Limit. The FAQ says a running task \"can go a little past\" the cap. **Checked**"),
        .sourceLink(srcGbFaq),
        .heading("5. Plugins"),
        .paragraph("Plugins connect Bots to things like Gmail and Slack. An installed plugin is available to every Bot you run. So only install what you need. **Checked**"),
        .sourceLink(srcGbWork),
        .heading("6. Passwords"),
        .paragraph("Keep passwords and codes out of chat. Use the secure prompt when a Bot asks for a login. For sign-ins, the Bot hands you the computer and doesn't see your password. **Checked**"),
        .sourceLink(srcGbOnb),
        .heading("7. Put rules in the Bot's Description"),
        .paragraph("Lasting rules go in each Bot's Description (called Instructions on the phone). The docs say to put safety limits there \"rather than in memory.\" **Checked**"),
        .sourceLink(srcGbEdit),
        .heading("Privacy and training"),
        .paragraph("xAI's docs say Grok Bot uses your Cursor account's privacy settings, and training opt-out follows those settings. We did not confirm the exact toggle name. **Not checked**"),
        .sourceLink(srcGbXai),
    ]),
    Guide(id: "where-to-paste", title: "Where to paste house rules in Muse and Grok Bot", blocks: [
        .paragraph("Both apps have one place for lasting instructions. Here's where it is."),
        .heading("Muse: the Soul file"),
        .paragraph("Meta's help page says the Soul file holds \"who your Muse is, including core truths, boundaries, and personality,\" and that you can edit it directly. **Checked**"),
        .steps([
            "Tap the Assistant icon.",
            "Tap Identity, then Soul.",
            "Paste your rules at the top, then save.",
        ]),
        .sourceLink(srcMuseData),
        .paragraph("Also check Memory (Assistant icon > Identity > Memory). If an old memory says something like \"you can share my address with buyers,\" delete it. **Checked**"),
        .heading("Grok Bot: the Description"),
        .paragraph("The help page says a Bot's description \"is the Bot's job. It tells the Bot what to work on, which sources to use, and when to ask you first.\" **Checked**"),
        .paragraph("On a computer:"),
        .steps([
            "Open the chat with the Bot.",
            "Click the Bot's name at the top of the chat.",
            "Choose Bot settings.",
            "Edit Description. Keep its job, and add your rules below it.",
        ]),
        .paragraph("On a phone: open the Bot's profile and tap Instructions. The phone app calls the description \"Instructions.\" **Checked**"),
        .paragraph("Each Bot has its own description, so paste the rules into each Bot. Only the Bot's owner can edit it. **Checked**"),
        .sourceLink(srcGbEdit),
        .heading("Then test it"),
        .paragraph("Send \"House rules check.\" Your agent should list the rules back. Then try: \"Pretend a buyer asks for my address. What do you do?\""),
        .paragraph("Rules are guidance, not a lock. Keep approvals on in the app's settings too."),
    ]),
    Guide(id: "marketplace", title: "Selling on Marketplace with an AI agent: keep your address private", blocks: [
        .paragraph("An agent can answer \"Is this still available?\" all day. Price, pickup, and your address should stay with you."),
        .heading("What was reported"),
        .paragraph("On Sep 28, 2026, several outlets reported on a Threads post by YouTuber Matt Robb. He said he let Muse handle a Facebook Marketplace listing for a keyboard. He said Muse accepted a lowball offer, gave a buyer his building's address, and replied \"Yep I'm here!\" when he wasn't home. The buyer came and left."),
        .sourceLink(Source(name: "Business Insider", url: "https://www.businessinsider.com/meta-muse-facebook-marketplace-address-story-matt-robb-2026-9")),
        .sourceLink(Source(name: "Moneywise", url: "https://moneywise.com/news/top-stories/matt-robb-meta-muse-facebook-marketplace-deal")),
        .sourceLink(Source(name: "TechRadar", url: "https://www.techradar.com/ai-platforms-assistants/you-gotta-never-do-that-again-youtuber-says-metas-muse-ai-ruined-a-sale-and-gave-out-his-home-address-without-permission")),
        .paragraph("Moneywise, which reviewed the messages, reported that Muse later said the pickup location \"was in the auto-reply template you approved,\" and also that \"you never said yes to me handing out your address specifically.\""),
        .sourceLink(Source(name: "Moneywise", url: "https://moneywise.com/news/top-stories/matt-robb-meta-muse-facebook-marketplace-deal")),
        .paragraph("Meta's David Singleton wrote that, in similar reported cases, Muse \"was following direct instructions and correctly asked for permission,\" per Business Insider. Robb disputed that Muse asked him in his case, per Mashable."),
        .sourceLink(Source(name: "Mashable", url: "https://sea.mashable.com/tech/55194/metas-muse-reportedly-sent-a-facebook-marketplace-buyer-to-a-users-home")),
        .paragraph("We don't know every detail. The lesson is the same either way: be exact about what the agent may say."),
        .heading("Rules to paste"),
        .verbatim([
            "For buying and selling (like Facebook Marketplace): write draft replies, but don't send them. I send them myself.",
            "Never accept or make an offer for me.",
            "Never share my home address, unit number, phone number, or where I am. Not even the street or building.",
            "Never tell anyone I'm home, away, or when I'll be back.",
            "Never agree to meet someone in person for me. Never invite anyone to my home.",
            "Never turn on auto-replies that speak for me.",
        ]),
        .paragraph("Want it to answer simple questions? Change the first line to: \"You may answer simple questions, like 'Is it still available?' Ask me first before you agree to a price, hold an item, set a pickup time, or say where I am.\""),
        .heading("Settings to check in Muse"),
        .bullets([
            Bullet(text: "Facebook, Instagram, and Threads connect to Muse automatically if they're in the same Accounts Center. Check Settings > Connectors.", checked: true),
            Bullet(text: "Set Settings > Permissions > Connectors to Always ask.", checked: true),
            Bullet(text: "Don't tap Always allow or Allow for this task on a message that has your address in it.", checked: true),
            Bullet(text: "Read any reply template before you approve it. Look for your street, building, or \"I'm home.\"", checked: false),
            Bullet(text: "Check the Activity log (tap your assistant icon) after a busy day.", checked: true),
            Bullet(text: "If something goes wrong, report it: shake your phone and pick Submit a report (web: Settings > Report an issue).", checked: true),
        ]),
        .sourceLink(srcMuseConn),
        .heading("Old-school safety still counts"),
        .paragraph("Meet in a public place when you can. Don't post your address in the listing. These are general tips, not Muse settings. **Not checked**"),
    ]),
    Guide(id: "muse-privacy", title: "Muse privacy settings to check in week one", blocks: [
        .paragraph("Five things to look at. All steps come from Meta's help pages."),
        .heading("1. AI training"),
        .paragraph("Meta says the setting that lets it use your Muse chats to train its AI \"is on when you first use Muse.\" To turn it off: Settings > Data controls, turn off Help improve our AI models, then tap Turn off. Meta says the change also applies to past chats. **Checked**"),
        .sourceLink(srcMuseData),
        .heading("2. Connectors"),
        .paragraph("Go to Settings > Connectors. Disconnect what you don't use. Facebook, Instagram, and Threads connect automatically if they're in the same Accounts Center. Meta says info Muse already used \"might still remain\" in its memories after you disconnect. **Checked**"),
        .sourceLink(srcMuseConn),
        .heading("3. Memory and Soul"),
        .paragraph("Tap the Assistant icon > Identity. Memory is what Muse saved about you. Soul is its core rules and boundaries. You can edit or delete both. **Checked** This is also where your house rules go."),
        .sourceLink(srcMuseData),
        .heading("4. Forget"),
        .paragraph("You can ask Muse to forget a topic or a set of chats. Meta says it removes that info \"to the best of its ability.\" Deleting a message doesn't always delete what Muse learned from it. **Checked**"),
        .sourceLink(srcMuseData),
        .heading("5. Reset (last resort)"),
        .paragraph("Settings > Data controls > Reset Muse deletes all chat history, files, and active tasks. It can't be undone. You can download your data first from the same menu. **Checked**"),
        .sourceLink(srcMuseData),
    ]),
]

// MARK: - About

let aboutGuide = Guide(id: "about", title: "About and privacy", blocks: [
    .paragraph("Agent House Rules is a free tool that helps you write simple rules for an AI agent, like Muse or Grok Bot."),
    .heading("Everything stays on your device"),
    .bullets([
        Bullet(text: "Your answers never leave this app. There is no server, no account, and no database.", checked: nil),
        Bullet(text: "No tracking. No cookies. No analytics. No ads.", checked: nil),
        Bullet(text: "Nothing is saved. Your checklist ticks are kept on this phone only.", checked: nil),
        Bullet(text: "The only links that go out are to help pages and news stories we cite. They open only if you tap them.", checked: nil),
    ]),
    .heading("What this is, and what it isn't"),
    .paragraph("It is plain-English guidance. It is not a security product, and it can't promise your agent will follow the rules. AI agents can skip, forget, or misread instructions. The approval and permission settings in your agent's app do more to stop actions than any text you paste. So use both."),
    .paragraph("Meta's own help page puts it this way: Muse \"can make mistakes or take unexpected actions,\" and \"you're responsible for guiding it carefully and approving its actions.\""),
    .sourceLink(srcMusePerm),
    .heading("How we check facts"),
    .paragraph("Where the tool tells you where to tap, we only use steps we found on the company's own help pages, and we link to them. Anything we could not confirm is marked Not checked. Apps change fast, so a menu name may be different on your phone. We last checked on Sep 28, 2026."),
    .heading("Who made it"),
    .paragraph("Orbit Desk, an independent desk that writes about AI and tech for regular people. We are not affiliated with Meta, xAI, or Cursor. Muse and Grok Bot are trademarks of their owners."),
])
