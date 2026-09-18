# Intent: Student Schedule and Guidance Management System

**Author:** Group 2
**Status:** Draft
**Last updated:** 2026-09-11

> Note: Sections marked *(Draft – for team review)* were filled in based on the problem/outcome statements provided and on Calendly (https://calendly.com/) as a reference model for the scheduling module. They should be reviewed and confirmed by the team before this document leaves Draft status.

## 1. Overview

This system supports the supervision and evaluation workflow between instructors (supervisors) and student project groups throughout a course or thesis cycle. It covers four areas: scheduling assessment/meeting sessions, managing group documentation and information, preparing topic-related question banks and materials, and supporting in-meeting activities (Q&A, requirement capture, minutes) together with periodic statistics and instructor evaluation.

## 2. Problem Statement

- Scheduling assessments: defining available time slots and per-slot capacity requirements for group check-ins/defenses.
- Managing documentation and group information (members, topic, progress artifacts).
- Preparing categorized questions and materials related to project topics, managed by the admin.
- During meetings: answering questions, recording new requirements raised, and generating meeting minutes automatically.
- Compiling statistics on a weekly and semester basis.

## 3. Reference Model for Scheduling

Calendly is used as the reference UX/functional model for the assessment-scheduling module, since it solves a closely related problem: letting one party publish available time slots (with duration and capacity) and letting others book into them without back-and-forth messaging, while syncing against an existing calendar to prevent conflicts.

Mapped onto this system *(Draft – for team review)*:

- The admin/instructor defines a set of assessment slots (date, time window, duration, room/link) similar to a Calendly event type.
- Each slot has a capacity (e.g., number of groups or number of students that can be assessed within it), analogous to Calendly's "collective"/group-event capacity.
- Groups book into an open slot; the system prevents double-booking and closes a slot once capacity is reached.
- Automated confirmation and reminder notifications are sent to both the group and the instructor.
- The instructor's existing calendar (if any) can be checked to avoid conflicts, the way Calendly syncs with Google/Outlook calendars.

This is a reference for behavior only — no integration with Calendly itself is assumed unless the team later decides to build on top of its API.

## 4. Expected Outcomes

- During sessions: the system assists in answering questions, logging newly raised requirements, and auto-generating meeting minutes.
- The system compiles weekly and semester-based statistics (e.g., number of sessions held, attendance, requirements raised/resolved, group progress trends).
- Instructors can evaluate groups along three dimensions — project topic fit, product quality, and communication — with detailed, per-group evaluation records and supporting information.

## 5. Affected Users and Systems *(Draft – for team review)*

**Users**
- Students (individual members of a project group)
- Group leaders (may have additional permissions: booking slots, submitting documents on behalf of the group)
- Instructors / supervisors (define slots, review documents, conduct evaluations)
- Admin (manages topics, question banks, materials, user accounts, and overall scheduling policy)

**Systems / integrations (candidates, to be confirmed)**
- Academic calendar or timetable system (to avoid scheduling conflicts with classes)
- Existing LMS or course management system (for topic/group rosters, if one already exists)
- Document storage (for group deliverables and meeting minutes)
- Notification channel (email and/or in-app) for booking confirmations and reminders

## 6. Limitations *(Draft – for team review)*

- Scope for v1 is assumed to be a single course/semester cycle; multi-cohort or multi-year history is not guaranteed unless explicitly requested.
- Meeting minutes generation is assumed to work from text input (typed notes or a transcript provided to the system), not live audio transcription, unless a transcription component is scoped in separately.
- Evaluation scoring rubric (weights across topic fit, product quality, communication) is not yet defined and needs input from instructors.
- No assumption yet on video-conferencing integration (e.g., whether sessions happen in person, or via an external tool the system only schedules but does not host).
- Capacity/slot conflict handling assumes a single admin/instructor calendar of truth; concurrent editing by multiple admins is not yet addressed.

## 7. Open Questions *(Draft – for team review)*

- Who can create/modify assessment slots — only the admin, or also individual instructors?
- What counts toward "capacity" for a slot — number of groups, number of students, or a fixed time budget per group?
- Should the system integrate with an existing calendar tool (e.g., via Calendly's API, Google Calendar) or manage scheduling entirely on its own?
- What is the required format and level of detail for auto-generated meeting minutes, and who can edit them after generation?
- How is the evaluation rubric structured, and is it fixed per semester or configurable per topic/course?
- What data retention policy applies to meeting minutes, requirements logs, and evaluation records across semesters?
- Is authentication tied to an existing university account system (SSO) or self-managed?

## 8. References

- Calendly — https://calendly.com/ (reference model for time-slot- and capacity-based scheduling)
