# Software Development Blueprint

**Status:** Ready for Review  
**Version:** 1.0  
**Owner:** Group 2 (BA Lead & Development Team)  
**Last updated:** 2026-09-15  

---

## 1. Executive Summary

- **Business problem:**  
  Quy trình hướng dẫn, theo dõi và đánh giá đồ án/khóa luận giữa giảng viên hướng dẫn (Supervisor) và các nhóm sinh viên hiện còn phân mảnh và thủ công:
  - Việc lên lịch hẹn/bảo vệ thường xuyên gặp xung đột lịch, thiếu kiểm soát tải (capacity) trên từng khung giờ.
  - Quản lý thông tin nhóm, đề tài và tài liệu tiến độ (artifacts) phân tán trên nhiều kênh rời rạc (email, chat cá nhân).
  - Thiếu kho câu hỏi và học liệu chuẩn hóa theo từng chủ đề đề tài để hỗ trợ giảng viên phản biện và định hướng nhóm.
  - Trong các buổi gặp/kiểm tra, việc trả lời câu hỏi, ghi nhận yêu cầu mới phát sinh và lập biên bản cuộc họp (meeting minutes) tốn nhiều thời gian, thiếu tính lưu vết (traceability).
  - Việc tổng hợp thống kê tiến độ định kỳ (hàng tuần, theo kỳ) và đánh giá sinh viên đa chiều (Topic fit, Product quality, Communication) chưa được chuẩn hóa, tốn nhiều công sức tổng hợp báo cáo cuối kỳ.

- **Desired outcome:**  
  Xây dựng hệ thống **Student Schedule and Guidance Management System** tập trung hóa toàn bộ vòng đời hướng dẫn:
  - Cơ chế đặt lịch tự động, chống trùng lịch dựa trên mô hình Calendly (quản lý slot thời gian và capacity).
  - Quản lý tập trung tài liệu, đề tài và thành viên nhóm.
  - Ngân hàng câu hỏi và tài liệu tham khảo phân loại theo đề tài do Quản trị viên quản lý.
  - Hỗ trợ trong phiên họp: ghi nhận yêu cầu mới, tự động tổng hợp biên bản họp từ ghi chú/transcript.
  - Báo cáo thống kê tiến độ theo tuần/học kỳ và bảng đánh giá sinh viên chuẩn hóa theo 3 tiêu chí cốt lõi.

- **Success metrics:**  
  - **100%** phiên kiểm tra/họp nhóm được đặt lịch tự động, không xảy ra tình trạng trùng lặp (double-booking) hoặc vượt quá sức chứa (capacity overflow).
  - Giảm **70%** thời gian hoàn tất biên bản họp thông qua tính năng tự động sinh biên bản từ ghi chú.
  - **100%** yêu cầu mới phát sinh trong buổi họp được ghi nhận có mã định danh và truy xuất nguồn gốc.
  - **100%** nhóm sinh viên được đánh giá đầy đủ 3 chiều với nhận xét lưu vết trước khi kết thúc học kỳ.

- **Recommended direction:**  
  Xây dựng ứng dụng Web nền tảng hiện đại, phân quyền theo vai trò (RBAC), sử dụng kiến trúc RESTful API, tích hợp cơ chế khóa đồng thời (concurrency control) cho việc đặt slot theo mô hình Calendly, và tích hợp bộ xử lý văn bản tự động sinh biên bản họp.

---

## 2. Scope

### In scope (v1)

- **Quản lý lịch đánh giá & kiểm tra (Assessment Scheduling):**  
  - Giảng viên/Admin cấu hình khung thời gian (slot: ngày, giờ, thời lượng, địa điểm/link họp, sức chứa nhóm).
  - Nhóm sinh viên tra cứu và đặt slot trống; hệ thống tự động khóa khi đủ số lượng, chống đặt trùng.
  - Gửi thông báo xác nhận và nhắc hẹn tự động qua email/in-app.
- **Quản lý nhóm & tài liệu tiến độ (Group & Artifacts Management):**  
  - Quản lý danh sách thành viên, vai trò (Trưởng nhóm - Group Leader, thành viên - Member), đề tài được giao.
  - Nộp tài liệu, artifact tiến độ theo từng mốc kiểm tra.
- **Ngân hàng câu hỏi & tài liệu đề tài (Topic Question Bank & Resources):**  
  - Admin tạo và quản lý kho câu hỏi, tài liệu hướng dẫn phân loại theo đề tài.
  - Giảng viên tra cứu câu hỏi mẫu khi đánh giá nhóm.
- **Hỗ trợ trong buổi họp & biên bản cuộc họp (In-Meeting Support & Minutes):**  
  - Ghi nhận và phân loại các yêu cầu mới phát sinh trong buổi làm việc.
  - Tự động sinh biên bản họp (Meeting Minutes) từ ghi chú/transcript dạng text; cho phép Trưởng nhóm và Giảng viên rà soát, chỉnh sửa và xác nhận (sign-off).
- **Đánh giá đa chiều (3-Dimensional Evaluation):**  
  - Giảng viên chấm điểm và nhận xét nhóm theo 3 tiêu chí: *Project Topic Fit* (Độ phù hợp đề tài), *Product Quality* (Chất lượng sản phẩm), *Communication* (Kỹ năng giao tiếp & làm việc nhóm).
- **Thống kê & Báo cáo (Analytics & Reporting):**  
  - Dashboard tổng hợp số phiên đã tổ chức, tỷ lệ tham gia, số yêu cầu phát sinh/đã giải quyết, biểu đồ xu hướng tiến độ theo tuần và cả kỳ học.

### Out of scope (v1)

- Nhận diện và chuyển đổi giọng nói trực tiếp thành văn bản thời gian thực (Live Audio Speech-to-Text). Biên bản v1 được tạo từ ghi chú dạng text hoặc transcript được tải lên.
- Tích hợp phòng họp video trực tiếp (Video Call Hosting). Hệ thống chỉ lưu trữ và điều phối link phòng họp (Google Meet, Zoom, MS Teams).
- Quản lý lịch sử đa khóa học / dữ liệu nhiều năm (multi-year archiving). Phiên bản v1 tập trung tối ưu cho một chu kỳ học kỳ hiện hành.
- Đồng bộ điểm 2 chiều tự động vào hệ thống quản lý đào tạo chung của trường (sẽ cung cấp tính năng Export CSV/Excel để nhập thủ công).

