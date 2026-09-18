# Capstone Tracking Backend

Spring Boot backend for the **Student Schedule and Guidance Management System**
(see the project's `blueprint.md` for the full 15-section spec). This is the
foundation the whole 5-sprint delivery plan (§12) builds on: every entity in the
Data Model (§8) exists in code, Sprint 1 and Sprint 2 have full working
service/controller layers, and Sprints 3–5 have their data layer ready so adding
their business logic is additive, not a refactor.

## Stack

- Java 17, Spring Boot 3.3.4, Maven
- Spring Web, Spring Data JPA, Spring Security (stateless JWT)
- PostgreSQL + Flyway migrations
- springdoc-openapi (Swagger UI)
- Lombok
- JUnit 5 + MockMvc + H2 (test profile)

## What's implemented

| Sprint | Area | Endpoints | Status |
|---|---|---|---|
| 1 | Auth | `POST /api/v1/auth/register`, `POST /api/v1/auth/login`, `GET /api/v1/auth/me` | Full — register restricted to `@fpt.edu.vn` (configurable), always creates a STUDENT |
| 1 | Users | `POST/GET /api/v1/users`, `GET/PUT /api/v1/users/{id}` | Full — Admin-only |
| 1 | Topics | `POST/GET /api/v1/topics`, `GET/PUT /api/v1/topics/{id}` | Full — create/edit = Admin, read = any role |
| 1 | Question Bank | `POST/GET /api/v1/topics/{topicId}/questions` | Full — matches API-006 |
| 1 | Student Groups | `POST/GET /api/v1/groups`, `GET/PUT /api/v1/groups/{id}`, `POST/DELETE /api/v1/groups/{id}/members[/{memberId}]` | Full — create/edit = Admin/Instructor |
| 2 | Schedule Slots | `POST/GET /api/v1/slots`, `GET /api/v1/slots/{id}` | Full — matches API-001/API-002, no-overlap rule (§5 step 1) |
| 2 | Bookings | `POST /api/v1/slots/{id}/book`, `DELETE /api/v1/bookings/{id}` | Full — matches API-003/API-004, pessimistic-lock capacity enforcement (NFR-002/R-001), late-cancellation window |
| — | Audit Trail | *(internal — `AuditService`, no endpoint yet)* | Recorder built and wired into booking create/cancel; extend the same call into Sprint 3–5 services as they're built |
| 3 | Artifact Submissions | — | **Data layer only**: `ArtifactSubmission` entity + repository |
| 4 | Meetings & Minutes | — | **Data layer only**: `MeetingSession`, `RequirementLog`, `MeetingMinute` entities + repositories |
| 5 | Evaluation & Reporting | — | **Data layer only**: `EvaluationRecord` entity + repository |

RBAC roles: `ADMIN`, `INSTRUCTOR`, `GROUP_LEADER`, `STUDENT`, enforced with
`@PreAuthorize` per blueprint.md §11.

"Data layer only" means the JPA entity, its lifecycle enum(s), and its Spring Data
repository exist and are covered by the Flyway migration — but there's no service/
controller yet. Building one of those sprints out is: add a `dto/` package next to
the entity, a service that follows `BookingService`'s shape (validate → mutate →
`auditService.record(...)` inside the same `@Transactional`), and a controller whose
`@PreAuthorize` matches the role column in blueprint.md §9's API contract.

## Run it in IntelliJ IDEA

1. **Open the project**: `File → Open...` and select this folder (the one with `pom.xml`).
   IntelliJ detects it as a Maven project and downloads dependencies automatically —
   watch the Maven tool window on the right for progress.
2. **Start PostgreSQL**: the easiest path is Docker —
   ```bash
   docker compose up -d
   ```
   This starts Postgres on `localhost:5432` with the database/user/password already
   baked in (see `docker-compose.yml`). No Docker? Install PostgreSQL locally and
   create a database/user matching `.env.example`, or point `DB_URL` at any Postgres
   instance you already have.
3. **Set environment variables** for the run configuration: copy `.env.example` to `.env`
   as a reference, then in IntelliJ go to the auto-generated `CapstoneTrackingBackendApplication`
   run configuration → **Modify options → Environment variables** and paste the values
   (or install the *EnvFile* plugin and point it at `.env` directly).
   The app runs with sane defaults even if you set nothing, **except** you must have
   Postgres reachable at the default URL.
4. **Run**: click the green ▶ next to `CapstoneTrackingBackendApplication.main()`, or
   `mvn spring-boot:run` from a terminal.
5. **Verify**: open http://localhost:8080/swagger-ui.html — you should see all endpoints
   grouped by tag (Auth, Users, Topics, Question Bank, Student Groups, Schedule Slots, Bookings).

