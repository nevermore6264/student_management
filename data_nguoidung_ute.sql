-- Dữ liệu người dùng cho Trường Đại học Sư Phạm Kỹ thuật - Đại học Đà Nẵng
-- Email domain: @ute.udn.vn
-- Địa chỉ: Đà Nẵng và các tỉnh lân cận

INSERT INTO NGUOIDUNG (MaNguoiDung, TenNguoiDung, Email, MatKhau, SoDienThoai, DiaChi, TrangThai, NgayTao) VALUES
-- Admin
('admin01', 'Nguyễn Văn An', 'admin@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0905123456', '48 Cao Thắng, Hải Châu, Đà Nẵng', 1, NOW()),

-- Sinh viên
('SV2021001', 'Lê Thị Minh Châu', 'le.minhchau@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0901234567', '123 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 1, NOW()),
('SV2021002', 'Trần Văn Đức', 'tran.duc@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0902345678', '456 Lê Duẩn, Thanh Khê, Đà Nẵng', 1, NOW()),
('SV2021003', 'Phạm Thị Hương', 'pham.huong@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0903456789', '789 Trần Phú, Sơn Trà, Đà Nẵng', 1, NOW()),
('SV2021004', 'Nguyễn Hoàng Nam', 'nguyen.nam@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0904567890', '321 Ngô Quyền, Liên Chiểu, Đà Nẵng', 1, NOW()),
('SV2021005', 'Lê Văn Tú', 'le.tu@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0905678901', '654 Điện Biên Phủ, Cẩm Lệ, Đà Nẵng', 1, NOW()),
('SV2021006', 'Trần Thị Lan', 'tran.lan@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0906789012', '987 Nguyễn Hữu Thọ, Ngũ Hành Sơn, Đà Nẵng', 1, NOW()),
('SV2021007', 'Phạm Văn Hùng', 'pham.hung@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0907890123', '147 Võ Văn Tần, Hòa Vang, Đà Nẵng', 1, NOW()),
('SV2021008', 'Nguyễn Thị Mai', 'nguyen.mai@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0908901234', '258 Lý Thường Kiệt, Hải Châu, Đà Nẵng', 1, NOW()),
('SV2021009', 'Lê Văn Phúc', 'le.phuc@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0909012345', '369 Trần Cao Vân, Thanh Khê, Đà Nẵng', 1, NOW()),
('SV2021010', 'Trần Văn Thành', 'tran.thanh@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0900123456', '741 Nguyễn Văn Thoại, Sơn Trà, Đà Nẵng', 1, NOW()),

-- Giảng viên
('GV2021001', 'PGS.TS Nguyễn Văn Bình', 'nguyen.binh@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0912345678', '48 Cao Thắng, Hải Châu, Đà Nẵng', 1, NOW()),
('GV2021002', 'TS Trần Thị Cẩm', 'tran.cam@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0923456789', '123 Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 1, NOW()),
('GV2021003', 'ThS Lê Văn Dũng', 'le.dung@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0934567890', '456 Lê Duẩn, Thanh Khê, Đà Nẵng', 1, NOW()),
('GV2021004', 'TS Phạm Thị Hoa', 'pham.hoa@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0945678901', '789 Trần Phú, Sơn Trà, Đà Nẵng', 1, NOW()),
('GV2021005', 'PGS.TS Nguyễn Hoàng Long', 'nguyen.long@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0956789012', '321 Ngô Quyền, Liên Chiểu, Đà Nẵng', 1, NOW()),
('GV2021006', 'ThS Lê Thị Nga', 'le.nga@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0967890123', '654 Điện Biên Phủ, Cẩm Lệ, Đà Nẵng', 1, NOW()),
('GV2021007', 'TS Trần Văn Quang', 'tran.quang@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0978901234', '987 Nguyễn Hữu Thọ, Ngũ Hành Sơn, Đà Nẵng', 1, NOW()),
('GV2021008', 'ThS Phạm Thị Thu', 'pham.thu@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0989012345', '147 Võ Văn Tần, Hòa Vang, Đà Nẵng', 1, NOW()),
('GV2021009', 'PGS.TS Nguyễn Văn Sơn', 'nguyen.son@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0990123456', '258 Lý Thường Kiệt, Hải Châu, Đà Nẵng', 1, NOW()),
('GV2021010', 'TS Lê Thị Xuân', 'le.xuan@ute.udn.vn', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0991234567', '369 Trần Cao Vân, Thanh Khê, Đà Nẵng', 1, NOW()); 