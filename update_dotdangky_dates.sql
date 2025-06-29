-- Câu lệnh UPDATE để cập nhật ngày tháng trong bảng DOTDANGKY
-- Thay đổi từ tháng 8 và tháng 1 năm 2025 sang tháng 6 và tháng 7 năm 2025

-- Cập nhật đợt đăng ký học kỳ 1 (từ tháng 8 sang tháng 6)
UPDATE DOTDANGKY 
SET NgayGioBatDau = '2025-06-01 00:00:00',
    NgayGioKetThuc = '2025-06-30 23:59:59'
WHERE MaDotDK LIKE 'DDK202501%';

-- Cập nhật đợt đăng ký học kỳ 2 (từ tháng 1 sang tháng 7)
UPDATE DOTDANGKY 
SET NgayGioBatDau = '2025-07-01 00:00:00',
    NgayGioKetThuc = '2025-07-15 23:59:59'
WHERE MaDotDK LIKE 'DDK202502%';

-- Hiển thị kết quả sau khi cập nhật
SELECT MaDotDK, TenDotDK, NgayGioBatDau, NgayGioKetThuc, MaKhoa, TrangThai
FROM DOTDANGKY
ORDER BY MaDotDK; 