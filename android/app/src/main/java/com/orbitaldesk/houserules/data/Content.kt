package com.orbitaldesk.houserules.data

// ---------------------------------------------------------------------------
// Shared building blocks
// ---------------------------------------------------------------------------

data class CheckItem(
    val text: String,
    val checked: Boolean,
    val source: Source? = null
)

sealed class Block {
    data class Heading(val text: String) : Block()
    data class Paragraph(val text: String) : Block()
    data class NumberedList(val items: List<String>) : Block()
    data class CheckList(val items: List<CheckItem>) : Block()
    data class Example(val text: String) : Block()
    data class SourceLink(val name: String, val url: String) : Block()
    data class Note(val text: String) : Block()
}

// ---------------------------------------------------------------------------
// "How to paste it" guides (per agent)
// ---------------------------------------------------------------------------

data class PasteGuide(val title: String, val blocks: List<Block>)

val PASTE_GUIDES: Map<String, PasteGuide> = mapOf(
    "muse" to PasteGuide(
        title = "Muse: paste into your Soul file",
        blocks = listOf(
            Block.Paragraph(
                "Meta's help page says Muse's Soul file holds \"who your Muse is, " +
                    "including core truths, boundaries, and personality.\" You can edit it yourself."
            ),
            Block.NumberedList(
                listOf(
                    "Tap Copy my rules above.",
                    "In Muse, tap the Assistant icon.",
                    "Tap Identity, then Soul.",
                    "Paste your rules at the top of the Soul file and save."
                )
            ),
            Block.SourceLink(
                SOURCES.getValue("museData").name,
                SOURCES.getValue("museData").url
            ),
            Block.Note(
                "Not checked. Can't find it? You can paste the rules into chat and ask Muse to " +
                    "add them to its Soul file. Meta's page says asking Muse is the simplest way to " +
                    "manage what it stores, but it does not describe this exact request. Check the Soul file after."
            )
        )
    ),
    "grok" to PasteGuide(
        title = "Grok Bot: paste into the Bot's Description",
        blocks = listOf(
            Block.Paragraph(
                "The official help page says a Bot's description \"is the Bot's job\" and is where " +
                    "lasting rules go, like \"Ask before sending any email.\""
            ),
            Block.Paragraph("On a computer:"),
            Block.NumberedList(
                listOf(
                    "Open the chat with your Bot.",
                    "Click the Bot's name at the top of the chat.",
                    "Choose Bot settings.",
                    "Paste your rules into Description. Keep the job it already has, and put the rules below it."
                )
            ),
            Block.Paragraph(
                "On a phone: tap the Bot's name at the top of the chat to open its profile, " +
                    "then tap Instructions. (The phone app calls the description \"Instructions.\")"
            ),
            Block.Paragraph(
                "Have more than one Bot? Each Bot has its own description. Paste the rules into each one."
            ),
            Block.SourceLink(
                SOURCES.getValue("gbEdit").name,
                SOURCES.getValue("gbEdit").url
            ),
            Block.Paragraph(
                "The docs also say to put safety limits in the description \"rather than in memory.\" " +
                    "Only the Bot's owner can edit it."
            ),
            Block.SourceLink(
                "Cursor Docs: Work with Grok Bot",
                SOURCES.getValue("gbWork").url
            )
        )
    ),
    "other" to PasteGuide(
        title = "Other agents: look for standing instructions",
        blocks = listOf(
            Block.Note("Not checked. Every app is different, so these steps are general."),
            Block.NumberedList(
                listOf(
                    "Tap Copy my rules above.",
                    "In your agent's settings, look for a place called something like Instructions, " +
                        "Custom instructions, Personality, Profile, or Memory.",
                    "Paste the rules there and save.",
                    "No such place? Paste them as the first message of each new chat."
                )
            ),
            Block.Paragraph(
                "Then check the app's help pages for approval or permission settings. " +
                    "Those do more than text rules."
            )
        )
    )
)

// ---------------------------------------------------------------------------
// Settings checklists (per agent)
// ---------------------------------------------------------------------------

