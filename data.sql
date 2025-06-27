-- Insert roles
INSERT INTO VAITRO (MaVaiTro, TenVaiTro, MoTa) VALUES
('ADMIN', 'Quản trị viên', 'Quản lý toàn bộ hệ thống'),
('SV', 'Sinh viên', 'Người học trong trường'),
('GV', 'Giảng viên', 'Người giảng dạy học phần');

-- Insert users
INSERT INTO NGUOIDUNG (MaNguoiDung, TenNguoiDung, Email, MatKhau, SoDienThoai, DiaChi, TrangThai, NgayTao) VALUES
('admin01', 'Nguyễn Văn A', 'admin@example.com', '123456', '0123456789', 'Hà Nội', 1, NOW()),
('sv001', 'Lê Văn C', 'le.c@example.edu', 'abc123', '0377777777', 'Hà Nam', 1, NOW()),
('sv002', 'Nguyễn Văn D', 'nguyen.d@example.edu', 'pass456', '0366666666', 'Nam Định', 1, NOW()),
('sv003', 'Lê Thị E', 'le.e@example.edu', 'pass789', '0355555555', 'Bắc Ninh', 1, NOW()),
('gv001', 'Ngô Thị D', 'ngo.d@university.edu', 'gvpass1', '0345678901', 'Hà Nội', 1, NOW()),
('gv002', 'Trần Văn F', 'tran.f@university.edu', 'gvpass2', '0322222222', 'Hà Nội', 1, NOW());

-- Assign roles to users
INSERT INTO NGUOIDUNGVAITRO (MaNguoiDung, MaVaiTro) VALUES
('admin01', 'ADMIN'),
('sv001', 'SV'),
('sv002', 'SV'),
('sv003', 'SV'),
('gv001', 'GV'),
('gv002', 'GV');

-- Insert departments
INSERT INTO KHOA (MaKhoa, TenKhoa, SoDienThoai, Email, DiaChi, MaTruong, TrangThai) VALUES
('CNTT', 'Công nghệ thông tin', '0909999999', 'cntt@university.edu', 'Tầng 3, nhà A', 'DHTH01', 1),
('KT', 'Kinh tế', '0918888888', 'kt@university.edu', 'Tầng 2, nhà B', 'DHTH01', 1);

-- Insert classes
INSERT INTO LOP (MaLop, TenLop, MaKhoa, KhoaHoc, HeDaoTao) VALUES
('CNTT01', 'Lớp CNTT 01', 'CNTT', '2021-2025', 'Chính quy'),
('KT01', 'Lớp Kinh tế 01', 'KT', '2022-2026', 'Chính quy');

-- Insert students
INSERT INTO SINHVIEN (MaSinhVien, HoTenSinhVien, NgaySinh, GioiTinh, Email, SoDienThoai, DiaChi, MaLop) VALUES
('SV001', 'Lê Văn C', '2003-05-10', 1, 'le.c@example.edu', '0377777777', 'Hà Nam', 'CNTT01'),
('SV002', 'Nguyễn Văn D', '2003-08-12', 1, 'nguyen.d@example.edu', '0366666666', 'Nam Định', 'CNTT01'),
('SV003', 'Lê Thị E', '2004-01-20', 0, 'le.e@example.edu', '0355555555', 'Bắc Ninh', 'KT01');

-- Insert lecturers
INSERT INTO GIANGVIEN (MaGiangVien, TenGiangVien, Email, SoDienThoai, MaKhoa) VALUES
('GV001', 'Ngô Thị D', 'ngo.d@university.edu', '0345678901', 'CNTT'),
('GV002', 'Trần Văn F', 'tran.f@university.edu', '0322222222', 'KT');

-- Insert courses
INSERT INTO HOCPHAN (MaHocPhan, TenHocPhan, SoTinChi, MaKhoa) VALUES
('HP001', 'Cơ sở dữ liệu', 3, 'CNTT'),
('HP002', 'Kinh tế vi mô', 3, 'KT');

-- Insert course classes
INSERT INTO LOPHOCPHAN (MaLopHP, MaHocPhan, TenLopHP, SoLuong, GiangVien, ThoiGianBatDau, ThoiGianKetThuc, PhongHoc, TrangThai) VALUES
('LHP001', 'HP001', 'Lớp CSDL 01', 60, 'GV001', '2025-09-01 07:00:00', '2025-12-20 09:00:00', 'A101', 1),
('LHP002', 'HP002', 'Lớp KTVM 01', 50, 'GV002', '2025-09-05 08:00:00', '2025-12-15 10:00:00', 'B201', 1);

-- Insert he so diem
INSERT INTO HESODIEM (MaHocPhan, HeSoChuyenCan, HeSoGiuaKy, HeSoCuoiKy) VALUES
('HP001', 0.2, 0.3, 0.5),
('HP002', 0.2, 0.3, 0.5);

-- Insert thoi khoa bieu
INSERT INTO THOIKHOABIEU (MaTKB, MaLopHP, Thu, TietBatDau, SoTiet, PhongHoc) VALUES
(1, 'LHP001', 2, 1, 3, 'A101'),
(2, 'LHP001', 4, 4, 3, 'A101'),
(3, 'LHP002', 3, 1, 3, 'B201'),
(4, 'LHP002', 5, 4, 3, 'B201');

-- Insert dot dang ky
INSERT INTO DOTDANGKY (MaDotDK, TenDotDK, NgayGioBatDau, NgayGioKetThuc, ThoiGian, MaKhoa, MoTa, TrangThai) VALUES
('DDK001', 'Đợt đăng ký học kỳ 1 năm 2025', '2025-08-01 00:00:00', '2025-08-15 23:59:59', 15, 'CNTT', 'Đăng ký học kỳ 1', 1),
('DDK002', 'Đợt đăng ký học kỳ 1 năm 2025', '2025-08-01 00:00:00', '2025-08-15 23:59:59', 15, 'KT', 'Đăng ký học kỳ 1', 1);

-- Insert phien dang ky
INSERT INTO PHIENDANGKY (MaPhienDK, MaSinhVien, NgayGioBatDau, ThoiGian, TrangThai, MaDotDK) VALUES
(1, 'SV001', '2025-08-01 00:00:00', 15, 1, 'DDK001'),
(2, 'SV002', '2025-08-01 00:00:00', 15, 1, 'DDK001'),
(3, 'SV003', '2025-08-01 00:00:00', 15, 1, 'DDK002');

-- Insert dang ky hoc phan
INSERT INTO DANGKYHOCPHAN (MaPhienDK, MaSinhVien, MaLopHP, ThoiGianDangKy, TrangThai, KetQuaDangKy) VALUES
(1, 'SV001', 'LHP001', '2025-08-01 08:00:00', 1, 1),
(2, 'SV002', 'LHP001', '2025-08-01 08:30:00', 1, 1),
(3, 'SV003', 'LHP002', '2025-08-01 09:00:00', 1, 1);

-- Insert diem
INSERT INTO DIEM (MaSinhVien, MaLopHP, DiemChuyenCan, DiemGiuaKy, DiemTongKet, GhiChu) VALUES
('SV001', 'LHP001', 8.5, 7.5, 8.0, ''),
('SV002', 'LHP001', 9.0, 8.0, 8.5, ''),
('SV003', 'LHP002', 8.0, 7.0, 7.5, ''); 