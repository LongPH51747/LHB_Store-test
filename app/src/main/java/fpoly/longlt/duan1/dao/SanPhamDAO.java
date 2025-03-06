package fpoly.longlt.duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.SanPham;

public class SanPhamDAO {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Lấy danh sách tất cả sản phẩm
    public ArrayList<SanPham> getAllSP() {
        ArrayList<SanPham> arrayList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM sanpham", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SanPham sanPham = new SanPham();
                    sanPham.setSpId(cursor.getInt(0)); // sp_id
                    sanPham.setTenSp(cursor.getString(1)); // tên sản phẩm
                    sanPham.setImg(cursor.getString(2)); // ảnh
                    sanPham.setStatus(cursor.getInt(3)); // trạng thái
                    sanPham.setPrice(cursor.getInt(4)); // giá
                    sanPham.setDescription(cursor.getString(5)); // mô tả
                    arrayList.add(sanPham);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return arrayList;
    }


//   public ArrayList<SanPham> getSPChiTiet(int sp_id){
//       SanPham sanPham = null;
//
//       Cursor cursor = db.rawQuery("SELECT * FROM sanpham WHERE sp_id = ?", new String[]{String.valueOf(sp_id)});
//
//   }
   public boolean insertSP(SanPham sanPham){
       ContentValues values = new ContentValues();
       values.put("tensp", sanPham.getTenSp());
       values.put("img", sanPham.getImg());
       values.put("status", sanPham.getStatus());
       values.put("price", sanPham.getPrice());
       values.put("description", sanPham.getDescription());
       long result = db.insert("sanpham", null, values);
       return result != -1;
   }

   public boolean updateStatusSP(int id, boolean check){
       int status = check ? 1 :0;
       ContentValues values = new ContentValues();
       values.put("status", status);
       long result = db.update("sanpham", values, "sp_id = ?", new String[]{String.valueOf(id)});
       return result != -1;
   }

   public boolean deleteSP(int id){
       long result = db.delete("sanpham", "sp_id = ?", new String[]{String.valueOf(id)});
       return result != -1;
   }

    public boolean updateSP(SanPham sanPham){
       ContentValues values = new ContentValues();
       values.put("tensp", sanPham.getTenSp());
       values.put("img", sanPham.getImg());
       values.put("price", sanPham.getPrice());
       long result = db.update("sanpham", values,"sp_id = ?", new String[]{String.valueOf(sanPham.getSpId())});
       return result != -1;
    }

    // Lấy chi tiết sản phẩm theo ID
//    public SanPham getSPChiTiet(int spId) {
//        SanPham sanPham = null;
//        Cursor cursor = null;
//        Cursor cursorChiTiet = null;
//
//        try {
//            // Truy vấn bảng sản phẩm
//            cursor = db.rawQuery("SELECT * FROM sanpham WHERE sp_id = ?", new String[]{String.valueOf(spId)});
//            if (cursor != null && cursor.moveToFirst()) {
//                sanPham = new SanPham();
//                sanPham.setSpId(cursor.getInt(0)); // sp_id
//                sanPham.setTenSp(cursor.getString(1)); // tên sản phẩm
//                sanPham.setImg(cursor.getString(2)); // ảnh
//                sanPham.setStatus(cursor.getInt(3)); // trạng thái
//                sanPham.setPrice(cursor.getInt(4)); // giá
//            }
//
//            // Truy vấn bảng chi tiết sản phẩm
//            if (sanPham != null) {
//                cursorChiTiet = db.rawQuery("SELECT * FROM chitietsp WHERE sp_id = ?", new String[]{String.valueOf(spId)});
//                if (cursorChiTiet != null && cursorChiTiet.moveToFirst()) {
//                    sanPham.setDescription(cursorChiTiet.getString(2)); // mô tả
//                    sanPham.setSize(cursorChiTiet.getString(3)); // kích thước
//                    sanPham.setColors(cursorChiTiet.getString(4)); // màu sắc
//                    sanPham.setSoLuong(cursorChiTiet.getInt(5)); // số lượng
//                }
//            }
//        } finally {
//            if (cursor != null) cursor.close();
//            if (cursorChiTiet != null) cursorChiTiet.close();
//        }
//
//        return sanPham;
//    }
    //Lấy sản phẩm chi tiết
    public int getIdChiTietSP(String color, String size, int spId){
        int id = -1;
        Cursor cursor = db.rawQuery("select chitietsp_id from chitietsp where color = ? and size = ? and sp_id = ?", new String[]{color, size, String.valueOf(spId)});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }
//    "chitietsp_id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "sp_id INTEGER REFERENCES sanpham," +
//            "size TEXT," +
//            "color TEXT," +
//            "soluong INTEGER)";
    public ChiTietSP getSPChiTiet(int spId) {
        ChiTietSP sp = new ChiTietSP();
      Cursor cursor = db.rawQuery("SELECT chitietsp_id,sp_id FROM chitietsp WHERE sp_id = ?", new String[]{String.valueOf(spId)});
      if (cursor.getCount()>0){
          cursor.moveToFirst();
          sp.setChitietSP_id(cursor.getInt(0));
          sp.setSp_id(cursor.getInt(1));
      }
      return sp;
    }
    // Lấy sản phẩm theo ID
    public SanPham getSP(int spId) {
        SanPham sanPham = new SanPham();
        Cursor cursor = db.rawQuery("SELECT * FROM sanpham WHERE sp_id = ?", new String[]{String.valueOf(spId)});
        if (cursor != null && cursor.moveToFirst()) {
            sanPham.setSpId(cursor.getInt(0)); // sp_id
            sanPham.setTenSp(cursor.getString(1)); // tên sản phẩm
            sanPham.setImg(cursor.getString(2)); // ảnh
            sanPham.setStatus(cursor.getInt(3)); // trạng thái
            sanPham.setPrice(cursor.getInt(4)); // giá
            sanPham.setDescription(cursor.getString(5)); // mô tả
        }
        return sanPham;
    }
    // Lấy tất cả màu sắc theo sản phẩm
    public ArrayList<String> getAllColors(int spId) {
        ArrayList<String> colors = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT DISTINCT color FROM chitietsp WHERE sp_id = ?", new String[]{String.valueOf(spId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    colors.add(cursor.getString(0)); // Thêm màu sắc vào danh sách
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return colors;
    }

