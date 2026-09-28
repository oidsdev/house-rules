import SwiftUI
import UIKit

// MARK: - Helpers

/// Renders markdown-lite (**bold**) strings. Falls back to plain text if parsing fails.
func mdText(_ s: String) -> Text {
    if let attr = try? AttributedString(
        markdown: s,
        options: AttributedString.MarkdownParsingOptions(interpretedSyntax: .inlineOnlyPreservingWhitespace)
    ) {
        return Text(attr)
    }
    return Text(s)
}

struct PrimaryButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .font(.headline)
            .foregroundColor(Color(UIColor.systemBackground))
            .frame(maxWidth: .infinity)
            .padding()
            .background(Color.primary)
            .cornerRadius(10)
            .opacity(configuration.isPressed ? 0.75 : 1.0)
    }
}

struct SecondaryButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .font(.headline)
            .foregroundColor(Color.primary)
            .frame(maxWidth: .infinity)
            .padding()
            .background(Color(UIColor.secondarySystemBackground))
            .cornerRadius(10)
            .opacity(configuration.isPressed ? 0.75 : 1.0)
    }
}

struct CheckTag: View {
    let checked: Bool
    var body: some View {
        Text(checked ? "Checked" : "Not checked")
            .font(.caption2)
            .bold()
            .padding(.horizontal, 6)
            .padding(.vertical, 2)
            .background(checked ? Color.primary : Color.secondary.opacity(0.25))
            .foregroundColor(checked ? Color(UIColor.systemBackground) : Color.primary)
            .cornerRadius(4)
    }
}

// MARK: - Block renderer (paste guides, guides, about)

struct BlockView: View {
    let block: Block

