
package fpoly.longlt.duan1.dao;

import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.User;


public class UserDAO {
    private DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUserName());
        contentValues.put("password", user.getPassWord());
        contentValues.put("name", user.getNameUser());
        contentValues.put("sdt", user.getPhoneNumber());
        contentValues.put("address", user.getAddress());
        contentValues.put("status", user.getStatus());
        contentValues.put("imgavatar", user.getImageAvatar());
//        contentValues.put("role", user.getRole());
        long result = sqLiteDatabase.insert("user",null,contentValues);
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        if (result == -1) {
            return false;
        }
        return true;
    }

    public ArrayList<User> getAllUser(){
        ArrayList<User> lst = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user", null);
        if (cursor.getCount()>1){
            cursor.moveToPosition(1);
            do {
                User user = new User();
                user.setId_user(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                user.setPassWord(cursor.getString(2));
                user.setNameUser(cursor.getString(3));
                user.setPhoneNumber(cursor.getString(4));
                user.setAddress(cursor.getString(5));
                user.setMoneyOnl(cursor.getInt(6));
                user.setRole(cursor.getInt(7));
                user.setStatus(cursor.getInt(8));
                user.setImageAvatar(cursor.getString(9));
                lst.add(user);
            } while (cursor.moveToNext());
        }
        return lst;
    }
    public boolean checkLogInAdmin(String userName, String passWord){
        int role = 1;
        String sql = "SELECT * FROM user WHERE username = ? AND password = ? AND role = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{userName, passWord, String.valueOf(role)});
        boolean result = (cursor.getCount() > 0);
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        return result;
    }

    public boolean checkLoginUser(String useName, String passWord) {
        int role = 0;
        String sql = "SELECT * FROM user WHERE username = ? AND password = ? AND role = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{useName, passWord, String.valueOf(role)});
        boolean result = (cursor.getCount() > 0);
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        return result;
    }

    public int getStatus(String useName, String passWord) {
        int status = 0;
        String sql = "select status from user where username = ? and password = ?";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,new String[]{useName,passWord});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            status = cursor.getInt(0);
            Log.d("db","status"+status+""+cursor.getCount());
        }
        return status;
    }

    public boolean deleteUser(int id_user){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int result = sqLiteDatabase.delete("user", "user_id = ?", new String[]{String.valueOf(id_user)});
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean changePassWord(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", user.getPassWord());
        int result = sqLiteDatabase.update("user", contentValues, "user_id = ?", new String[]{String.valueOf(user.getId_user())});
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean addAddress(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", user.getAddress());
        int result = sqLiteDatabase.update("user", contentValues, "user_id = ?", new String[]{String.valueOf(user.getId_user())});
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        if (result == -1) {
            return false;
        }
        return true;
    }

    public int getIDByLogIn(String name, String pass) {
        String sql = "SELECT user_id FROM user WHERE username = ? AND password = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{name, pass});
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
            cursor.close();
            sqLiteDatabase.close();
            return id;
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        // Neu ko tim thay tra ve -1
        return -1;
    }
    public String getNameUserByID(int id){
        int role = 0;
        String sql = "SELECT name FROM user WHERE user_id = ? AND role = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(id),String.valueOf(role)});
        if (cursor != null && cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            cursor.close();
            sqLiteDatabase.close();
            return name;
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        // tra ve chuoi rong
        return null;
    }
    @SuppressLint("Range")
    public ArrayList<User> getData(String sql, String... selectionArgs) {
        ArrayList<User> userArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    User user = new User();
                    user.setId_user(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
                    user.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                    user.setPassWord(cursor.getString(cursor.getColumnIndex("password")));
                    user.setNameUser(cursor.getString(cursor.getColumnIndex("name")));
                    user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("sdt")));
                    user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                    user.setMoneyOnl(Integer.parseInt(cursor.getString(cursor.getColumnIndex("moneyonl"))));
                    user.setRole(Integer.parseInt(cursor.getString(cursor.getColumnIndex("role"))));
                    userArrayList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return userArrayList;
    }

    public ArrayList<User> getALl() {
        String sql = "SELECT * FROM user";
        return getData(sql);
    }
    public boolean updateStatus(int id, boolean check){
        int status = check ?1:0;

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", status);
        int result = sqLiteDatabase.update("user", values, "user_id = ?", new String[]{String.valueOf(id)});
        return result != -1;
    }

    public int getMoney(int id) {
        int money = 0;
        String sql = "SELECT moneyonl FROM user WHERE user_id = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                money = cursor.getInt(cursor.getColumnIndexOrThrow("moneyonl"));
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Đảm bảo đóng Cursor
            }
        }
        return money;
    }
    public boolean updateMoney(int money, int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        try {
            int currentMoney = getMoney(id); // Lấy số tiền hiện tại
            int newMoney = currentMoney + money; // Cộng thêm số tiền mới

            ContentValues values = new ContentValues();
            values.put("moneyonl", newMoney); // Cập nhật số tiền mới

            int check = sqLiteDatabase.update("user", values, "user_id = ?", new String[]{String.valueOf(id)});
            return check > 0; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            sqLiteDatabase.close(); // Đóng cơ sở dữ liệu sau khi sử dụng
        }
    }

    public boolean updateUser(User user, int id){
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", user.getNameUser());
            contentValues.put("sdt", user.getPhoneNumber());
            contentValues.put("address", user.getAddress());
            contentValues.put("imgavatar", user.getImageAvatar());

            int result = sqLiteDatabase.update("user", contentValues, "user_id = ?", new String[]{String.valueOf(id)});
            return result > 0; // Trả về true nếu có ít nhất một hàng được cập nhật
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            return false;
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close(); // Đảm bảo đóng database
            }
        }
    }

    public String getImgAvatar(int id){
        String sql = "SELECT imgavatar FROM user WHERE user_id = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()){
            String imgPath = cursor.getString(cursor.getColumnIndexOrThrow("imgavatar"));
            cursor.close();
            sqLiteDatabase.close();
            return imgPath;
        }
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        if (sqLiteDatabase != null & sqLiteDatabase.isOpen()){
            cursor.close();
        }
        return null;
    }

    public boolean updateNameAndAddressAnotherImg(int userId, String newName, String newAddress, String newPhoneNumber) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", newName);
            contentValues.put("address", newAddress);
            contentValues.put("sdt", newPhoneNumber);
            int result = sqLiteDatabase.update("user", contentValues, "user_id = ?", new String[]{String.valueOf(userId)});
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    public ArrayList<User> getUserByID(int user_id){
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM user WHERE user_id = ?";
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(user_id)});
            if(cursor.getCount() > 0 && cursor.moveToFirst()){
                User user = new User();
                user.setNameUser(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow("sdt")));
                user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) sqLiteDatabase.close();
        }
        return users;
    }
}
