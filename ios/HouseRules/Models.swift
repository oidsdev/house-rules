import Foundation

// MARK: - Quiz data model

enum AgentChoice: String, CaseIterable, Identifiable {
    case muse, grok, other

    var id: String { rawValue }

    var title: String {
        switch self {
        case .muse: return "Muse"
        case .grok: return "Grok Bot"
        case .other: return "Something else"
        }
    }

    var subtitle: String {
        switch self {
        case .muse: return "Meta's AI agent app"
        case .grok: return "xAI's agent app"
        case .other: return "Any other AI agent or assistant"
        }
    }
}

enum ExtraType {
    case number, text
}

struct ExtraField {
    let type: ExtraType
    let key: String
    let label: String
    let def: String
}

struct QuizOption {
    let value: String
    let label: String
    let sub: String
    let extra: ExtraField?
}

struct QuizQuestion {
    let id: String
    let title: String
    let hint: String
    let options: [QuizOption]
}

// MARK: - Rich content blocks (used by paste guides, guides, about)

struct Source {
    let name: String
    let url: String
}

struct Bullet {
    let text: String
    /// nil = no tag; true = "Checked", false = "Not checked"
    let checked: Bool?
}

enum Block {
    case heading(String)
    case paragraph(String)   // markdown-lite (**bold**)
    case bullets([Bullet])   // markdown-lite
    case steps([String])     // numbered, markdown-lite
    case verbatim([String])  // monospaced box
    case sourceLink(Source)  // "Source: name" tappable link
}

struct PasteGuide {
    let title: String
    let blocks: [Block]
}

struct Guide {
    let id: String
    let title: String
    let blocks: [Block]
}

struct ChecklistItem {
    let id: String
    let text: String         // markdown-lite (**bold**)
    let checked: Bool        // true = "Checked", false = "Not checked"
    let source: Source?
}