    var body: some View {
        switch block {
        case .heading(let t):
            Text(t)
                .font(.headline)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.top, 10)
        case .paragraph(let t):
            mdText(t)
                .font(.body)
                .frame(maxWidth: .infinity, alignment: .leading)
        case .bullets(let bs):
            VStack(alignment: .leading, spacing: 8) {
                ForEach(bs.indices, id: \.self) { i in
                    HStack(alignment: .top, spacing: 8) {
                        Text("•")
                        VStack(alignment: .leading, spacing: 4) {
                            mdText(bs[i].text).font(.body)
                            if let c = bs[i].checked {
                                CheckTag(checked: c)
                            }
                        }
                    }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        case .steps(let ss):
            VStack(alignment: .leading, spacing: 8) {
                ForEach(ss.indices, id: \.self) { i in
                    HStack(alignment: .top, spacing: 8) {
                        Text("\(i + 1).").bold()
                        mdText(ss[i]).font(.body)
                    }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        case .verbatim(let ls):
            VStack(alignment: .leading, spacing: 6) {
                ForEach(ls.indices, id: \.self) { i in
                    Text(ls[i]).font(.system(.body, design: .monospaced))
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding()
            .background(Color(UIColor.secondarySystemBackground))
            .cornerRadius(8)
        case .sourceLink(let s):
            Link("Source: \(s.name)", destination: URL(string: s.url)!)
                .font(.footnote)
                .foregroundColor(.secondary)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}

// MARK: - App flow

enum AppFlow: Hashable {
    case welcome, agent, quiz(Int), results
}

struct RootView: View {
    @State private var flow: AppFlow = .welcome
    @State private var agent: AgentChoice = .muse
    @State private var answers: [String: String] = [:]
    @State private var extras: [String: String] = [:]

    var body: some View {
        Group {
            switch flow {
            case .welcome:
                WelcomeView(onStart: { flow = .agent })
            case .agent:
                AgentPickerView(agent: $agent,
                               onBack: { flow = .welcome },
                               onNext: { flow = .quiz(0) })
            case .quiz(let i):
                QuizView(index: i, answers: $answers, extras: $extras,
                         onBack: { flow = (i == 0) ? .agent : .quiz(i - 1) },
                         onNext: { flow = (i + 1 < questions.count) ? .quiz(i + 1) : .results })
            case .results:
                ResultsView(agent: agent, answers: answers, extras: extras, onRestart: {
                    answers = [:]
                    extras = [:]
                    flow = .welcome
                })
            }
        }
        .animation(.default, value: flow)
    }
}

// MARK: - 1. Welcome

struct WelcomeView: View {
    let onStart: () -> Void

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Text("Give your AI agent house rules.")
                        .font(.largeTitle)
                        .bold()
                        .padding(.top, 24)

                    Text("AI agents like Muse and Grok Bot can send messages, buy things, and book stuff for you. They follow instructions. So give them good ones.")
                        .font(.title3)

                    Text("Answer 10 easy questions. You get a short list of rules to copy and paste into your agent, plus a checklist of settings to change.")

                    Text("About 3 minutes. Free. No sign-up. Nothing you pick leaves this device.")
                        .font(.subheadline)
                        .foregroundColor(.secondary)

                    Button("Start", action: onStart)
                        .buttonStyle(PrimaryButtonStyle())
                        .padding(.top, 8)

                    NavigationLink("Read the short guides first") {
                        GuidesListView()
                    }
                    .buttonStyle(SecondaryButtonStyle())

                    VStack(alignment: .leading, spacing: 8) {
                        Text("Good to know").bold()
                        Text("Rules help, but they are not a lock. An agent can still get things wrong. That's why you also get a settings checklist. The app's own settings do more to stop actions than any text you paste.")
                            .font(.subheadline)
                    }
                    .padding()
                    .background(Color(UIColor.secondarySystemBackground))
                    .cornerRadius(10)
                    .padding(.top, 8)

                    Text("Not affiliated with Meta, xAI, or Cursor. Muse and Grok Bot are their owners' trademarks. This is an independent, free guide from Orbit Desk.")
                        .font(.footnote)
                        .foregroundColor(.secondary)
                        .padding(.top, 16)
                }
                .padding()
            }
            .toolbar {
                NavigationLink("About") { GuideDetailView(guide: aboutGuide) }
            }
        }
    }
}

// MARK: - 2. Agent picker

struct AgentPickerView: View {
    @Binding var agent: AgentChoice
    let onBack: () -> Void
    let onNext: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Text("Before the questions")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                Spacer()
                Button("Back", action: onBack)
            }
            ProgressView(value: 0.08)

            Text("Which agent do you use?")
                .font(.largeTitle)
                .bold()
            Text("We'll show you where to paste your rules.")
                .foregroundColor(.secondary)

            ForEach(AgentChoice.allCases) { choice in
                Button(action: {
                    agent = choice
                    onNext()
                }) {
                    HStack {
                        VStack(alignment: .leading, spacing: 2) {
                            Text(choice.title).font(.headline)
                            Text(choice.subtitle).font(.subheadline).foregroundColor(.secondary)
                        }
                        Spacer()
                        Image(systemName: "chevron.right").foregroundColor(.secondary)
                    }
                    .padding()
                    .background(Color(UIColor.secondarySystemBackground))
                    .cornerRadius(10)
                }
                .buttonStyle(.plain)
            }
            Spacer()
        }
        .padding()
    }
}

// MARK: - 3. Quiz

struct QuizView: View {
    let index: Int
    @Binding var answers: [String: String]
    @Binding var extras: [String: String]
    let onBack: () -> Void
    let onNext: () -> Void