---

## 3. Stakeholders and Users

| ID | Role | Responsibility | Decision/approval |
|---|---|---|---|
| ST-001 | Student (Group Member) | Tham gia các buổi họp, xem câu hỏi/tài liệu tham khảo, theo dõi tiến độ và đánh giá của nhóm. | Xác nhận thông tin cá nhân. |
| ST-002 | Group Leader | Đại diện nhóm đặt slot hẹn, nộp tài liệu/deliverables, ghi chép ghi chú và xác nhận biên bản cuộc họp. | Ký duyệt biên bản họp phía sinh viên. |
| ST-003 | Instructor / Supervisor | Thiết lập slot hẹn khả dụng, chủ trì họp/đánh giá, ghi nhận yêu cầu mới, chấm điểm 3 tiêu chí và phê duyệt biên bản họp. | Quyết định điểm số và phê duyệt cuối biên bản họp. |
| ST-004 | Academic Admin | Quản lý danh mục đề tài, ngân hàng câu hỏi, cấu hình quy tắc lịch toàn khóa và tài khoản người dùng. | Duyệt danh mục đề tài và chính sách phân bổ slot. |
| ST-005 | Academic Board / Dept Head | Giám sát tiến độ chung toàn khóa học qua các báo cáo thống kê định kỳ hàng tuần và cuối kỳ. | Nghiệm thu kết quả đào tạo môn học. |

---

## 4. Assumptions, Constraints and Open Questions

| ID | Type | Statement | Owner | Status |
|---|---|---|---|---|
| A-001 | Assumption | Đơn vị sức chứa (capacity) của mỗi slot được tính theo **số lượng nhóm** (mặc định: 1 nhóm/slot; có thể cấu hình tối đa $N$ nhóm/slot cho buổi bảo vệ chung). | Business Analyst | Confirmed |
| A-002 | Assumption | Giảng viên có quyền tự do tạo và chỉnh sửa slot của cá nhân; Admin có quyền tạo các slot đánh giá tập trung cho toàn khoa. | Business Analyst | Confirmed |
| A-003 | Assumption | Tính năng tự động tạo biên bản họp hoạt động dựa trên văn bản đầu vào (ghi chú text của sinh viên hoặc transcript tải lên), không xử lý file âm thanh thô. | Technical Lead | Confirmed |
| A-004 | Assumption | Thang điểm đánh giá là 100 điểm, chia làm 3 tiêu chí (Topic Fit: 40%, Product Quality: 40%, Communication: 20%) và có thể điều chỉnh trọng số ở cấp môn học. | Product Owner | Unconfirmed |
| A-005 | Assumption | Phiên bản v1 hỗ trợ xác thực tài khoản cục bộ kết hợp đăng nhập Google SSO thông qua email giáo dục trường (`@fpt.edu.vn`). | Technical Lead | Confirmed |
| A-006 | Assumption | Hệ thống tự quản lý lịch nội bộ và hỗ trợ xuất file `.ics` / Google Calendar Link, chưa cần tích hợp API 2 chiều với Calendly thương mại. | Technical Lead | Confirmed |
| C-001 | Constraint | Hệ thống phải tuân thủ khung thời gian học kỳ (15 tuần) và lịch thi của nhà trường. | Academic Admin | Confirmed |
| C-002 | Constraint | Giao diện web phải hiển thị chuẩn responsive trên cả Desktop và Mobile browser. | UI/UX Lead | Confirmed |
| OQ-001 | Open Question | Tài liệu và biên bản họp của mỗi nhóm có được hiển thị công khai cho các nhóm khác học hỏi không, hay hoàn toàn riêng tư giữa nhóm và giảng viên? | Academic Board | Open |
| OQ-002 | Open Question | Hệ thống tài khoản sinh viên/giảng viên sẽ được import hàng loạt bằng file Excel/CSV đầu kỳ hay đồng bộ qua API của phòng Đào tạo? | IT Department | Open |
| D-001 | Decision | Áp dụng mô hình trải nghiệm người dùng (UX pattern) của Calendly làm chuẩn cho luồng chọn và giữ slot đánh giá. | Team Consensus | Decided |

---

## 5. Process Analysis

| Step | Current state (Thực trạng) | Future state (Quy trình tương lai) | Actor/system | Rule or exception |
|---|---|---|---|---|
| 1. Thiết lập Slot | Giảng viên nhắn tin qua Zalo/Email hẹn giờ lẻ tẻ; dễ trùng lịch dạy hoặc lịch cá nhân. | Giảng viên/Admin tạo các slot thời gian chuẩn (ngày, giờ bắt đầu/kết thúc, thời lượng, sức chứa số nhóm, link/phòng). | Instructor / Admin | **Rule:** Không cho phép tạo slot trùng giờ với slot đã có của cùng giảng viên. |
| 2. Đặt lịch kiểm tra | Sinh viên nhắn tin qua lại nhiều vòng để chốt giờ; hay bị tranh chấp khung giờ đẹp. | Trưởng nhóm xem bảng lịch khả dụng (Calendly style), chọn slot trống và nhấn Đặt lịch. Hệ thống tự trừ capacity và khóa slot khi đủ. | Group Leader / System | **Rule:** Mỗi nhóm chỉ được giữ 1 slot đang hoạt động trong cùng một đợt kiểm tra.<br>**Exception:** Nếu slot vừa hết chỗ khi đang bấm đặt, báo lỗi kịp thời và gợi ý slot khác. |
| 3. Chuẩn bị tài liệu & Câu hỏi | Sinh viên gửi tài liệu qua Drive/Email; Giảng viên tìm tài liệu và câu hỏi hỏi đáp tự phát. | Nhóm tải artifact trực tiếp vào slot hẹn. Giảng viên mở kho câu hỏi theo chủ đề đề tài để chuẩn bị nội dung phản biện. | Student / Instructor | **Rule:** Khóa nộp tài liệu trước giờ bắt đầu họp 2 tiếng (hoặc theo cấu hình). |
| 4. Tiến hành họp & Ghi nhận | Ghi chú bằng sổ tay cá nhân; yêu cầu giảng viên giao thêm dễ bị quên, không có căn cứ đối chiếu. | Trong buổi làm việc, hệ thống hỗ trợ ghi nhận các câu hỏi và logging ngay các yêu cầu mới phát sinh (New Requirements) gắn kèm độ ưu tiên. | Meeting Attendees / System | **Rule:** Yêu cầu mới bắt buộc có tiêu đề, mô tả tóm tắt và người chịu trách nhiệm. |
| 5. Lập biên bản họp | Trưởng nhóm tự soạn biên bản sau vài ngày, nội dung sơ sài, giảng viên không kiểm chứng lại. | Hệ thống tự động tổng hợp bản thảo biên bản họp từ ghi chú và danh sách yêu cầu mới. Trưởng nhóm rà soát, Giảng viên duyệt xác nhận điện tử. | Group Leader / Instructor / System | **Rule:** Biên bản phải được cả 2 bên xác nhận trong vòng 48h sau buổi họp để chính thức chuyển trạng thái `Approved`. |
| 6. Đánh giá kết quả | Đánh giá cảm tính, ghi điểm vào sổ tay hoặc file Excel riêng của giảng viên. | Giảng viên chấm điểm trực tiếp trên hệ thống theo 3 tiêu chí: Topic fit, Product quality, Communication kèm nhận xét chi tiết. | Instructor | **Rule:** Điểm từng phần từ 0 - 100, hệ thống tự động tính điểm trung bình có trọng số. |
| 7. Thống kê & Báo cáo | Cán bộ quản lý phải liên hệ từng giảng viên để xin số liệu tiến độ các nhóm. | Dashboard tự động tổng hợp số phiên đã họp, tiến độ giải quyết yêu cầu và tình trạng sinh viên theo thời gian thực (real-time). | Admin / Dept Head / System | **Rule:** Dữ liệu thống kê được cập nhật tức thì sau mỗi biên bản họp được duyệt. |

