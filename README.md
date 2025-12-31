# برنامه چت Client-Server

یک برنامه چت چند کاربره مبتنی بر TCP با معماری Client-Server که به زبان Java پیاده‌سازی شده است.

## نحوه اجرا

### اجرای سرور

```bash
./script/run-server.sh [port]
```

- **پورت پیش‌فرض:** `5555`
- **مثال:**
  ```bash
  ./script/run-server.sh          # 5555
  ./script/run-server.sh 8080     # 8080
  ```

### اجرای کلاینت

```bash
./script/run-client.sh [host] [port]
```

- **میزبان پیش‌فرض:** `127.0.0.1`
- **پورت پیش‌فرض:** `5555`
- **مثال:**
  ```bash
  ./script/run-client.sh                    # 127.0.0.1:5555
  ./script/run-client.sh 192.168.1.100 8080 # اتصال به 192.168.1.100:8080
  ```

## دستورات

تمام دستورات با `/` شروع می‌شوند. هر ورودی که با `/` شروع نشود به عنوان پیام چت ارسال می‌شود.

### احراز هویت

- **`/register <username>`**
  - ثبت نام با نام کاربری جدید
  - مثال: `/register ali`

- **`/login <username>`**
  - ورود با نام کاربری ثبت‌شده
  - مثال: `/login ali`

### مدیریت اتاق ها

- **`/create <roomName>`**
  - ایجاد اتاق جدید
  - مثال: `/create general`

- **`/join <roomName>`**
  - ورود به یک اتاق
  - مثال: `/join general`

- **`/leave`**
  - ترک اتاق و بازگشت به لابی

- **`/rooms`**
  - نمایش لیست تمام اتاق ها

- **`/users`**
  - نمایش لیست کاربران آنلاین در اتاق فعلی

### فایل

- **`/upload <localPath>`**
  - آپلود فایل
  - مثال: `/upload /home/user/document.pdf`

- **`/download <fileId> <savePath>`**
  - دانلود فایل
  - مثال: `/download file123 /home/user/downloads/file.pdf`

### خروجی (Export)

- **`/export last <N> <savePath>`**
  - خروجی گرفتن از آخرین N پیام به فرمت JSON
  - مثال: `/export last 50 /home/user/messages.json`

### دیگر دستورات

- **`/help`**
  - نمایش راهنمای دستورات

- **`/exit`**
  - خروج از برنامه

## ساختار پروژه

```
oop-chatapp/
├── client/
│   ├── src/java/com/ap/chat/
│   │   ├── client/
│   │   │   ├── app/
│   │   │   ├── cli/
│   │   │   ├── command/
│   │   │   │   ├── dispatcher/
│   │   │   │   └── impl/
│   │   │   ├── export/
│   │   │   ├── file/
│   │   │   └── net/
│   │   ├── common/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── protocol/
│   │   │   │   └── payload/
│   │   │   └── util/
│   │   └── server/
│   │       └── src/main/java/com/ap/chat/server/
│   │           ├── app/
│   │           ├── command/
│   │           │   ├── dispatcher/
│   │           │   └── impl/
│   │           ├── error/
│   │           ├── net/
│   │           ├── repository/
│   │           ├── service/
│   │           │   ├── AuthService
│   │           │   ├── RoomService
│   │           │   ├── MessageService
│   │           │   ├── FileStorageService
│   │           │   ├── ExportService
│   │           │   └── BroadcastService
│   │           └── state/
│   └── build/
├── script/
│   ├── run-server.sh
│   └── run-client.sh
└── server_storage/

```