On first boot, Flyway runs, in order:
- `V1__init_schema.sql` — Sprint 1 tables (users, topics, student_groups, group_members, question_bank_items)
- `V2__seed_admin.sql` — one Admin account
- `V3__sprint2_5_schema.sql` — every remaining table in the Data Model (schedule_slots, bookings,
  meeting_sessions, requirement_logs, meeting_minutes, evaluation_records, artifact_submissions,
  system_audit_trail)

Log in as the seeded admin:

```
email:    admin@fpt.edu.vn
password: Admin@123
```

**Change or remove this seed account before any shared/deployed environment.**

## Quick smoke test (curl)

```bash
# 1. Log in as admin
TOKEN=$(curl -s -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@fpt.edu.vn","password":"Admin@123"}' | python3 -c "import sys,json;print(json.load(sys.stdin)['accessToken'])")

# 2. Create a topic as admin
curl -s -X POST http://localhost:8080/api/v1/topics \
  -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" \
  -d '{"topicCode":"T001","title":"Smart Attendance System","category":"IoT"}'

# 3. As that same admin (standing in for an Instructor here), publish a slot
curl -s -X POST http://localhost:8080/api/v1/slots \
  -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" \
  -d '{"startTime":"2026-10-01T08:00:00Z","endTime":"2026-10-01T08:30:00Z","durationMinutes":30,"capacity":1,"locationType":"ONLINE","meetingUrl":"https://meet.example.com/x"}'
```

Booking itself needs a `GROUP_LEADER` token — see `BookingFlowIntegrationTest` for a
full worked example (create instructor, group, leader, slot, then book/cancel).

## Running tests

```bash
mvn test
```

Tests run against an in-memory H2 database (`application-test.yml`, `spring.profiles.active=test`),
so they don't need Postgres running.

- `AuthFlowIntegrationTest` — register → login → access-protected-endpoint → RBAC-rejection, end to end.
- `BookingFlowIntegrationTest` — the three rules that actually matter for NFR-002/R-001: a slot's
  last seat can't be double-booked (409), a group can't hold two active bookings (400), and a
  booking can't be cancelled inside the 2-hour window (400). This is a correctness test, not a load
  test — NFR-002 also calls for a k6/JMeter run at ≥50 concurrent requests before Sprint 2 ships.

> **A note on how this codebase was produced:** it was generated and hand-reviewed in a
> sandboxed environment without access to Maven Central, so `mvn compile`/`mvn test`
> could not be executed there to double-check it end to end. Run `mvn clean test` as your
> first step after opening the project — if anything doesn't compile, it is most likely a
> small dependency-version mismatch, easy to spot from the error and fix from there.

## Design notes / where the blueprint mapped to code

- **NFR-002 / R-001 (no over-booking under concurrency)**: `ScheduleSlotRepository.findByIdForUpdate`
  takes a `SELECT ... FOR UPDATE` row lock, held for the rest of `BookingService.book()`'s transaction.
  Two requests racing for the same slot's last seat now serialize at that line instead of both reading
  "capacity available" and both writing a Confirmed booking. `StudentGroupService.addMember`'s
  "one leader per group" check follows the same check-then-act-inside-`@Transactional` shape, but
  without a row lock — fine there because a losing request just gets a 409 to retry, not a phantom
  double-booking of a physically limited resource.
- **NFR-006 (auditability)**: `SystemAuditTrail` + `AuditService` are built and wired into
  `BookingService.book()`/`cancel()` (`AuditAction.CREATE`/`CANCEL`). `AuditService.record(...)` requires
  an existing transaction (`Propagation.MANDATORY`) so the audit row and the state change it documents
  always commit or roll back together. Extend the same one-line call into the Sprint 4/5 services
  (`MeetingMinute` approval, `EvaluationRecord` scoring) as they're built.
- **RBAC**: enforced with `@PreAuthorize` at the controller layer rather than in services, so the
  permission model is visible directly on each endpoint. Known simplification carried over from
  Sprint 1: a `GROUP_LEADER` can call booking/member endpoints for *any* group ID, not just their
  own — role checks don't yet verify resource ownership. Add an ownership check (e.g. a custom
  `@PreAuthorize("@groupSecurity.isLeaderOf(#groupId)")`) before this goes further than local dev.
- **Connection pool**: capped deliberately small in `application.yml` (`maximum-pool-size: 15`) —
  NFR-002's 50-concurrent-groups scenario is a burst of short row-locked transactions, not 50 held-open
  connections; a bigger pool just moves the queuing from the pool to Postgres's own lock manager.
  Re-tune once you have a real k6 run to look at.
- **Google SSO (A-005)**: `JwtTokenProvider`/`AuthService` are structured so a Google-issued identity
  can be exchanged for the same JWT this API already issues — that OAuth2 exchange itself isn't
  implemented here; `spring-boot-starter-oauth2-client` is the natural next dependency for it.
- **EvaluationRecord is Restricted Confidential (§11)**: the entity itself has a javadoc reminder never
  to return it directly from a controller — build a DTO for it the way `UserResponse`/`TopicResponse`
  already do, before wiring up Sprint 5's endpoints.