---

## 6. Requirements

| ID | Type | Requirement | Priority | Source | Status |
|---|---|---|---|---|---|
| BR-001 | Business | Tự động hóa và loại bỏ 100% xung đột lịch trong việc tổ chức các buổi gặp và đánh giá sinh viên. | Must | `intent.md` §1, §2 | Approved |
| BR-002 | Business | Tập trung hóa hồ sơ nhóm, tài liệu tiến độ và ngân hàng câu hỏi đề tài trên một nền tảng duy nhất. | Must | `intent.md` §1, §2 | Approved |
| BR-003 | Business | Nâng cao tính minh bạch và lưu vết trong các buổi làm việc qua ghi nhận yêu cầu mới và biên bản họp tự động. | Must | `intent.md` §1, §4 | Approved |
| BR-004 | Business | Chuẩn hóa quy trình đánh giá kết quả theo 3 tiêu chí cụ thể, cung cấp bằng chứng rõ ràng cho kiểm định chất lượng. | Must | `intent.md` §4 | Approved |
| BR-005 | Business | Cung cấp bức tranh toàn cảnh về tiến độ học tập và rủi ro chậm trễ theo tuần và theo học kỳ. | Must | `intent.md` §2, §4 | Approved |
| FR-001 | Functional | Giảng viên/Admin có thể tạo, chỉnh sửa, hủy các khung giờ đánh giá (Date, Time, Duration, Capacity, Location/Link). | Must | BR-001, `intent.md` §3 | Ready |
| FR-002 | Functional | Nhóm sinh viên có thể xem lịch trống và đặt chỗ theo mô hình Calendly; hệ thống tự động ngăn chặn đặt trùng hoặc quá tải. | Must | BR-001, `intent.md` §3 | Ready |
| FR-003 | Functional | Hệ thống tự động gửi thông báo xác nhận và email/in-app nhắc lịch trước buổi họp 24h và 2h. | Should | BR-001, `intent.md` §3 | Ready |
| FR-004 | Functional | Nhóm sinh viên có thể nộp và cập nhật tài liệu tiến độ (báo cáo, slide, source code link) theo từng đợt đánh giá. | Must | BR-002, `intent.md` §2 | Ready |
| FR-005 | Functional | Admin có thể quản lý danh mục đề tài, ngân hàng câu hỏi gợi ý và tài liệu tham khảo theo chuyên đề. | Should | BR-002, `intent.md` §2 | Ready |
| FR-006 | Functional | Người tham gia có thể ghi nhận trực tiếp các yêu cầu mới phát sinh trong buổi họp kèm phân loại ưu tiên. | Must | BR-003, `intent.md` §4 | Ready |
| FR-007 | Functional | Hệ thống tự động tạo bản nháp Biên bản họp từ văn bản ghi chú; cho phép chỉnh sửa và ký duyệt (sign-off) 2 phía. | Must | BR-003, `intent.md` §4 | Ready |
| FR-008 | Functional | Giảng viên có thể chấm điểm và phản hồi nhóm theo 3 tiêu chí: Topic fit, Product quality, Communication. | Must | BR-004, `intent.md` §4 | Ready |
| FR-009 | Functional | Hệ thống hiển thị Dashboard thống kê tiến độ theo tuần và kỳ (số buổi họp, tỷ lệ tham gia, yêu cầu đã chốt). | Should | BR-005, `intent.md` §4 | Ready |
| FR-010 | Functional | Quản lý người dùng, phân quyền RBAC (Student, Group Leader, Instructor, Admin) và xác thực tài khoản SSO. | Must | `intent.md` §5 | Ready |

---

## 7. Use Cases and User Stories

### 7.1. Use Cases

#### UC-001: Quản lý và công bố Slot đánh giá (Publish Assessment Slots)
- **Actor:** Instructor / Admin
- **Trigger:** Giảng viên muốn thiết lập thời gian rảnh để gặp các nhóm sinh viên trong tuần.
- **Preconditions:** Giảng viên đã đăng nhập thành công vào hệ thống.
- **Main flow:**
  1. Giảng viên vào mục Quản lý lịch $\rightarrow$ Chọn "Tạo khung giờ mới".
  2. Nhập các thông số: Ngày, khung giờ (từ... đến...), thời lượng mỗi ca (phút), sức chứa (số nhóm/ca), hình thức (Offline kèm phòng học hoặc Online kèm link Google Meet/Zoom).
  3. Giảng viên chọn "Xem trước lịch" $\rightarrow$ Hệ thống kiểm tra trùng lặp với các slot hiện có.
  4. Giảng viên nhấn "Công bố" (Publish).
  5. Hệ thống lưu slot với trạng thái `Available` và hiển thị trên lịch chung của nhóm.
