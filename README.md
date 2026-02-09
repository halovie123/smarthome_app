# Smart Home App - Android Application

Ứng dụng Smart Home với giao diện đăng nhập và đăng ký người dùng.

## Cấu trúc ứng dụng

### Màn hình chính (Activities)

1. **IntroActivity** - Màn hình giới thiệu
   - Hiển thị logo và thông tin giới thiệu app
   - Nút "Get started" để chuyển sang màn hình xác thực

2. **AuthActivity** - Màn hình chọn đăng nhập/đăng ký
   - 2 nút tab: "Log In" và "Sign In"
   - Người dùng chọn tab để chuyển sang màn hình tương ứng

3. **LoginActivity** - Màn hình đăng nhập
   - Input: Username/Email
   - Input: Password
   - Nút "Log In"

4. **SignUpActivity** - Màn hình đăng ký
   - Input: Username/Email
   - Input: Password
   - Input: Confirm Password
   - Nút "Sign In"

5. **MainActivity** - Màn hình chính sau khi đăng nhập thành công

## Luồng hoạt động

```
IntroActivity (Get Started)
    ↓
AuthActivity (Chọn Log In hoặc Sign In)
    ↓                          ↓
LoginActivity            SignUpActivity
    ↓                          ↓
    └──────→ MainActivity ←────┘
```

## Cài đặt và chạy ứng dụng

### Yêu cầu
- Android Studio (phiên bản mới nhất)
- JDK 8 trở lên
- Android SDK API 24 trở lên

### Các bước cài đặt

1. Mở Android Studio
2. Chọn "Open an Existing Project"
3. Chọn thư mục `SmartHomeApp`
4. Đợi Android Studio đồng bộ Gradle
5. Kết nối thiết bị Android hoặc tạo emulator
6. Nhấn "Run" (hoặc Shift + F10)

## Tính năng

- ✅ Màn hình giới thiệu với nút Get Started
- ✅ Giao diện đăng nhập/đăng ký với tab switching
- ✅ Form đăng nhập với email và mật khẩu
- ✅ Form đăng ký với email, mật khẩu và xác nhận mật khẩu
- ✅ Kiểm tra validation cơ bản (trường rỗng, mật khẩu khớp)
- ✅ Chuyển đổi giữa các màn hình
- ✅ Giao diện đẹp mắt với màu xanh (#6DA5E8)

## Tùy chỉnh

### Thay đổi màu sắc
Chỉnh sửa file `res/values/colors.xml`:
```xml
<color name="primary_blue">#6DA5E8</color>
```

### Thêm logo
Thay thế placeholder trong `activity_intro.xml` bằng ImageView với logo của bạn.

## Phát triển tiếp

Các tính năng có thể thêm:
- Tích hợp Firebase Authentication
- Lưu trữ thông tin người dùng
- Quản lý session/token
- Chức năng quên mật khẩu
- Giao diện điều khiển thiết bị smart home

## Lưu ý

- Hiện tại chưa có backend thực, cần tích hợp API để đăng nhập/đăng ký thực sự
- Validation hiện tại chỉ ở mức cơ bản, cần thêm kiểm tra email hợp lệ, độ mạnh mật khẩu, etc.