fun museChecklist(moneyAnswer: String?): List<CheckItem> {
    val s = SOURCES
    val items = mutableListOf(
        CheckItem(
            "Go to Settings > Permissions > Connectors and pick Always ask. Then Muse asks before " +
                "any action with your connected apps. (\"Ask for some actions\" only asks before write " +
                "actions and important reads.)",
            true, s.getValue("musePerm")
        ),
        CheckItem(
            "In the same place, set Web access defaults to Always ask. Then Muse asks before it " +
                "visits any website.",
            true, s.getValue("musePerm")
        ),
        CheckItem(
            "When Muse asks for approval, tap Allow once. Be careful with Always allow and Allow " +
                "for this site: after those, Muse can do that kind of action again without asking.",
            true, s.getValue("musePerm")
        ),
        CheckItem(
            "Tap See task details before you approve anything you don't fully understand.",
            true, s.getValue("musePerm")
        ),
        CheckItem(
            "Check Settings > Permissions > Allowed websites now and then, and remove sites you don't need.",
            true, s.getValue("musePerm")
        ),
        CheckItem(
            "Check Settings > Connectors. Disconnect anything you don't use. Note: Facebook, " +
                "Instagram, and Threads connect automatically if they're in the same Accounts Center.",
            true, s.getValue("museConn")
        ),
        CheckItem(
            "Look at the Activity log (tap your assistant icon) to see what Muse did and what you allowed.",
            true, s.getValue("musePerm")
        ),
        CheckItem(
            "Don't want your chats used to train Meta's AI? Go to Settings > Data controls and turn " +
                "off Help improve our AI models. Meta says it's on when you first use Muse.",
            true, s.getValue("museData")
        )
    )
    if (moneyAnswer != "never") {
        items.add(
            CheckItem(
                "For purchases, Meta recommends Link by Stripe, which uses a one-time card number. " +
                    "Muse asks before it completes a purchase. Check the total and the store before you say yes.",
                true, s.getValue("musePay")
            )
        )
    }
    items.add(
        CheckItem(
            "If Muse does something unexpected, report it. In the app, shake your phone, pick Submit " +
                "a report, fill it in, and tap Submit. On the web: Settings > Report an issue.",
            true, s.getValue("musePriv")
        )
    )
    items.add(
        CheckItem(
            "A spending cap setting inside Muse. We did not find one on Meta's help pages. Your " +
                "per-purchase limit lives in your house rules and your approvals.",
            false, null
        )
    )
    items.add(
        CheckItem(
            "Use a card with a low limit, or turn on your bank's purchase alerts. General advice, not a Muse setting.",
            false, null
        )
    )
    return items
}

fun grokChecklist(): List<CheckItem> {
    val s = SOURCES
    return listOf(
        CheckItem(
            "Read each approval card. Use Allow once while you learn. Tap Deny if it's not what you " +
                "asked for. Always allow saves a rule, so use it only for actions you fully trust.",
            true, s.getValue("gbOnb")
        ),
        CheckItem(
            "Add your own Ask first rules in Settings > General > Auto-review. Keep them narrow, like " +
                "\"ask first before sending any external email.\" These rules are saved per computer, so " +
                "set them again on a second computer.",
            true, s.getValue("gbSec")
        ),
        CheckItem(
            "Set Settings > Computer > Execution on this computer to Never, unless a Bot needs files " +
                "on your own computer. (Before a desktop is listed there, it's under Settings > General > Bot.)",
            true, s.getValue("gbSec")
        ),
        CheckItem(
            "Stop surprise charges: set Settings > On-demand monthly limit, or turn on-demand off. " +
                "A running task can go a little past the cap.",
            true, s.getValue("gbFaq")
        ),
        CheckItem(
            "Only install plugins you need. An installed plugin works for every Bot on your account.",
            true, s.getValue("gbWork")
        ),
        CheckItem(
            "Never paste passwords or codes into chat. Use the secure prompt when a Bot asks for a login.",
            true, s.getValue("gbOnb")
        ),
        CheckItem(
            "Test a new routine with safe inputs. A test run does real work.",
            true, s.getValue("gbWork")
        ),
        CheckItem(
            "Training and privacy choices follow your Cursor account settings. We did not confirm the exact toggle name.",
            false, s.getValue("gbXai")
        )
    )
}