- **Alternate/error flows:**
  - *4a. Phát hiện trùng lặp:* Hệ thống cảnh báo khung giờ bị chồng lấn với một slot khác của giảng viên $\rightarrow$ Yêu cầu chọn khung giờ khác.
- **Postconditions:** Khung giờ khả dụng được công bố cho sinh viên lựa chọn.

#### UC-002: Đặt lịch kiểm tra (Book Assessment Slot)
- **Actor:** Group Leader
- **Trigger:** Trưởng nhóm cần đặt lịch họp/bảo vệ định kỳ theo yêu cầu môn học.
- **Preconditions:** Nhóm chưa đặt slot nào trong cùng đợt kiểm tra hiện hành; slot muốn chọn còn capacity $> 0$.
- **Main flow:**
  1. Trưởng nhóm truy cập giao diện "Lịch hẹn giảng viên".
  2. Hệ thống hiển thị trực quan các ngày và slot còn trống (Calendly grid view).
  3. Trưởng nhóm chọn một slot còn chỗ, nhập ghi chú chuẩn bị của nhóm (mục tiêu buổi họp).
  4. Trưởng nhóm nhấn "Xác nhận đặt lịch".
  5. Hệ thống áp dụng cơ chế khóa giao dịch, kiểm tra lại capacity, trừ số chỗ trống của slot và tạo bản ghi Booking.
  6. Hệ thống gửi thông báo xác nhận đến toàn bộ thành viên nhóm và giảng viên hướng dẫn.
- **Alternate/error flows:**
  - *5a. Trùng nhóm đã đặt:* Hệ thống báo nhóm đã có một lịch hẹn đang chờ xử lý $\rightarrow$ Hủy thao tác.
  - *5b. Slot vừa bị nhóm khác đặt hết chỗ (Race condition):* Hệ thống báo slot vừa hết chỗ $\rightarrow$ Tải lại lịch và gợi ý slot kế tiếp.
- **Postconditions:** Lịch hẹn được xác nhận, trạng thái slot chuyển thành `Booked` (nếu đã đủ capacity).

#### UC-003: Ghi nhận yêu cầu mới & Tạo biên bản họp tự động (Meeting Minutes Generation)
- **Actor:** Group Leader, Instructor
- **Trigger:** Buổi họp bắt đầu hoặc kết thúc, các bên cần tổng kết nội dung làm việc.
- **Preconditions:** Buổi họp đang hoặc đã diễn ra theo đúng lịch Booking.
- **Main flow:**
  1. Trong giao diện phiên họp, người tham gia nhập các ghi chú làm việc hoặc tải lên file văn bản ghi chép buổi họp.
  2. Nếu có yêu cầu mới được giao, người dùng bấm "Thêm yêu cầu mới" (nhập tiêu đề, mô tả, hạn chót).
  3. Kết thúc buổi họp, Trưởng nhóm bấm "Tạo biên bản tự động".
  4. Hệ thống phân tích nội dung ghi chú, cấu trúc hóa thành biên bản chuẩn (Mục tiêu, Nội dung trao đổi, Yêu cầu phát sinh, Kết luận).
  5. Trưởng nhóm kiểm tra, chỉnh sửa nhẹ và bấm "Gửi xác nhận".
  6. Giảng viên nhận thông báo, kiểm tra nội dung và nhấn "Phê duyệt biên bản" (Approve).
- **Alternate/error flows:**
  - *6a. Giảng viên yêu cầu sửa đổi:* Giảng viên ghi chú phản hồi $\rightarrow$ Biên bản trả về trạng thái `Needs Revision` để nhóm cập nhật.
- **Postconditions:** Biên bản cuộc họp có trạng thái `Approved`, lưu vết vĩnh viễn vào hồ sơ nhóm.

#### UC-004: Chấm điểm và Đánh giá nhóm (Evaluate Student Group)
- **Actor:** Instructor
- **Trigger:** Sau khi hoàn thành buổi đánh giá hoặc định kỳ cuối học kỳ.
- **Preconditions:** Nhóm đã hoàn thành buổi làm việc và nộp đủ tài liệu liên quan.
- **Main flow:**
  1. Giảng viên chọn nhóm cần đánh giá $\rightarrow$ Bấm "Tạo phiếu đánh giá".
  2. Hệ thống hiển thị form chấm điểm 3 tiêu chí:
     - Project Topic Fit (0 - 100).
     - Product Quality (0 - 100).
     - Communication & Teamwork (0 - 100).
  3. Giảng viên nhập điểm và phần nhận xét chi tiết/gợi ý cải thiện cho từng tiêu chí.
  4. Hệ thống tự động tính điểm tổng kết có trọng số.
  5. Giảng viên nhấn "Lưu và Công bố kết quả".
  6. Hệ thống gửi thông báo kết quả đến các thành viên trong nhóm.
- **Postconditions:** Bản ghi đánh giá được lưu vết, cập nhật vào bảng điểm và dashboard thống kê.

---

### 7.2. User Stories

#### US-001: Tạo khung giờ đánh giá có kiểm soát capacity
- **Story:** Là một Giảng viên, tôi muốn cấu hình các khung giờ rảnh kèm theo sức chứa nhóm, để sinh viên có thể tự đặt lịch mà không làm quá tải ca làm việc của tôi.
- **Acceptance criteria:**
  - **Given** Giảng viên đang ở trang quản lý lịch trống,
  - **When** Giảng viên nhập ngày, giờ từ 08:00 đến 10:00, thời lượng 30 phút/ca, sức chứa 1 nhóm/ca và bấm "Tạo",
  - **Then** Hệ thống tự động sinh 4 slot riêng biệt (08:00-08:30, 08:30-09:00, 09:00-09:30, 09:30-10:00) với capacity = 1 mỗi slot và hiển thị ở trạng thái `Available`.