    // Lấy tất cả kích thước theo sản phẩm
    public ArrayList<String> getAllSizes(int spId) {
        ArrayList<String> sizes = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT DISTINCT size FROM chitietsp WHERE sp_id = ?", new String[]{String.valueOf(spId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    sizes.add(cursor.getString(0)); // Thêm kích thước vào danh sách
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return sizes;
    }

    // Thêm sản phẩm mới
//    public boolean insertSP(SanPham sanPham) {
//        ContentValues values = new ContentValues();
//        values.put("tensp", sanPham.getTenSp());
//        values.put("img", sanPham.getImg());
//        values.put("status", sanPham.getStatus());
//        values.put("price", sanPham.getPrice());
//        long result = db.insert("sanpham", null, values);
//        return result != -1;
//    }

    // Cập nhật trạng thái sản phẩm
//    public boolean updateStatusSP(int id, boolean check) {
//        int status = check ? 1 : 0;
//        ContentValues values = new ContentValues();
//        values.put("status", status);
//        long result = db.update("sanpham", values, "sp_id = ?", new String[]{String.valueOf(id)});
//        return result != -1;
//    }

    // Cập nhật thông tin sản phẩm
//    public boolean updateSP(SanPham sanPham) {
//        ContentValues values = new ContentValues();
//        values.put("tensp", sanPham.getTenSp());
//        values.put("img", sanPham.getImg());
//        values.put("price", sanPham.getPrice());
//        long result = db.update("sanpham", values, "sp_id = ?", new String[]{String.valueOf(sanPham.getSpId())});
//        return result != -1;
//    }

    // Lấy ID của sản phẩm dựa vào tên
    public int getIdSP(SanPham sanPham) {
        int id = -1;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT sp_id FROM sanpham WHERE tensp = ?", new String[]{sanPham.getTenSp()});
            if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getInt(0);
            } else {
                Log.d("SanPhamDAO", "Không tìm thấy sản phẩm với tên: " + sanPham.getTenSp());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return id;
    }

    // Lấy tất cả chi tiết sản phẩm
    public ArrayList<ChiTietSP> getAllChiTietSP() {
        ArrayList<ChiTietSP> lst = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM chitietsp", null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    ChiTietSP chiTietSP = new ChiTietSP();
                    chiTietSP.setChitietSP_id(cursor.getInt(0));
                    chiTietSP.setSp_id(cursor.getInt(1));
                    chiTietSP.setSize(cursor.getString(2));
                    chiTietSP.setColor(cursor.getString(3));
                    chiTietSP.setSoluong(cursor.getInt(4));
                    lst.add(chiTietSP);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return lst;
    }

    // Thêm chi tiết sản phẩm
    public boolean insertChiTietSP(ChiTietSP chiTietSP) {
        ContentValues values = new ContentValues();
        values.put("sp_id", chiTietSP.getSp_id());
        values.put("size", chiTietSP.getSize());
        values.put("color", chiTietSP.getColor());
        values.put("soluong", chiTietSP.getSoluong());
        long result = db.insert("chitietsp", null, values);
        return result != -1;
    }

    // Cập nhật chi tiết sản phẩm
    public boolean updateChiTietSP(ChiTietSP chiTietSP) {
        ContentValues values = new ContentValues();
        values.put("sp_id", chiTietSP.getSp_id());
        values.put("size", chiTietSP.getSize());
        values.put("color", chiTietSP.getColor());
        values.put("soluong", chiTietSP.getSoluong());
        int result = db.update("chitietsp", values, "chitietsp_id = ?", new String[]{String.valueOf(chiTietSP.getChitietSP_id())});
        return result != -1;
    }
    public ArrayList<SanPham> search(String query) {
        ArrayList<SanPham> arrayList = new ArrayList<>();
        // Kiểm tra xem query có phải là số hay không
        String sql;
        String[] selectionArgs;

        if (isNumeric(query)) {
            // Nếu query là số, tìm kiếm theo tên hoặc giá (sử dụng "=" cho giá)
            sql = "SELECT sp_id, tensp, img, status, price FROM sanpham WHERE tensp LIKE ? OR price = ?";
            selectionArgs = new String[]{"%" + query + "%", query};  // Tìm kiếm theo tên và giá
        } else {
            // Nếu query là chuỗi, chỉ tìm kiếm theo tên
            sql = "SELECT sp_id, tensp, img, status, price FROM sanpham WHERE tensp LIKE ?";
            selectionArgs = new String[]{"%" + query + "%"};  // Chỉ tìm kiếm theo tên
        }

        // Thực thi câu truy vấn
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                // Tạo đối tượng SanPham từ kết quả cursor
                SanPham sanPham = new SanPham();
                sanPham.setSpId(cursor.getInt(0));
                sanPham.setTenSp(cursor.getString(1));
                sanPham.setImg(cursor.getString(2));
                sanPham.setStatus(cursor.getInt(3));
                sanPham.setPrice(cursor.getInt(4));
                // Thêm vào danh sách
                arrayList.add(sanPham);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    // Kiểm tra xem chuỗi có phải là số không
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);  // Thử chuyển đổi chuỗi thành số
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}