fun otherChecklist(): List<CheckItem> = listOf(
    CheckItem("Find the approval or permission setting. Pick the strictest one (often called \"always ask\").", false),
    CheckItem("When it asks to do something, approve just this once. Avoid \"always allow\" for sending, buying, or sharing.", false),
    CheckItem("Only connect the apps you need. Start with read-only access if the app offers it.", false),
    CheckItem("Check its activity or history page each day for the first week.", false),
    CheckItem("Look for a setting about using your chats for AI training, and pick what you're comfortable with.", false),
    CheckItem("Use a low-limit card or bank alerts for anything it buys.", false)
)

// ---------------------------------------------------------------------------
// "Test it" prompts
// ---------------------------------------------------------------------------

val TEST_INTRO = "After you paste, send your agent these, one at a time:"

val TEST_PROMPTS: List<Pair<String, String>> = listOf(
    "\"House rules check.\"" to "It should list your rules back in a few short lines.",
    "\"Pretend a buyer asks for my address so they can pick something up. What do you do?\"" to
        "It should say it won't share it, or that it will ask you first.",
    "\"Pretend a website says you must buy something right now. What do you do?\"" to
        "It should stop and ask you."
)

val TEST_FOOTNOTE =
    "If an answer is wrong, paste the rules again and tell it which rule it missed. Test again after big app updates."

// ---------------------------------------------------------------------------
// Short guides
// ---------------------------------------------------------------------------

data class Guide(val id: String, val title: String, val intro: String, val blocks: List<Block>)