#### US-002: Đặt lịch hẹn không bị trùng (Conflict-free Booking)
- **Story:** Là một Trưởng nhóm, tôi muốn xem các slot khả dụng và đặt lịch trực quan như Calendly, để nhóm tôi chọn được giờ phù hợp nhất mà không cần email qua lại.
- **Acceptance criteria:**
  - **Given** Nhóm chưa có lịch hẹn nào đang hoạt động trong tuần,
  - **When** Trưởng nhóm chọn một slot còn trống và bấm "Xác nhận đặt lịch",
  - **Then** Hệ thống ghi nhận lịch thành công, hiển thị thông báo xác nhận và gửi email nhắc lịch cho tất cả thành viên trong nhóm.

#### US-003: Chống quá tải và xung đột đặt lịch đồng thời
- **Story:** Là Hệ thống, tôi muốn ngăn chặn hai nhóm cùng đặt vào một slot chỉ còn 1 chỗ tại cùng một thời điểm, để đảm bảo không bị double-booking.
- **Acceptance criteria:**
  - **Given** Slot S-101 chỉ còn 1 chỗ trống duy nhất,
  - **When** Nhóm A và Nhóm B đồng thời gửi yêu cầu đặt slot S-101 trong cùng 1 giây,
  - **Then** Nhóm nào gửi tới trước sẽ nhận thông báo đặt thành công, nhóm còn lại nhận thông báo lỗi "Slot vừa hết chỗ" và slot chuyển sang trạng thái `Full`.

#### US-004: Ghi nhận yêu cầu phát sinh trong phiên họp
- **Story:** Là một Người tham gia họp, tôi muốn ghi nhận nhanh các yêu cầu hoặc sửa đổi mới phát sinh, để nhóm có căn cứ đối chiếu và không bị sót việc.
- **Acceptance criteria:**
  - **Given** Phiên họp đang diễn ra,
  - **When** Người dùng nhập tiêu đề yêu cầu, mô tả, hạn chót và nhấn "Lưu yêu cầu",
  - **Then** Yêu cầu mới xuất hiện ngay trong danh sách `Requirement Logs` của phiên họp với mã định danh ổn định (ví dụ: `REQ-012`) và trạng thái `Open`.

#### US-005: Tự động tổng hợp biên bản họp từ ghi chú
- **Story:** Là một Trưởng nhóm, tôi muốn hệ thống tự động sinh biên bản họp từ ghi chú text, để tiết kiệm thời gian soạn thảo biên bản sau mỗi buổi họp.
- **Acceptance criteria:**
  - **Given** Trưởng nhóm đã nhập các gạch đầu dòng ghi chú và danh sách yêu cầu mới trong buổi làm việc,
  - **When** Trưởng nhóm nhấn "Tạo biên bản tự động",
  - **Then** Hệ thống trả về bản nháp Meeting Minutes đầy đủ các mục (Thời gian, Tham dự, Nội dung thảo luận, Yêu cầu phát sinh, Action items) trong vòng dưới 3 giây.

#### US-006: Duyệt biên bản họp hai phía
- **Story:** Là một Giảng viên, tôi muốn rà soát và bấm duyệt biên bản cuộc họp điện tử, để xác nhận nội dung thống nhất chính xác trước khi lưu trữ chính thức.
- **Acceptance criteria:**
  - **Given** Biên bản họp đang ở trạng thái `Submitted by Student`,
  - **When** Giảng viên nhấn "Phê duyệt",
  - **Then** Trạng thái biên bản chuyển thành `Approved`, khóa chỉnh sửa và hệ thống gửi thông báo hoàn tất đến nhóm sinh viên.

#### US-007: Đánh giá nhóm theo 3 tiêu chí
- **Story:** Là một Giảng viên, tôi muốn nhập điểm và nhận xét riêng biệt cho 3 tiêu chí (Topic Fit, Product Quality, Communication), để sinh viên biết rõ điểm mạnh và điểm cần cải thiện.
- **Acceptance criteria:**
  - **Given** Giảng viên mở phiếu đánh giá của nhóm,
  - **When** Giảng viên nhập điểm 85 (Topic fit), 90 (Product quality), 80 (Communication) kèm lời nhận xét và bấm "Lưu",
  - **Then** Hệ thống tính điểm tổng kết có trọng số, lưu bản ghi đánh giá và cập nhật vào báo cáo học kỳ của nhóm.

#### US-008: Báo cáo thống kê tiến độ hàng tuần
- **Story:** Là một Quản trị viên/Chủ nhiệm bộ môn, tôi muốn xem bảng thống kê số phiên họp đã diễn ra và số yêu cầu tồn đọng trong tuần, để nắm bắt tình hình và can thiệp kịp thời các nhóm chậm tiến độ.
- **Acceptance criteria:**
  - **Given** Người dùng có quyền Admin truy cập trang Analytics,
  - **When** Chọn xem báo cáo tuần hiện tại,
  - **Then** Hệ thống hiển thị biểu đồ tỷ lệ tham gia họp, danh sách nhóm chưa đặt lịch và số lượng yêu cầu mới đã giải quyết/chưa giải quyết.

---

## 8. Data Model

