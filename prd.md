Product Requirements Document (PRD): VaultPrep SSC

Project Name: VaultPrep SSC

Concept: Distraction-Free, Offline-First Competitive Exam Toolkit

Platform: Mobile

1. Executive Summary

VaultPrep is a study application for SSC (Staff Selection Commission) aspirants designed to eliminate internet-based distractions. The app operates on a "Download Once, Study Anywhere" model, using a heavy local SQLite database to provide thousands of questions, mock tests, and detailed analytics without requiring an active data connection.

2. Problem Statement

Competitive exam aspirants struggle with:

Internet Distractions: Social media and news notifications during study hours.

Unreliable Connectivity: Students in rural areas cannot access cloud-based test series.

Subscription Fatigue: Users prefer one-time "ownership" over recurring monthly fees.

3. User Personas

The Focused Aspirant: Wants to turn off their phone's internet for 4 hours and solve 200 questions without interruption.

The Rural Learner: Has high-speed internet only once a week at a town center and needs to download all content for offline use.

4. Functional Requirements

4.1 Content & Data Management

Initial Sync: Upon first launch, the app must download a compressed .sqlite or .db file (approx. 200-500MB).

Incremental Updates: Ability to check for "New Question Packs" and append them to the local database when online.

Media Handling: All math formulas and diagrams must be rendered using SVGs or local WebP images stored in the app filesystem.

4.2 The "Zen" Exam Engine

Offline Verification: The app should detect if the device is offline and grant "Deep Work Points" for sessions completed in Airplane Mode.

Test Types:

PYQ Mode: Fixed sets of previous year papers.

Topic Drill: Focused practice on specific tags (e.g., "Trigonometry", "Ancient History").

Mock Generator: Algorithm-driven generation of unique 100-question sets based on SSC weightage.

State Persistence: If the app crashes or the phone dies, the current test state must be recovered from the local DB.

4.3 Local Analytics (The "Vault Engine")

Performance Tracking: Calculate accuracy, speed (seconds per question), and subject-wise strength.

Topic Heatmaps: Visual representation of weak areas based on the last 10 sessions.

Local Leaderboard: Comparison against "Benchmarked Scores" stored in the local DB (since global leaderboards require internet).

4.4 Monetization System

Free Tier: Access to 1 year of PYQs and 3 Mock Tests.

Pro Vault (One-time Purchase): * Unlock 10+ years of PYQs.

Enable the Infinite Mock Generator.

Advanced Analytics.

Mechanism: Use a simple License Key or IAP (In-App Purchase) that toggles a boolean flag in the local encrypted storage.

5. Technical Specifications

5.1 Tech Stack Recommendations

Frontend: Flutter (Preferred for offline performance) or React Native.

Database: SQLite with SQLCipher (AES-256 encryption) to prevent users from extracting the question bank.

Local Storage: Key-Value store (like Hive or SharedPrefs) for user settings.

5.2 Database Schema (High-Level)

Table: questions

id (PK), subject_id, topic_id, text, option_a/b/c/d, correct_idx, explanation_text, image_path, year.

Table: user_attempts

id, question_id, selected_idx, is_correct, time_spent, session_id.

Table: test_sessions

id, test_name, score, total_questions, timestamp, is_focus_mode_active.

6. UI/UX Requirements

Minimalist Design: No flashy animations. High-contrast text for long reading sessions.

Night Mode: Essential for students studying late at night.

Navigation: Bottom tabs: [Dashboard, Library, Analytics, Settings].

7. Implementation Roadmap (Sprint for Antigravity)

Phase 1: Database Foundation

Set up SQLite.

Import a CSV of 1,000 sample questions.

Build the QuestionProvider logic to fetch questions by topic.

Phase 2: Exam Logic

Build the Timer and Scoring engine.

Implement "Mark for Review" and "Navigation Grid" for exams.

Phase 3: Focus Mode & Sync

Implement Connectivity listener.

Build the Download Manager for the .db file.

Phase 4: Analytics & Paywall

Build the Result charts.

Implement the Premium unlock logic.

8. Success Metrics

Offline Ratio: % of study time spent without an active internet connection.

Retention: Number of users returning for 30+ consecutive days (the "Study Streak").