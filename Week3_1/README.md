# 1. 
nullable là một khái niệm dùng để chỉ các kiểu dữ liệu có thể nhận giá trị null (hoặc không có giá trị).  khi một biến hoặc trường dữ liệu được khai báo là nullable, có nghĩa là nó có thể chứa một giá trị hợp lệ (ví dụ như số, chuỗi, đối tượng) hoặc là null

---

# 2
- Nên sử dụng nullable khi bạn biết rằng một giá trị có thể không có mặt tại một thời điểm nào đó, ví dụ như một giá trị không được khởi tạo, hoặc có thể không có giá trị trong một tình huống nào đó

- Không nên sử dụng nullable khi bạn muốn đảm bảo rằng một giá trị luôn luôn có giá trị hợp lệ (không phải null)

---

# 3
- ?: Dùng để khai báo một biến có thể nhận giá trị null

- ?.: An toàn truy cập null. Nếu đối tượng là null, sẽ trả về null thay vì gây lỗi

- ?:: Toán tử Elvis, được sử dụng để cung cấp giá trị thay thế khi giá trị là null

- let: Là một hàm mở rộng trong Kotlin để thực thi mã khi đối tượng không phải là null. Nếu đối tượng là null, mã trong let sẽ không được gọi

- !!: Toán tử !! buộc ép một giá trị nullable thành không-null. Nếu giá trị là null, sẽ gây ra NullPointerException