    var q: QuizQuestion { questions[index] }
    var isLast: Bool { index == questions.count - 1 }

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Text("Question \(index + 1) of \(questions.count)")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                Spacer()
                Button("Back", action: onBack)
            }
            ProgressView(value: Double(index + 1), total: Double(questions.count))

            ScrollView {
                VStack(alignment: .leading, spacing: 12) {
                    Text(q.title)
                        .font(.title2)
                        .bold()
                    Text(q.hint)
                        .font(.subheadline)
                        .foregroundColor(.secondary)

                    ForEach(q.options.indices, id: \.self) { i in
                        let opt = q.options[i]
                        let selected = answers.wrappedValue[q.id] == opt.value
                        Button(action: { answers.wrappedValue[q.id] = opt.value }) {
                            HStack {
                                VStack(alignment: .leading, spacing: 4) {
                                    Text(opt.label).font(.headline)
                                    if !opt.sub.isEmpty {
                                        Text(opt.sub).font(.subheadline).foregroundColor(.secondary)
                                    }
                                }
                                Spacer()
                                Image(systemName: selected ? "checkmark.circle.fill" : "circle")
                                    .foregroundColor(selected ? Color.primary : Color.secondary)
                            }
                            .padding()
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(10)
                            .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(Color.primary, lineWidth: selected ? 2 : 0)
                            )
                        }
                        .buttonStyle(.plain)
                    }

                    if let opt = q.options.first(where: { $0.value == answers.wrappedValue[q.id] }),
                       let ex = opt.extra {
                        VStack(alignment: .leading, spacing: 6) {
                            Text(ex.label).font(.subheadline)
                            TextField(ex.def, text: Binding(
                                get: { extras.wrappedValue[ex.key] ?? ex.def },
                                set: { extras.wrappedValue[ex.key] = $0 }
                            ))
                            .keyboardType(ex.type == .number ? .numberPad : .default)
                            .textFieldStyle(.roundedBorder)
                        }
                        .padding(.top, 4)
                    }
                }
            }

            Button(isLast ? "See my rules" : "Next", action: onNext)
                .buttonStyle(PrimaryButtonStyle())
                .disabled(answers.wrappedValue[q.id] == nil)
                .opacity(answers.wrappedValue[q.id] == nil ? 0.4 : 1.0)
        }
        .padding()
    }
}

// MARK: - Share sheet

struct ShareSheet: UIViewControllerRepresentable {
    let text: String

    func makeUIViewController(context: Context) -> UIActivityViewController {
        let vc = UIActivityViewController(activityItems: [text], applicationActivities: nil)
        // iPad presents the share sheet as a popover and crashes without an anchor.
        if let pop = vc.popoverPresentationController,
           let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
           let window = scene.windows.first {
            pop.sourceView = window
            pop.sourceRect = CGRect(x: window.bounds.midX, y: window.bounds.midY, width: 0, height: 0)
            pop.permittedArrowDirections = []
        }
        return vc
    }

    func updateUIViewController(_ vc: UIActivityViewController, context: Context) {}
}

// MARK: - 4. Results

struct ResultsView: View {
    let agent: AgentChoice
    let answers: [String: String]
    let extras: [String: String]
    let onRestart: () -> Void

    @State private var copied = false
    @State private var showShare = false

    var rules: String { buildRules(answers: answers, extras: extras) }

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Text(rules)
                        .font(.system(.body, design: .monospaced))
                        .textSelection(.enabled)
                        .padding()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(10)

                    Button(copied ? "Copied ✓" : "Copy my rules") {
                        UIPasteboard.general.string = rules
                        copied = true
                    }
                    .buttonStyle(PrimaryButtonStyle())
                    if copied {
                        Text("Copied. Now paste it into your agent (steps below).")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }

                    HStack(spacing: 12) {
                        Button("Share…") { showShare = true }
                            .buttonStyle(SecondaryButtonStyle())
                        Button("Start over", action: onRestart)
                            .buttonStyle(SecondaryButtonStyle())
                    }

                    VStack(alignment: .leading, spacing: 8) {
                        Text("This is guidance, not a guarantee.").bold()
                        Text("Agents can skip or forget rules. Keep approvals on, read each request before you tap yes, and use the settings below. You are still the one in charge.")
                            .font(.subheadline)
                    }
                    .padding()
                    .background(Color(UIColor.secondarySystemBackground))
                    .cornerRadius(10)

                    Group {
                        NavigationLink("How to paste it") { PasteView(initialAgent: agent) }
                        NavigationLink("Test it (1 minute)") { TestItView() }
                        NavigationLink("Settings checklist") { ChecklistView(initialAgent: agent, answers: answers) }
                        NavigationLink("Short guides") { GuidesListView() }
                    }
                    .buttonStyle(SecondaryButtonStyle())

                    Text("Helpful guidance only. Not a security guarantee, not legal advice. Everything stays on your device.")
                        .font(.footnote)
                        .foregroundColor(.secondary)
                        .padding(.top, 8)
                }
                .padding()
            }
            .navigationTitle("Your house rules")
            .sheet(isPresented: $showShare) { ShareSheet(text: rules) }
        }
    }
}

// MARK: - 5. Where to paste

struct PasteView: View {
    @State var tab: AgentChoice