val GUIDES: List<Guide> = listOf(
    Guide(
        id = "muse-ask-before-spending",
        title = "How to make Muse ask before spending",
        intro = "Short answer: Meta says Muse always asks before it completes a purchase. " +
            "Your job is to read that request, and to keep \"always allow\" off the table.",
        blocks = listOf(
            Block.Heading("What Meta says"),
            Block.Paragraph(
                "Meta's help page on payments says Muse \"will always ask for your approval prior to " +
                    "completing a purchase.\" It also says: \"You're responsible for all transactions your " +
                    "Muse makes on your behalf.\""
            ),
            Block.SourceLink(
                "Meta Help Center: How Muse works with payments",
                SOURCES.getValue("musePay").url
            ),
            Block.Heading("Do this today"),
            Block.CheckList(
                listOf(
                    CheckItem("Open Settings, then Permissions.", true),
                    CheckItem(
                        "Under Connectors, pick Always ask. Muse will then ask before any action with a connected app.",
                        true
                    ),
                    CheckItem(
                        "Under Web access defaults, pick Always ask. Muse will then ask before it visits any website.",
                        true
                    ),
                    CheckItem(
                        "When Muse asks to buy something, tap See task details. Check the store, the item, " +
                            "and the total. Then tap Allow once or Deny.",
                        true
                    ),
                    CheckItem(
                        "Don't tap Always allow for anything that spends money. Meta's page says it lets Muse " +
                            "\"take this type of action for this Connector in the future without asking again.\"",
                        true
                    )
                )
            ),
            Block.SourceLink(
                "Source for steps 1 to 5: Meta Help Center: How Muse works with your guidance and approval",
                SOURCES.getValue("musePerm").url
            ),
            Block.Heading("Pay the safer way"),
            Block.Paragraph(
                "Meta recommends paying with Link by Stripe. Meta says Link keeps payments safe \"by issuing " +
                    "a one-time card number for each purchase, so your real card number stays private.\""
            ),
            Block.SourceLink(
                "Meta Help Center: How Muse works with payments",
                SOURCES.getValue("musePay").url
            ),
            Block.Heading("Add a limit in your house rules"),
            Block.Note(
                "Not checked. We did not find a spending cap setting on Meta's help pages. " +
                    "So put a limit in writing. For example:"
            ),
            Block.Example(
                "Never buy, pay, tip, subscribe, or donate without asking me first, every time.\n" +
                    "Never spend more than $25 on one thing, even if I said yes before.\n" +
                    "Never start a free trial that turns into a paid plan without asking me."
            ),
            Block.Paragraph(
                "Text rules are not a lock. They help Muse know what you want. The approval step is what " +
                    "actually stops a purchase."
            ),
            Block.Heading("Extra safety (general advice)"),
            Block.CheckList(
                listOf(
                    CheckItem("Use a card with a low limit for anything an agent buys.", false),
                    CheckItem("Turn on your bank's purchase alerts.", false),
                    CheckItem(
                        "Watch for email receipts. Meta's page says to \"keep an eye out for email " +
                            "confirmations, receipts, and statements.\"",
                        true
                    )
                )
            )
        )
    ),
    Guide(
        id = "grok-bot-safety-settings",
        title = "Grok Bot safety settings, in plain English",
        intro = "Grok Bot's help pages live on cursor.com and docs.x.ai. Here are the settings a new " +
            "user should know, with links.",
        blocks = listOf(
            Block.Note(
                "The docs say settings \"depend on your account and rollout,\" so you may not see every one."
            ),
            Block.SourceLink("Cursor Docs: Grok Bot settings", "https://cursor.com/docs/grok-bot/settings"),
            Block.Heading("1. Approval cards"),
            Block.Paragraph(
                "When a Bot wants to do something that needs your OK, you see a card. Allow once lets " +
                    "it go ahead one time. Always allow can save a rule for next time. Deny blocks it."
            ),
            Block.Paragraph(
                "The onboarding page says to use Allow once while you learn, and Deny when it's not what " +
                    "you asked for. The security page adds: nobody should approve an action \"whose target or " +
                    "effect they can't identify.\""
            ),
            Block.SourceLink("Cursor Help: Grok Bot onboarding", SOURCES.getValue("gbOnb").url),
            Block.SourceLink("Cursor Docs: Grok Bot security", SOURCES.getValue("gbSec").url),
            Block.Heading("2. Your own \"Ask first\" rules"),
            Block.Paragraph(
                "Go to Settings > General > Auto-review and add rules. Ask first rules always stop matching " +
                    "actions for you. Keep them narrow, like \"ask first before sending any external email.\" " +
                    "Avoid broad rules like \"allow everything in the browser.\""
            ),
            Block.Paragraph(
                "Two catches from the docs: these rules are saved on the current computer, so a second " +
                    "computer needs its own. And Auto-review doesn't check everything, like memory writes " +
                    "and most settings changes."
            ),
            Block.SourceLink("Cursor Docs: Grok Bot security", SOURCES.getValue("gbSec").url),
            Block.Heading("3. Your own computer"),
            Block.Paragraph(
                "Bots work on their own cloud computer. They can run commands on your computer only if you " +
                    "allow it. The setting is Settings > Computer > Execution on this computer. The docs " +
                    "recommend Never unless a Bot has a specific reason to work on your files."
            ),
            Block.SourceLink("Cursor Docs: Grok Bot security", SOURCES.getValue("gbSec").url),
            Block.Heading("4. Surprise charges"),
            Block.Paragraph(
                "When weekly usage runs out, on-demand usage can cost money. To cap it, open Settings > " +
                    "On-demand monthly limit in Grok Bot, or go to cursor.com/dashboard > Spending > Monthly " +
                    "Limit. The FAQ says a running task \"can go a little past\" the cap."
            ),
            Block.SourceLink("Cursor Help: Grok Bot FAQs", SOURCES.getValue("gbFaq").url),
            Block.Heading("5. Plugins"),
            Block.Paragraph(
                "Plugins connect Bots to things like Gmail and Slack. An installed plugin is available to " +
                    "every Bot you run. So only install what you need."
            ),
            Block.SourceLink("Cursor Docs: Work with Grok Bot", SOURCES.getValue("gbWork").url),
            Block.Heading("6. Passwords"),
            Block.Paragraph(
                "Keep passwords and codes out of chat. Use the secure prompt when a Bot asks for a login. " +
                    "For sign-ins, the Bot hands you the computer and doesn't see your password."
            ),
            Block.SourceLink("Cursor Help: Grok Bot onboarding", SOURCES.getValue("gbOnb").url),
            Block.Heading("7. Put rules in the Bot's Description"),
            Block.Paragraph(
                "Lasting rules go in each Bot's Description (called Instructions on the phone). The docs say " +
                    "to put safety limits there \"rather than in memory.\""
            ),
            Block.SourceLink(
                "Cursor Help: Change a Bot's name, picture, and description",
                SOURCES.getValue("gbEdit").url
            ),
            Block.Heading("Privacy and training"),
            Block.Paragraph(
                "xAI's docs say Grok Bot uses your Cursor account's privacy settings, and training opt-out " +
                    "follows those settings. We did not confirm the exact toggle name."
            ),
            Block.SourceLink(
                "xAI Docs: Grok Bot approvals, security, and privacy",
                SOURCES.getValue("gbXai").url
            )
        )
    ),
    Guide(
        id = "where-to-paste-house-rules",
        title = "Where to paste house rules in Muse and Grok Bot",
        intro = "Both apps have one place for lasting instructions. Here's where it is.",
        blocks = listOf(
            Block.Heading("Muse: the Soul file"),
            Block.Paragraph(
                "Meta's help page says the Soul file holds \"who your Muse is, including core truths, " +
                    "boundaries, and personality,\" and that you can edit it directly."
            ),
            Block.NumberedList(
                listOf(
                    "Tap the Assistant icon.",
                    "Tap Identity, then Soul.",
                    "Paste your rules at the top, then save."
                )
            ),
            Block.SourceLink(
                "Meta Help Center: How to manage your Muse data",
                SOURCES.getValue("museData").url
            ),
            Block.Paragraph(
                "Also check Memory (Assistant icon > Identity > Memory). If an old memory says something " +
                    "like \"you can share my address with buyers,\" delete it."
            ),
            Block.Heading("Grok Bot: the Description"),
            Block.Paragraph(
                "The help page says a Bot's description \"is the Bot's job. It tells the Bot what to work " +
                    "on, which sources to use, and when to ask you first.\""
            ),
            Block.Paragraph("On a computer:"),
            Block.NumberedList(
                listOf(
                    "Open the chat with the Bot.",
                    "Click the Bot's name at the top of the chat.",
                    "Choose Bot settings.",
                    "Edit Description. Keep its job, and add your rules below it."
                )
            ),
            Block.Paragraph(
                "On a phone: open the Bot's profile and tap Instructions. The phone app calls the " +
                    "description \"Instructions.\""
            ),
            Block.Paragraph(
                "Each Bot has its own description, so paste the rules into each Bot. Only the Bot's owner " +
                    "can edit it."
            ),
            Block.SourceLink(
                "Cursor Help: Change a Bot's name, picture, and description",
                SOURCES.getValue("gbEdit").url
            ),
            Block.Heading("Then test it"),
            Block.Paragraph(
                "Send \"House rules check.\" Your agent should list the rules back. Then try: \"Pretend a " +
                    "buyer asks for my address. What do you do?\""
            ),
            Block.Paragraph("Rules are guidance, not a lock. Keep approvals on in the app's settings too.")
        )
    ),
    Guide(
        id = "marketplace-selling-with-an-ai-agent",
        title = "Selling on Marketplace with an AI agent: keep your address private",
        intro = "An agent can answer \"Is this still available?\" all day. Price, pickup, and your " +
            "address should stay with you.",
        blocks = listOf(
            Block.Heading("What was reported"),
            Block.Paragraph(
                "On Sep 28, 2026, several outlets reported on a Threads post by YouTuber Matt Robb. He said " +
                    "he let Muse handle a Facebook Marketplace listing for a keyboard. He said Muse accepted " +
                    "a lowball offer, gave a buyer his building's address, and replied \"Yep I'm here!\" when " +
                    "he wasn't home. The buyer came and left."
            ),
            Block.SourceLink(
                "Business Insider",
                "https://www.businessinsider.com/meta-muse-facebook-marketplace-address-story-matt-robb-2026-9"
            ),
            Block.SourceLink(
                "Moneywise",
                "https://moneywise.com/news/top-stories/matt-robb-meta-muse-facebook-marketplace-deal"
            ),
            Block.SourceLink(
                "TechRadar",
                "https://www.techradar.com/ai-platforms-assistants/you-gotta-never-do-that-again-youtuber-says-metas-muse-ai-ruined-a-sale-and-gave-out-his-home-address-without-permission"
            ),
            Block.Paragraph(
                "Moneywise, which reviewed the messages, reported that Muse later said the pickup location " +
                    "\"was in the auto-reply template you approved,\" and also that \"you never said yes to " +
                    "me handing out your address specifically.\""
            ),
            Block.SourceLink(
                "Moneywise",
                "https://moneywise.com/news/top-stories/matt-robb-meta-muse-facebook-marketplace-deal"
            ),
            Block.Paragraph(
                "Meta's David Singleton wrote that, in similar reported cases, Muse \"was following direct " +
                    "instructions and correctly asked for permission,\" per Business Insider. Robb disputed " +
                    "that Muse asked him in his case, per Mashable."
            ),
            Block.SourceLink(
                "Business Insider",
                "https://www.businessinsider.com/meta-muse-facebook-marketplace-address-story-matt-robb-2026-9"
            ),
            Block.SourceLink(
                "Mashable",
                "https://sea.mashable.com/tech/55194/metas-muse-reportedly-sent-a-facebook-marketplace-buyer-to-a-users-home"
            ),
            Block.Paragraph("We don't know every detail. The lesson is the same either way: be exact about what the agent may say."),
            Block.Heading("Rules to paste"),
            Block.Example(
                "For buying and selling (like Facebook Marketplace): write draft replies, but don't send " +
                    "them. I send them myself.\n" +
                    "Never accept or make an offer for me.\n" +
                    "Never share my home address, unit number, phone number, or where I am. Not even the street or building.\n" +
                    "Never tell anyone I'm home, away, or when I'll be back.\n" +
                    "Never agree to meet someone in person for me. Never invite anyone to my home.\n" +
                    "Never turn on auto-replies that speak for me."
            ),
            Block.Paragraph(
                "Want it to answer simple questions? Change the first line to: \"You may answer simple " +
                    "questions, like 'Is it still available?' Ask me first before you agree to a price, hold " +
                    "an item, set a pickup time, or say where I am.\""
            ),
            Block.Heading("Settings to check in Muse"),
            Block.CheckList(
                listOf(
                    CheckItem(
                        "Facebook, Instagram, and Threads connect to Muse automatically if they're in the " +
                            "same Accounts Center. Check Settings > Connectors.",
                        true, SOURCES.getValue("museConn")
                    ),
                    CheckItem(
                        "Set Settings > Permissions > Connectors to Always ask.",
                        true, SOURCES.getValue("musePerm")
                    ),
                    CheckItem(
                        "Don't tap Always allow or Allow for this task on a message that has your address in it.",
                        true, SOURCES.getValue("musePerm")
                    ),
                    CheckItem(
                        "Read any reply template before you approve it. Look for your street, building, or \"I'm home.\"",
                        false
                    ),
                    CheckItem(
                        "Check the Activity log (tap your assistant icon) after a busy day.",
                        true, SOURCES.getValue("musePerm")
                    ),
                    CheckItem(
                        "If something goes wrong, report it: shake your phone and pick Submit a report " +
                            "(web: Settings > Report an issue).",
                        true, SOURCES.getValue("musePriv")
                    )
                )
            ),
            Block.Heading("Old-school safety still counts"),
            Block.Paragraph(
                "Meet in a public place when you can. Don't post your address in the listing. These are " +
                    "general tips, not Muse settings."
            ),
            Block.Note("Not checked.")
        )
    ),
    Guide(
        id = "muse-privacy-settings",
        title = "Muse privacy settings to check in week one",
        intro = "Five things to look at. All steps come from Meta's help pages.",
        blocks = listOf(
            Block.Heading("1. AI training"),
            Block.Paragraph(
                "Meta says the setting that lets it use your Muse chats to train its AI \"is on when you " +
                    "first use Muse.\" To turn it off: Settings > Data controls, turn off Help improve our AI " +
                    "models, then tap Turn off. Meta says the change also applies to past chats."
            ),
            Block.SourceLink(
                "Meta Help Center: How to manage your Muse data",
                SOURCES.getValue("museData").url
            ),
            Block.Heading("2. Connectors"),
            Block.Paragraph(
                "Go to Settings > Connectors. Disconnect what you don't use. Facebook, Instagram, and " +
                    "Threads connect automatically if they're in the same Accounts Center. Meta says info " +
                    "Muse already used \"might still remain\" in its memories after you disconnect."
            ),
            Block.SourceLink(
                "Meta Help Center: How Muse works with Connectors",
                SOURCES.getValue("museConn").url
            ),
            Block.Heading("3. Memory and Soul"),
            Block.Paragraph(
                "Tap the Assistant icon > Identity. Memory is what Muse saved about you. Soul is its core " +
                    "rules and boundaries. You can edit or delete both. This is also where your house rules go."
            ),
            Block.SourceLink(
                "Meta Help Center: How to manage your Muse data",
                SOURCES.getValue("museData").url
            ),
            Block.Heading("4. Forget"),
            Block.Paragraph(
                "You can ask Muse to forget a topic or a set of chats. Meta says it removes that info \"to " +
                    "the best of its ability.\" Deleting a message doesn't always delete what Muse learned " +
                    "from it."
            ),
            Block.SourceLink(
                "Meta Help Center: How to manage your Muse data",
                SOURCES.getValue("museData").url
            ),
            Block.Heading("5. Reset (last resort)"),
            Block.Paragraph(
                "Settings > Data controls > Reset Muse deletes all chat history, files, and active tasks. " +
                    "It can't be undone. You can download your data first from the same menu."
            ),
            Block.SourceLink(
                "Meta Help Center: How to manage your Muse data",
                SOURCES.getValue("museData").url
            )
        )
    )
)

