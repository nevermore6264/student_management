-- Cập nhật điểm cuối kỳ cho bảng DIEM
-- Điểm cuối kỳ được tính từ: diemTongKet = (diemChuyenCan * 0.2 + diemGiuaKy * 0.3 + diemCuoiKy * 0.5)
-- Suy ra: diemCuoiKy = (diemTongKet - diemChuyenCan * 0.2 - diemGiuaKy * 0.3) / 0.5

UPDATE DIEM 
SET diemCuoiKy = ROUND((diemTongKet - diemChuyenCan * 0.2 - diemGiuaKy * 0.3) / 0.5, 1)
WHERE diemChuyenCan IS NOT NULL 
  AND diemGiuaKy IS NOT NULL 
  AND diemTongKet IS NOT NULL;

-- Kiểm tra kết quả sau khi update
SELECT 
    maSinhVien,
    maLopHP,
    diemChuyenCan,
    diemGiuaKy,
    diemCuoiKy,
    diemTongKet,
    ROUND(diemChuyenCan * 0.2 + diemGiuaKy * 0.3 + diemCuoiKy * 0.5, 1) as diemTongKetTinhLai,
    ghiChu
FROM DIEM 
WHERE diemCuoiKy IS NOT NULL
ORDER BY maSinhVien, maLopHP;

-- Cập nhật điểm cuối kỳ cho các trường hợp đặc biệt (nếu điểm cuối kỳ < 0 hoặc > 10)
UPDATE DIEM 
SET diemCuoiKy = CASE 
    WHEN diemCuoiKy < 0 THEN 0
    WHEN diemCuoiKy > 10 THEN 10
    ELSE diemCuoiKy
END
WHERE diemCuoiKy IS NOT NULL;

-- Hiển thị thống kê điểm cuối kỳ
SELECT 
    'Thống kê điểm cuối kỳ' as ThongKe,
    COUNT(*) as TongSoDiem,
    AVG(diemCuoiKy) as DiemTrungBinh,
    MIN(diemCuoiKy) as DiemThapNhat,
    MAX(diemCuoiKy) as DiemCaoNhat
FROM DIEM 
WHERE diemCuoiKy IS NOT NULL; 