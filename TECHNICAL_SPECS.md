# Smart Home App - Thông số kỹ thuật

## Cấu hình Project

### SDK và API Level
- **Minimum SDK**: API 24 (Android 7.0 Nougat)
- **Target SDK**: API 34 (Android 14)
- **Compile SDK**: API 34

### Ngôn ngữ và Build System
- **Ngôn ngữ lập trình**: Java 8
- **Layout**: XML
- **Build System**: Gradle (Groovy DSL)
- **Gradle Version**: 8.2
- **Android Gradle Plugin**: 8.2.0

## Thư viện sử dụng

```gradle
dependencies {
    // AndroidX
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

## Cấu trúc Package

```
com.example.smarthomeapp
├── IntroActivity.java          // Màn hình giới thiệu
├── AuthActivity.java           // Màn hình chọn Log In/Sign In
├── LoginActivity.java          // Màn hình đăng nhập
├── SignUpActivity.java         // Màn hình đăng ký
└── MainActivity.java           // Màn hình chính
```

## Cấu trúc Resources

### Layout Files (XML)
- `activity_intro.xml` - Giao diện màn hình giới thiệu
- `activity_auth.xml` - Giao diện chọn đăng nhập/đăng ký
- `activity_login.xml` - Giao diện đăng nhập
- `activity_signup.xml` - Giao diện đăng ký
- `activity_main.xml` - Giao diện màn hình chính

### Drawable Resources (XML)
- `button_rounded.xml` - Background nút bo tròn
- `white_card_bg.xml` - Background card trắng
- `input_bg.xml` - Background input field
- `tab_button_selector.xml` - Background tab được chọn
- `tab_button_unselected.xml` - Background tab chưa chọn
- `circle_bg.xml` - Background hình tròn cho logo

### Colors
```xml
<color name="primary_blue">#6DA5E8</color>
<color name="white">#FFFFFFFF</color>
<color name="black">#FF000000</color>
<color name="light_gray">#F5F5F5</color>
<color name="text_gray">#666666</color>
```

## Tính năng

### 1. Màn hình Giới thiệu (IntroActivity)
- Hiển thị logo placeholder
- Nút "Get started"
- Chuyển sang AuthActivity khi nhấn nút

### 2. Màn hình Xác thực (AuthActivity)
- 2 tab button: "Log In" và "Sign In"
- Chuyển sang LoginActivity khi chọn "Log In"
- Chuyển sang SignUpActivity khi chọn "Sign In"

### 3. Màn hình Đăng nhập (LoginActivity)
- Input: Username/Email
- Input: Password
- Button: "Log In"
- Validation: Kiểm tra trường rỗng
- Chuyển sang MainActivity khi đăng nhập thành công

### 4. Màn hình Đăng ký (SignUpActivity)
- Input: Username/Email
- Input: Password
- Input: Confirm Password
- Button: "Sign In"
- Validation: Kiểm tra trường rỗng và mật khẩu khớp
- Chuyển sang MainActivity khi đăng ký thành công

### 5. Màn hình Chính (MainActivity)
- Hiển thị thông báo chào mừng
- (Có thể mở rộng thêm tính năng)

## Yêu cầu hệ thống

### Để phát triển
- Android Studio Hedgehog (2023.1.1) trở lên
- JDK 8 trở lên
- Android SDK API 24 - API 34
- Minimum RAM: 8GB
- Recommended RAM: 16GB

### Để chạy ứng dụng
- Android 7.0 (API 24) trở lên
- Minimum RAM: 2GB
- Storage: 50MB

## Build Commands

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Install on Device
```bash
./gradlew installDebug
```

### Clean Build
```bash
./gradlew clean
```

## Permissions

Hiện tại ứng dụng không yêu cầu permissions đặc biệt.

## Tương thích

- Tương thích với Android 7.0 (Nougat) trở lên
- Hỗ trợ cả điện thoại và tablet
- Hỗ trợ cả portrait và landscape mode
- Tương thích với AndroidX

## Ghi chú

- Code được viết hoàn toàn bằng **Java**
- Layout được thiết kế bằng **XML**
- Build configuration sử dụng **Groovy DSL**
- Không sử dụng Kotlin
- Không sử dụng Kotlin DSL cho Gradle
- Tuân thủ Material Design Guidelines
