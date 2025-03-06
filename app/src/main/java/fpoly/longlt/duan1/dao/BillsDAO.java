package fpoly.longlt.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.util.Log;


import java.text.ParseException;
import java.util.ArrayList;

import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.DonHang;

public class BillsDAO {
    public DBHelper dbHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BillsDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean insertBill(DonHang donHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", donHang.getUser_id());
//        contentValues.put("vc_id", donHang.getVc_id());
//        contentValues.put("oddetail_id", donHang.getOdDetail_id());
        contentValues.put("od_date", sdf.format(donHang.getOd_date()));
        contentValues.put("status", donHang.getStatus());
        contentValues.put("chitietsp_id", donHang.getChitietsp_id());
        long result = sqLiteDatabase.insert("bills", null, contentValues);
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        if (result == -1) {
            return false;
        }
        return true;
    }

    // Lay anh tu bang sp qua bang spchitiet de lay dc ben bills

    public ArrayList<String> getImgBills(int od_id){
        String sql = "SELECT sanpham.img FROM bills" +
                " JOIN orderdetail ON bills.od_id = orderdetail.od_id" +
                " JOIN chitietsp ON chitietsp.chitietsp_id = orderdetail.chitietsp_id" +
                " JOIN sanpham ON chitietsp.sp_id = sanpham.sp_id" +
                " WHERE orderdetail.od_id = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(od_id)});
        ArrayList<String> img = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Lấy ảnh ra từ cốt tên img tại bảng sp
                img.add(cursor.getString(0));
                Log.d("TAG", "getImgBills: "+img);
                cursor.close();
                return img;
            }while (cursor.moveToNext());
        }
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            do {
//                DonHang donHang = new DonHang();
//                donHang.setOd_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("od_id"))));
//                donHang.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
////                donHang.setVc_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("vc_id"))));
////                donHang.setOdDetail_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("oddetail_id"))));
//                try {
//                    donHang.setOd_date(sdf.parse(cursor.getString(cursor.getColumnIndex("od_date"))));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                donHang.setTotal_price(Integer.parseInt(cursor.getString(cursor.getColumnIndex("total_price"))));
//                donHang.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("status"))));
////                list.add(donHang);
//            } while (cursor.moveToNext());
//        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        return img;
    }
    public ArrayList<DonHang> getAll(int id){
        ArrayList<DonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE user_id = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    DonHang donHang = new DonHang();
                    donHang.setOd_id(cursor.getInt(0));
                    donHang.setUser_id(cursor.getInt(1));
                    donHang.setVc_id(cursor.getInt(2));
                    try {
                        donHang.setOd_date(sdf.parse(cursor.getString(3)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    donHang.setTotal_price(cursor.getInt(4));
                    donHang.setStatus(cursor.getInt(5));
                    list.add(donHang);
                }while (cursor.moveToNext());
            }
            return list;
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
    public int getOrderDetail_Id(int od_id, int chitietsp_id){
        String sql = "SELECT oddetail_id FROM orderdetail WHERE od_id = ? AND chitietsp_id = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(od_id), String.valueOf(chitietsp_id)});
        try {
            if (cursor != null && cursor.moveToFirst()){
                int id = cursor.getInt(0);
                cursor.close();
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && cursor.isClosed()){
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
                sqLiteDatabase.close();
            }
        }
        return -1;
    }
}
