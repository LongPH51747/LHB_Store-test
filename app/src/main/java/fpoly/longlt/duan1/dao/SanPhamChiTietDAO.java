package fpoly.longlt.duan1.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RatingBar;


import java.util.ArrayList;

import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.ChiTietSP;

public class SanPhamChiTietDAO {
    DBHelper dbHelper;
    public SanPhamChiTietDAO(Context context){
        dbHelper = new DBHelper(context);
    }
    public boolean insertSpChiTiet(ChiTietSP chiTietSP){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sp_id",chiTietSP.getSp_id());
        contentValues.put("size", chiTietSP.getSize());
        contentValues.put("color", chiTietSP.getColor());
        contentValues.put("soluong", chiTietSP.getSoluong());
        long result = sqLiteDatabase.insert("chitietsp",null,contentValues);
        if (result == -1){
            return false;
        }
        return true;
    }
    public ArrayList<ChiTietSP> getAllSpByID(int sq_id){
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietsp WHERE sp_id = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(sq_id)});
        try {
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    ChiTietSP chiTietSP = new ChiTietSP();
                    chiTietSP.setSize(cursor.getString(cursor.getColumnIndexOrThrow("size")));
                    chiTietSP.setColor(cursor.getString(cursor.getColumnIndexOrThrow("color")));
                    chiTietSP.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("soluong"))));
                    list.add(chiTietSP);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
                sqLiteDatabase.close();
            }
        }
        return list;
    }

    public String getNameProductByID_SP(int chitietsp_id){
        SQLiteDatabase sqLiteDatabase = null;
        String name = null;
        Cursor cursor = null;
        try {
            String sql = "SELECT sanpham.tensp FROM chitietsp " +
                    "JOIN sanpham ON sanpham.sp_id = chitietsp.sp_id " +
                    "WHERE sanpham.sp_id = ?";
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(chitietsp_id)});
            if (cursor.getCount() > 0 && cursor.moveToFirst()){
                name = cursor.getString(cursor.getColumnIndexOrThrow("tensp"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null && !cursor.isClosed()) cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) sqLiteDatabase.close();
        }
        return name;
    }

    public int getPriceByID(int sp_id){
        int price = 0;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try {
            String sql = "SELECT sanpham.price FROM chitietsp " +
                    "JOIN sanpham ON sanpham.sp_id = chitietsp.sp_id " +
                    "WHERE sanpham.sp_id = ?";
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(sp_id)});
            if (cursor.getCount() > 0 && cursor.moveToFirst()){
                price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) sqLiteDatabase.close();
        }
        return price;
    }

    public boolean deleteByID(int id_spChiTiet){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int result = sqLiteDatabase.delete("chitietsp","chitietsp_id = ?", new String[]{String.valueOf(id_spChiTiet)});
        if (result == -1){
            return false;
        }
        return true;
    }
}
