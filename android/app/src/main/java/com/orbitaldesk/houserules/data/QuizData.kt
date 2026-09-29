package com.orbitaldesk.houserules.data

/** An optional follow-up input shown when its option is selected. */
data class ExtraField(
    val type: String, // "number" or "text"
    val key: String,
    val label: String,
    val default: String
)

data class QuizOption(
    val value: String,
    val label: String,
    val sub: String,
    val extra: ExtraField? = null
)

data class Question(
    val id: String,
    val title: String,
    val hint: String,
    val options: List<QuizOption>
)

data class Source(val name: String, val url: String)

/** Official help pages cited by the checklists and guides. */
val SOURCES: Map<String, Source> = mapOf(
    "musePerm" to Source(
        "Meta Help Center: How Muse works with your guidance and approval",
        "https://www.meta.com/help/artificial-intelligence/1385290430137537/"
    ),
    "museData" to Source(
        "Meta Help Center: How to manage your Muse data",
        "https://www.meta.com/help/artificial-intelligence/2225571704857152/"
    ),
    "museConn" to Source(
        "Meta Help Center: How Muse works with Connectors",
        "https://www.meta.com/help/artificial-intelligence/1687253048996149/"
    ),
    "musePriv" to Source(
        "Meta Help Center: How Muse handles your privacy, safety and security",
        "https://www.meta.com/help/artificial-intelligence/1047255454427887/"
    ),
    "musePay" to Source(
        "Meta Help Center: How Muse works with payments",
        "https://www.meta.com/help/artificial-intelligence/1436362127544482/"
    ),
    "gbEdit" to Source(
        "Cursor Help: Change a Bot's name, picture, and description",
        "https://cursor.com/help/grok-bot/edit-bot"
    ),
    "gbOnb" to Source(
        "Cursor Help: Grok Bot onboarding",
        "https://cursor.com/help/grok-bot/onboarding"
    ),
    "gbSec" to Source(
        "Cursor Docs: Grok Bot security",
        "https://cursor.com/docs/grok-bot/security"
    ),
    "gbFaq" to Source(
        "Cursor Help: Grok Bot FAQs",
        "https://cursor.com/help/grok-bot/faqs"
    ),
    "gbWork" to Source(
        "Cursor Docs: Work with Grok Bot",
        "https://cursor.com/docs/grok-bot/work"
    ),
    "gbXai" to Source(
        "xAI Docs: Grok Bot approvals, security, and privacy",
        "https://docs.x.ai/grok-bot/approvals-security-and-privacy"
    )
)

/** The 10 quiz questions, ported verbatim from the web app. */
val QUESTIONS: List<Question> = listOf(
    Question(
        id = "money",
        title = "Can your agent spend your money?",
        hint = "This means buying, paying, tipping, subscribing, or donating.",
        options = listOf(
            QuizOption("never", "No. Never spend money.", "It can find prices and make a list. I pay myself."),
            QuizOption(
                "limit", "Only after I say yes, with a limit.", "Recommended if you want it to shop.",
                ExtraField("number", "limit", "Most it can spend on one thing, in dollars", "25")
            ),
            QuizOption("ask", "Only after I say yes. No set limit.", "It asks every single time.")
        )
    ),
    Question(
        id = "personal",
        title = "Can it share your address, phone number, or location?",
        hint = "This includes your street, building, unit number, or saying \"I'm home.\"",
        options = listOf(
            QuizOption("never", "Never share them.", "Recommended."),
            QuizOption("ask", "Ask me first, every time.", "It tells me who wants it and why.")
        )
    ),
    Question(
        id = "strangers",
        title = "Can it message or call people you don't know?",
        hint = "Like a new buyer, a stranger who texts you, or someone new by email.",
        options = listOf(
            QuizOption("never", "No. Only people I already know.", "It tells me about new people instead."),
            QuizOption("ask", "Yes, but it shows me the message first.", "I say yes before it sends."),
            QuizOption("business", "Businesses are OK. New people need my yes.", "For things like store hours, prices, or quotes.")
        )
    ),
    Question(
        id = "market",
        title = "Will it help you buy or sell stuff online?",
        hint = "Like Facebook Marketplace, Craigslist, or eBay.",
        options = listOf(
            QuizOption("none", "No. Keep it out of buying and selling.", ""),
            QuizOption("draft", "Draft replies only. I send them myself.", "Recommended to start."),
            QuizOption(
                "reply", "It can answer simple questions.",
                "But it asks me about price, pickup, meetups, and address."
            )
        )
    ),
    Question(
        id = "meet",
        title = "Can it book things or set up plans for you?",
        hint = "Like appointments, tables, rides, trips, or pickups.",
        options = listOf(
            QuizOption("never", "No. I book things myself.", "It can show me options."),
            QuizOption("ask", "Ask me first, every time.", "It shows me the place, date, time, and cost.")
        )
    ),
    Question(
        id = "email",
        title = "Can it use your email and calendar?",
        hint = "Reading, writing, sending, and invites.",
        options = listOf(
            QuizOption("none", "No. Stay out of my email and calendar.", ""),
            QuizOption("read", "It can read them and write drafts.", "I send everything myself."),
            QuizOption("send", "It can send, but asks me first.", "Every email and every invite.")
        )
    ),
    Question(
        id = "family",
        title = "Will it deal with kids or family?",
        hint = "Names, photos, schools, schedules, or where they are.",
        options = listOf(
            QuizOption("never", "Keep all family info private.", "Recommended."),
            QuizOption(
                "list", "Only share with people I name.", "And ask me first.",
                ExtraField("text", "familyOk", "Who is OK? (optional, like \"Grandma Ann, Coach Lee\")", "")
            )
        )
    ),
    Question(
        id = "sensitive",
        title = "Can it share your health or money info?",
        hint = "Like doctor visits, medicine, bank details, or account numbers.",
        options = listOf(
            QuizOption("never", "Never share it with anyone.", "Recommended."),
            QuizOption("ask", "Ask me first, every time.", "It tells me who gets it and why.")
        )
    ),
    Question(
        id = "confirm",
        title = "When should it check with you before acting?",
        hint = "Checking in is slower, but you see what it's about to do.",
        options = listOf(
            QuizOption(
                "outside", "Before anything that leaves my phone.",
                "Sending, buying, booking, posting, sharing, or deleting. Recommended."
            ),
            QuizOption("all", "Before everything.", "Good for your first week.")
        )
    ),
    Question(
        id = "unsure",
        title = "What should it do when it isn't sure?",
        hint = "Agents guess. You can tell it not to.",
        options = listOf(
            QuizOption("ask", "Stop and ask me.", "Recommended."),
            QuizOption("wait", "Stop, do nothing, and tell me later.", "Fewer pings. Some tasks will wait.")
        )
    )
)