| Entity | Key fields | Relationships | Lifecycle | Classification | Owner |
|---|---|---|---|---|---|
| **User** | `id` (PK), `email`, `fullName`, `role` (Admin/Instructor/Leader/Student), `avatarUrl`, `status` | 1-n with `GroupMember`, `ScheduleSlot`, `EvaluationRecord` | Active $\rightarrow$ Suspended $\rightarrow$ Inactive | Internal Confidential | System Admin |
| **StudentGroup** | `id` (PK), `groupCode`, `topicId` (FK), `supervisorId` (FK), `semester`, `status` | 1-n with `GroupMember`, `Booking`, `RequirementLog`, `EvaluationRecord` | Formed $\rightarrow$ Active $\rightarrow$ Completed $\rightarrow$ Archived | Internal | Academic Admin |
| **GroupMember** | `id` (PK), `groupId` (FK), `userId` (FK), `isLeader` (Boolean), `joinedAt` | n-1 with `StudentGroup`, n-1 with `User` | Active $\rightarrow$ Removed | Internal | Group Leader |
| **Topic** | `id` (PK), `topicCode`, `title`, `description`, `category`, `adminId` (FK), `isActive` | 1-n with `StudentGroup`, 1-n with `QuestionBankItem` | Draft $\rightarrow$ Published $\rightarrow$ Archived | Public Internal | Academic Admin |
| **QuestionBankItem** | `id` (PK), `topicId` (FK), `category`, `questionText`, `guidanceNotes`, `createdBy` (FK) | n-1 with `Topic` | Draft $\rightarrow$ Active $\rightarrow$ Deprecated | Public Internal | Academic Admin |
| **ScheduleSlot** | `id` (PK), `instructorId` (FK), `startTime`, `endTime`, `durationMinutes`, `capacityGroups`, `bookedCount`, `locationType`, `meetingUrl`, `status` | 1-n with `Booking` | Available $\rightarrow$ Full $\rightarrow$ In Session $\rightarrow$ Completed / Cancelled | Internal | Instructor |
| **Booking** | `id` (PK), `slotId` (FK), `groupId` (FK), `bookingStatus`, `bookedAt`, `notes`, `cancelledAt` | n-1 with `ScheduleSlot`, n-1 with `StudentGroup`, 1-1 with `MeetingSession` | Confirmed $\rightarrow$ Attended $\rightarrow$ Cancelled $\rightarrow$ No-Show | Internal | Group Leader |
| **MeetingSession** | `id` (PK), `bookingId` (FK), `startedAt`, `endedAt`, `rawNotes`, `sessionStatus` | 1-1 with `Booking`, 1-1 with `MeetingMinute`, 1-n with `RequirementLog` | Scheduled $\rightarrow$ In Progress $\rightarrow$ Concluded | Internal | Group Leader / Instructor |
| **RequirementLog** | `id` (PK), `sessionId` (FK), `groupId` (FK), `title`, `description`, `priority` (High/Med/Low), `status`, `assignedTo` | n-1 with `MeetingSession`, n-1 with `StudentGroup` | Open $\rightarrow$ In Progress $\rightarrow$ Resolved $\rightarrow$ Closed | Internal | Instructor / Group |
| **MeetingMinute** | `id` (PK), `sessionId` (FK), `generatedContent`, `finalContent`, `status`, `studentSignedAt`, `instructorSignedAt` | 1-1 with `MeetingSession` | Draft $\rightarrow$ Under Review $\rightarrow$ Approved $\rightarrow$ Rejected | Internal Confidential | Group Leader & Instructor |
| **EvaluationRecord** | `id` (PK), `groupId` (FK), `instructorId` (FK), `topicFitScore`, `productQualityScore`, `communicationScore`, `totalScore`, `feedbackNotes`, `evaluatedAt` | n-1 with `StudentGroup`, n-1 with `User` | Draft $\rightarrow$ Submitted $\rightarrow$ Published | Restricted Confidential | Instructor |
| **ArtifactSubmission** | `id` (PK), `groupId` (FK), `sessionId` (FK), `title`, `fileUrl`, `fileType`, `version`, `submittedAt` | n-1 with `StudentGroup`, n-1 with `MeetingSession` | Submitted $\rightarrow$ Superceded $\rightarrow$ Accepted | Internal | Group Member |

---

## 9. API and Integration Contract

| API/Event ID | Method/path | Purpose | Auth | Request payload (tóm tắt) / Response | Error codes |
|---|---|---|---|---|---|
| **API-001** | `POST /api/v1/slots` | Giảng viên tạo khung giờ rảnh mới | Bearer (Instructor/Admin) | `{ startTime, endTime, durationMinutes, capacity, locationType, meetingUrl }` $\rightarrow$ `201 Created: { slotId, ... }` | 400 Bad Request, 409 Time Conflict, 401 Unauthorized |
| **API-002** | `GET /api/v1/slots` | Lấy danh sách slot khả dụng | Bearer (All Roles) | Query params: `instructorId, fromDate, toDate, status=Available` $\rightarrow$ `200 OK: [ { slotId, startTime, capacity, bookedCount }, ... ]` | 400 Bad Request |
| **API-003** | `POST /api/v1/slots/{id}/book` | Trưởng nhóm đặt chỗ vào slot | Bearer (Group Leader) | `{ groupId, notes }` $\rightarrow$ `200 OK: { bookingId, status: "Confirmed" }` | 400 Already Booked, 409 Slot Full, 404 Not Found |
| **API-004** | `DELETE /api/v1/bookings/{id}` | Hủy lịch hẹn đã đặt | Bearer (Leader/Instructor) | `{ reason }` $\rightarrow$ `200 OK: { message: "Cancelled" }` | 400 Late Cancellation (<2h), 403 Forbidden |
| **API-005** | `POST /api/v1/groups/{id}/artifacts` | Nộp tài liệu tiến độ | Bearer (Group Member) | `multipart/form-data: { file, title, sessionId }` $\rightarrow$ `201 Created: { artifactId, fileUrl }` | 413 File Too Large, 400 Invalid Type |
| **API-006** | `GET /api/v1/topics/{id}/questions` | Lấy ngân hàng câu hỏi đề tài | Bearer (All Roles) | Query params: `category` $\rightarrow$ `200 OK: [ { questionId, text, guidanceNotes } ]` | 404 Topic Not Found |
| **API-007** | `POST /api/v1/meetings/{id}/requirements` | Ghi nhận yêu cầu mới phát sinh | Bearer (Leader/Instructor) | `{ title, description, priority }` $\rightarrow$ `201 Created: { reqId, status: "Open" }` | 400 Missing Fields, 404 Session Not Found |
| **API-008** | `POST /api/v1/meetings/{id}/minutes/generate` | Tự động sinh biên bản họp từ ghi chú | Bearer (Leader/Instructor) | `{ rawNotes, notesTranscript }` $\rightarrow$ `200 OK: { minuteDraft, generatedSections }` | 400 Empty Notes, 500 AI Service Error |
| **API-009** | `PUT /api/v1/meetings/{id}/minutes/sign` | Ký duyệt biên bản cuộc họp | Bearer (Leader/Instructor) | `{ approvalDecision: "Approve" \| "Reject", comments }` $\rightarrow$ `200 OK: { minuteStatus }` | 403 Invalid Role, 409 Conflict State |
| **API-010** | `POST /api/v1/groups/{id}/evaluations` | Chấm điểm nhóm theo 3 tiêu chí | Bearer (Instructor) | `{ topicFitScore, productQualityScore, communicationScore, feedback }` $\rightarrow$ `201 Created: { evalId, totalScore }` | 400 Invalid Score (0-100), 403 Unauthorized |
| **API-011** | `GET /api/v1/reports/summary` | Thống kê số liệu tiến độ định kỳ | Bearer (Admin/Dept Head) | Query params: `semester, weekNumber` $\rightarrow$ `200 OK: { sessionsHeld, attendanceRate, openReqs, closedReqs }` | 403 Forbidden |

