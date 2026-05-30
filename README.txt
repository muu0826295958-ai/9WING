# 9WING Remote Server List

ไฟล์ที่สร้างให้:
1. servers.json
   - เอาไปอัปขึ้น GitHub หรือเว็บของน้า
   - ใช้ Raw link ใส่ใน RemoteServerConfig.kt

2. ProxyList.kt
   - วางแทนไฟล์ app/src/main/java/com/yg/by9wingbot/ProxyList.kt
   - มี fallback server list ฝังในแอพ

3. RemoteServerConfig.kt
   - วางไว้ที่ app/src/main/java/com/yg/by9wingbot/RemoteServerConfig.kt
   - ตอนเปิดแอพให้เรียก RemoteServerConfig.loadOnAppStart(...)

ต้องเพิ่ม permission ใน AndroidManifest.xml:
<uses-permission android:name="android.permission.INTERNET" />

ตัวอย่างเรียกใน MainActivity.onCreate():
RemoteServerConfig.loadOnAppStart(this) { servers, fromRemote, message ->
    // TODO: refresh spinner/dropdown server ใน UI
    // servers คือรายการล่าสุดที่โหลดได้
}