    init(initialAgent: AgentChoice) {
        _tab = State(initialValue: initialAgent)
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                Picker("Agent", selection: $tab) {
                    ForEach(AgentChoice.allCases) { c in
                        Text(c.title).tag(c)
                    }
                }
                .pickerStyle(.segmented)

                if let guide = pasteGuides[tab] {
                    Text(guide.title)
                        .font(.title2)
                        .bold()
                        .padding(.top, 4)
                    ForEach(guide.blocks.indices, id: \.self) { i in
                        BlockView(block: guide.blocks[i])
                    }
                }
            }
            .padding()
        }
        .navigationTitle("How to paste it")
        .navigationBarTitleDisplayMode(.inline)
    }
}

// MARK: - 6. Test it

struct TestItView: View {
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                Text("After you paste, send your agent these, one at a time:")
                ForEach(testPrompts.indices, id: \.self) { i in
                    VStack(alignment: .leading, spacing: 6) {
                        Text("\(i + 1). \"\(testPrompts[i].prompt)\"")
                            .font(.headline)
                        Text(testPrompts[i].expect)
                            .foregroundColor(.secondary)
                    }
                    .padding()
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color(UIColor.secondarySystemBackground))
                    .cornerRadius(10)
                }
                Text(testFollowup)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
            }
            .padding()
        }
        .navigationTitle("Test it (1 minute)")
        .navigationBarTitleDisplayMode(.inline)
    }
}

// MARK: - 7. Settings checklist

struct ChecklistRow: View {
    let agent: AgentChoice
    let item: ChecklistItem
    @State private var done = false

    var key: String { "checklist.\(agent.rawValue).\(item.id)" }

    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            Button(action: {
                done.toggle()
                UserDefaults.standard.set(done, forKey: key)
            }) {
                Image(systemName: done ? "checkmark.square.fill" : "square")
                    .font(.title2)
                    .foregroundColor(Color.primary)
            }
            .buttonStyle(.plain)

            VStack(alignment: .leading, spacing: 6) {
                mdText(item.text)
                    .font(.body)
                    .strikethrough(done, color: .secondary)
                    .foregroundColor(done ? .secondary : .primary)
                HStack(spacing: 8) {
                    CheckTag(checked: item.checked)
                    if let s = item.source {
                        Link("(source)", destination: URL(string: s.url)!)
                            .font(.footnote)
                    }
                }
            }
        }
        .padding(.vertical, 6)
        .onAppear { done = UserDefaults.standard.bool(forKey: key) }
    }
}

struct ChecklistView: View {
    @State var tab: AgentChoice
    let answers: [String: String]

    init(initialAgent: AgentChoice, answers: [String: String]) {
        _tab = State(initialValue: initialAgent)
        self.answers = answers
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                Picker("Agent", selection: $tab) {
                    ForEach(AgentChoice.allCases) { c in
                        Text(c.title).tag(c)
                    }
                }
                .pickerStyle(.segmented)

                Text("Checked means we found it on the company's own help pages (link included). Not checked means good advice we could not confirm for your app. Menus change, so names may differ.")
                    .font(.subheadline)
                    .foregroundColor(.secondary)

                let list = checklist(for: tab, answers: answers)
                Text(list.title)
                    .font(.title2)
                    .bold()
                if let intro = list.intro {
                    Text(intro)
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
                ForEach(list.items.indices, id: \.self) { i in
                    ChecklistRow(agent: tab, item: list.items[i])
                    if i < list.items.count - 1 {
                        Divider()
                    }
                }
            }
            .padding()
        }
        .navigationTitle("Settings checklist")
        .navigationBarTitleDisplayMode(.inline)
    }
}

// MARK: - 8. Guides

struct GuidesListView: View {
    var body: some View {
        List {
            ForEach(guides.indices, id: \.self) { i in
                NavigationLink(guides[i].title) {
                    GuideDetailView(guide: guides[i])
                }
            }
        }
        .navigationTitle("Short guides")
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct GuideDetailView: View {
    let guide: Guide

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                ForEach(guide.blocks.indices, id: \.self) { i in
                    BlockView(block: guide.blocks[i])
                }
            }
            .padding()
        }
        .navigationTitle(guide.title)
        .navigationBarTitleDisplayMode(.inline)
    }
}
