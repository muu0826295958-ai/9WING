9WING GitHub READY UPLOAD
==========================

วิธีใช้
1) อัปโหลดไฟล์/โฟลเดอร์ทั้งหมดนี้ขึ้น GitHub repo: 9WING
2) ต้องวางไว้ที่ root repo แบบนี้:
   - popup.json
   - packets/manifest.json
   - packets/*.json
   - packetsVIP/manifest.json
   - packetsVIP/*.json
3) ลิงก์ raw ที่แอปใช้:
   - https://raw.githubusercontent.com/muu0826295958-ai/9WING/main/popup.json
   - https://raw.githubusercontent.com/muu0826295958-ai/9WING/main/packets/manifest.json
   - https://raw.githubusercontent.com/muu0826295958-ai/9WING/main/packetsVIP/manifest.json

แก้ VIP KEY
- เปิด popup.json
- แก้ vip.password หรือเพิ่มรายการใน vip.keys

เปิด/ปิดระบบจากเซิร์ฟเวอร์
- เปิด popup.json
- true = เปิด, false = ปิด
- maintenance_enabled = true จะขึ้นโหมดปิดปรับปรุง

เพิ่มปุ่มใหม่โดยไม่ต้องปล่อย APK
1) สร้างไฟล์ .json ใหม่ใน packets/ หรือ packetsVIP/
2) เพิ่มชื่อไฟล์ใน manifest.json ช่อง files
3) แอปกดโหลด config ใหม่ หรือเปิดแอปใหม่

หมายเหตุ
- ไฟล์ปุ่มตัวอย่างตั้ง enabled=false และ packets=[] ไว้ก่อน เพื่อกันกดทำงานผิดพลาด
- ให้เอาค่าปุ่ม/แพ็กเก็ตเดิมของโปรเจกต์ไปใส่ในไฟล์ sample หรือสร้างไฟล์ใหม่เอง
- จำนวนออนไลน์ใน popup.json เป็นแบบแสดงผลจากไฟล์ ถ้าจะนับจริงอัตโนมัติต้องมี backend/API เพิ่ม เพราะ GitHub raw เขียนค่าออนไลน์กลับไม่ได้