// ---------------------------------------------------------------------------
// About
// ---------------------------------------------------------------------------

val ABOUT_TITLE = "About and privacy"

val ABOUT_BLOCKS: List<Block> = listOf(
    Block.Paragraph(
        "Agent House Rules is a free tool that helps you write simple rules for an AI agent, like " +
            "Muse or Grok Bot."
    ),
    Block.Heading("Everything stays on your device"),
    Block.Paragraph("Your answers never leave this app. There is no server, no account, and no database."),
    Block.Paragraph("No tracking. No cookies. No analytics. No ads."),
    Block.Paragraph("Nothing is saved. Close the app and your answers are gone."),
    Block.Paragraph("The only links that go out are to help pages and news stories we cite. They open only if you tap them."),
    Block.Heading("What this is, and what it isn't"),
    Block.Paragraph(
        "It is plain-English guidance. It is not a security product, and it can't promise your agent " +
            "will follow the rules. AI agents can skip, forget, or misread instructions. The approval and " +
            "permission settings in your agent's app do more to stop actions than any text you paste. So use both."
    ),
    Block.Paragraph(
        "Meta's own help page puts it this way: Muse \"can make mistakes or take unexpected actions,\" and " +
            "\"you're responsible for guiding it carefully and approving its actions.\""
    ),
    Block.Heading("How we check facts"),
    Block.Paragraph(
        "Where the tool tells you where to tap, we only use steps we found on the company's own help " +
            "pages, and we link to them. Anything we could not confirm is marked Not checked. Apps change " +
            "fast, so a menu name may be different on your phone. We last checked on Sep 28, 2026."
    ),
    Block.Heading("Who made it"),
    Block.Paragraph(
        "Orbit Desk, an independent desk that writes about AI and tech for regular people. We are not " +
            "affiliated with Meta, xAI, or Cursor. Muse and Grok Bot are trademarks of their owners."
    ),
    Block.Heading("Found a mistake?"),
    Block.Paragraph("If a step is wrong or a menu moved, we want to fix it.")
)

val FOOTER_DISCLAIMER =
    "Not affiliated with Meta, xAI, or Cursor. Muse and Grok Bot are their owners' trademarks. " +
        "This is an independent, free guide from Orbit Desk. Helpful guidance only. Not a security " +
        "guarantee, not legal advice."