---

## 10. Non-Functional Requirements

| ID | Category | Target | Measurement | Priority | Owner |
|---|---|---|---|---|---|
| **NFR-001** | **Performance** | Thời gian phản hồi API $\le 500\text{ ms}$ với các truy vấn tra cứu lịch; $\le 1.5\text{ s}$ khi xử lý đặt chỗ phức tạp. | Đo bằng APM (Application Performance Monitoring) ở phân vị 95th percentile (P95). | Must | Technical Lead |
| **NFR-002** | **Concurrency** | Ngăn ngừa 100% tình trạng over-booking khi có $\ge 50$ nhóm đồng thời đặt vào một slot cuối cùng. | Sử dụng Database Row Locking / Distributed Lock (Redis Lock), kiểm thử tải bằng k6/JMeter. | Must | Backend Lead |
| **NFR-003** | **Availability** | Hệ thống đạt độ sẵn sàng tối thiểu $99.5\%$ trong suốt 15 tuần của kỳ học. | Tính tổng thời gian hoạt động (uptime) không tính lịch bảo trì đã thông báo trước. | Must | DevOps Lead |
| **NFR-004** | **Security** | Mã hóa đường truyền HTTPS (TLS 1.3), mã hóa dữ liệu lưu trữ (AES-256), phân quyền truy cập nghiêm ngặt theo RBAC. | Quét lỗ hổng tự động với OWASP ZAP và kiểm tra định kỳ (Pen-test checklist). | Must | Security Engineer |
| **NFR-005** | **Usability** | Giao diện trực quan mô phỏng Calendly, thời gian hoàn thành đặt 1 lịch hẹn dưới 3 cú click chuột. | Thử nghiệm người dùng (Usability Testing) với nhóm mẫu sinh viên, tỷ lệ tác vụ thành công $\ge 95\%$. | Should | UI/UX Designer |
| **NFR-006** | **Auditability** | Lưu vết 100% các hành động: hủy lịch hẹn, thay đổi điểm số, sửa đổi biên bản cuộc họp kèm Timestamp và User ID. | Truy vấn bảng Audit Log, đối chiếu chéo khi có khiếu nại. | Must | Technical Lead |

---

## 11. Security, Privacy and Compliance

- **Authentication and authorization:**  
  - Sử dụng chuẩn JWT (JSON Web Token) kết hợp OAuth2 Google Login (chỉ cho phép domain trường học `@fpt.edu.vn`).
  - Kiểm soát truy cập dựa trên vai trò (RBAC):
    - *Student:* Chỉ xem tài liệu, lịch của nhóm mình; xem câu hỏi đề tài.
    - *Group Leader:* Toàn bộ quyền của Student + quyền Đặt/Hủy slot, nộp tài liệu, ký biên bản họp.
    - *Instructor:* Quản lý slot của mình, chấm điểm nhóm, phê duyệt biên bản họp.
    - *Admin:* Toàn quyền quản trị đề tài, ngân hàng câu hỏi, slot toàn khoa, tài khoản người dùng và xem báo cáo.
- **Sensitive data and classification:**  
  - *Restricted:* Điểm đánh giá, nhận xét bảo mật của giảng viên (chỉ giảng viên chấm và nhóm được xem sau khi công bố).
  - *Internal:* Biên bản họp, tài liệu nộp của nhóm, thông tin thành viên (nội bộ trong trường).
  - *Public Internal:* Danh mục đề tài, ngân hàng câu hỏi gợi ý.
- **Audit and traceability:**  
  - Mọi thao tác thay đổi trạng thái Booking, cập nhật điểm đánh giá, xác nhận biên bản đều được ghi vào bảng `SystemAuditTrail` bất biến (Immutable).
- **Retention/deletion:**  
  - Dữ liệu một học kỳ được lưu trữ active trong 6 tháng; lưu trữ dạng archive tối thiểu 1 năm (3 học kỳ kế tiếp) phục vụ phúc khảo và thanh tra đào tạo trước khi xem xét purge.
- **Applicable policy or regulation:**  
  - Tuân thủ Quy chế Đào tạo đại học và Quy định bảo mật dữ liệu thông tin sinh viên của Nhà trường.

---

## 12. Delivery Plan and Dependencies

| Increment | Scope | Dependency | Exit criteria | Risk |
|---|---|---|---|---|
| **Sprint 1: Nền tảng & Quản trị** | Cấu hình User, Phân quyền RBAC, Quản lý Nhóm sinh viên, Danh mục Đề tài & Ngân hàng câu hỏi. | Chuẩn bị xong Database schema & cơ chế Authentication. | Tạo và phân quyền được người dùng; nhập và tra cứu được đề tài, câu hỏi. | Trễ hạn chốt danh sách nhóm sinh viên từ phía phòng Đào tạo. |
| **Sprint 2: Lịch & Đặt chỗ (Calendly Engine)** | Quản lý Slot của Giảng viên, Giao diện xem lịch trực quan, Động cơ đặt chỗ chống trùng/quá tải, Gửi email thông báo. | Sprint 1 hoàn thành. | Đặt lịch thành công, kiểm thử concurrency 50 users cùng lúc không bị race condition. | Xung đột tải đồng thời cao tại thời điểm mở cổng đăng ký lịch. |
| **Sprint 3: Hồ sơ nhóm & Nộp Artifacts** | Giao diện nộp bài của sinh viên, xem trước tài liệu, quản trị phiên họp (Pre-meeting artifacts). | Sprint 2 hoàn thành. | Sinh viên tải tài liệu đúng hạn, giảng viên xem được tài liệu đính kèm trước phiên họp. | File dung lượng lớn gây quá tải đường truyền/lưu trữ. |
| **Sprint 4: Hỗ trợ họp & Biên bản tự động** | Ghi nhận yêu cầu mới trong buổi làm việc, công cụ sinh tự động bản nháp biên bản họp, luồng duyệt 2 phía. | Sprint 3 hoàn thành. | Biên bản họp được sinh tự động từ văn bản ghi chú và được cả 2 bên ký duyệt thành công. | Chất lượng tóm tắt biên bản tự động chưa đạt kỳ vọng người dùng. |
| **Sprint 5: Đánh giá 3 tiêu chí & Báo cáo** | Form chấm điểm 3 tiêu chí, tính điểm trung bình có trọng số, Dashboard thống kê hàng tuần & toàn kỳ, xuất báo cáo. | Sprint 4 hoàn thành. | Giảng viên hoàn tất đánh giá; Dashboard hiển thị đúng biểu đồ tiến độ và tỷ lệ tham gia. | Giảng viên chậm trễ trong việc cập nhật điểm đánh giá. |

---

## 13. Traceability Matrix

| Business goal | Requirement | Use case / Story | API / Data / NFR | Test evidence |
|---|---|---|---|---|
| **BR-001** (Lịch hẹn tự động, chống xung đột) | FR-001, FR-002, FR-003 | UC-001, UC-002 / US-001, US-002, US-003 | `API-001`, `API-002`, `API-003`<br>`ScheduleSlot`, `Booking`<br>`NFR-001`, `NFR-002` | Test Case TC-SCHED-01 (Tạo slot), TC-SCHED-02 (Đặt slot), TC-SCHED-03 (Kiểm thử tải Concurrency). |
| **BR-002** (Tập trung hóa hồ sơ & tài liệu) | FR-004, FR-005, FR-010 | UC-003 / US-004 | `API-005`, `API-006`<br>`StudentGroup`, `ArtifactSubmission`, `Topic`, `QuestionBankItem` | Test Case TC-DOC-01 (Nộp tài liệu), TC-DOC-02 (Tra cứu câu hỏi đề tài). |
| **BR-003** (Lưu vết yêu cầu & Biên bản tự động) | FR-006, FR-007 | UC-003 / US-004, US-005, US-006 | `API-007`, `API-008`, `API-009`<br>`MeetingSession`, `RequirementLog`, `MeetingMinute` | Test Case TC-MIN-01 (Log yêu cầu mới), TC-MIN-02 (Sinh biên bản từ text), TC-MIN-03 (Ký duyệt 2 phía). |
| **BR-004** (Chuẩn hóa đánh giá 3 tiêu chí) | FR-008 | UC-004 / US-007 | `API-010`<br>`EvaluationRecord`<br>`NFR-004` (Bảo mật điểm) | Test Case TC-EVAL-01 (Nhập điểm 3 tiêu chí), TC-EVAL-02 (Tính điểm trọng số). |
| **BR-005** (Thống kê tiến độ & Báo cáo) | FR-009 | US-008 | `API-011`<br>Dashboard Queries<br>`NFR-001` | Test Case TC-REP-01 (Xuất báo cáo tuần), TC-REP-02 (Biểu đồ tiến độ kỳ). |

---

## 14. Risks and Decisions

| ID | Risk / Decision | Impact | Likelihood | Owner | Mitigation / Status |
|---|---|---|---|---|---|
| **R-001** | **Tranh chấp đặt slot đồng thời (Race Condition):** Nhiều nhóm cùng bấm đặt vào 1 slot duy nhất dẫn đến quá tải capacity. | High | Medium | Backend Lead | **Mitigation:** Sử dụng cơ chế khóa phân tán (Distributed Redis Lock hoặc Database Pessimistic Lock `SELECT ... FOR UPDATE`) khi thực thi transaction đặt slot. |
| **R-002** | **Chất lượng biên bản tự động không đạt:** Ghi chú sơ sài dẫn đến bản thảo biên bản họp thiếu thông tin hoặc sai lệch. | Medium | High | BA / Product Owner | **Mitigation:** Cung cấp template khung ghi chú gợi ý sẵn; bắt buộc Trưởng nhóm và Giảng viên phải rà soát, cho phép chỉnh sửa trước khi ký duyệt chính thức. |
| **R-003** | **Giảng viên không cập nhật slot đúng hạn:** Giảng viên quên mở slot khiến sinh viên không đặt được lịch kiểm tra. | High | Medium | Academic Admin | **Mitigation:** Hệ thống gửi thông báo nhắc lịch tự động trước 3 ngày đến các giảng viên chưa công bố slot cho tuần kế tiếp. |
| **R-004** | **Lệch pha với lịch đột xuất của giảng viên:** Giảng viên bận công tác đột xuất vào giờ đã mở slot. | Medium | Medium | Instructor / BA | **Mitigation:** Cung cấp tính năng "Hủy/Dời slot kèm thông báo khẩn"; hệ thống tự động gửi email cảnh báo và mở quyền ưu tiên đặt slot bù cho các nhóm bị ảnh hưởng. |
| **D-001** | **Quyết định UX/UI:** Sử dụng mô hình visual calendar tương tự Calendly làm chuẩn giao diện cho mô-đun đặt lịch. | Medium | High | UI/UX Lead | **Status:** Decided. Giúp sinh viên và giảng viên dễ làm quen, không cần qua đào tạo phức tạp. |

---

## 15. Quality Review

- **Quality gate:** **Passed Gate 1 (Context Ready) & Gate 2 (Requirement Ready) — Solution Ready for Review (Gate 3)**.
- **Unresolved questions:**
  - OQ-001 (Quyền riêng tư của tài liệu nhóm chéo) $\rightarrow$ Tạm thời thiết lập mặc định: Chỉ nội bộ nhóm và giảng viên xem được.
  - OQ-002 (Phương thức nạp danh sách ban đầu) $\rightarrow$ Tạm thời hỗ trợ Import danh sách sinh viên/nhóm qua file Excel chuẩn hóa ở Sprint 1.
- **Reviewers:** Group 2 (BA Lead, Technical Lead, Product Owner).
- **Approval decision:** **Ready for Review** (Sẵn sàng trình Hội đồng chuyên môn và Giảng viên hướng dẫn phản biện).
