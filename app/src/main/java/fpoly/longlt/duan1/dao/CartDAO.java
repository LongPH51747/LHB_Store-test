package fpoly.longlt.duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.GioHang;
import fpoly.longlt.duan1.model.SanPham;

public class CartDAO {
    private DBHelper fileHelper;
    private SQLiteDatabase db;

    public CartDAO(Context context){
        fileHelper = new DBHelper(context);
        db = fileHelper.getWritableDatabase();
    }
    public boolean addToCart(int userId, int chitietspId, int quantity) {
        Log.d("AddToCart", "Bắt đầu hàm addToCart: userId=" + userId + ", chitietspId=" + chitietspId + ", quantity=" + quantity);

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        Cursor cursor = db.rawQuery(
                "SELECT * FROM cart WHERE user_id = ? AND chitietsp_id = ? AND status = 0",
                new String[]{String.valueOf(userId), String.valueOf(chitietspId)}
        );
        try {
            if (cursor.moveToFirst()) {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                Log.d("AddToCart", "Đã tồn tại trong giỏ hàng. currentQuantity=" + currentQuantity);
                ContentValues values = new ContentValues();
                values.put("quantity", currentQuantity + quantity);
                int result = db.update("cart", values, "user_id = ? AND chitietsp_id = ? AND status = 0",
                        new String[]{String.valueOf(userId), String.valueOf(chitietspId)});
                return result > 0;
            }
        } finally {
            cursor.close();
        }

        // Nếu sản phẩm chưa tồn tại, thêm mới
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("chitietsp_id", chitietspId);
        values.put("quantity", quantity);
        long result = db.insert("cart", null, values);
        Log.d("AddToCart", "Thêm mới sản phẩm vào giỏ hàng.");
        return result != -1;
    }
//    public boolean addToCart(int user_id, int sp_id, int chitietsp_id, int quantity, int price, String imgPath, String color, String size) {
//        // Truy vấn kiểm tra sản phẩm đã tồn tại trong giỏ hàng hay chưa (dựa trên user_id, sp_id, color, size)
//        Cursor cursor = db.rawQuery(
//                "SELECT quantity, price FROM cart WHERE user_id = ? AND sp_id = ? AND color = ? AND size = ?",
//                new String[]{String.valueOf(user_id), String.valueOf(sp_id), color, size});
//
//        boolean result = false;
//
//        if (cursor.moveToFirst()) {
//            // Nếu sản phẩm đã tồn tại, tăng số lượng và cập nhật tổng tiền
//            int currentQuantity = cursor.getInt(0); // Lấy số lượng hiện tại
//            int newQuantity = currentQuantity + quantity; // Tăng số lượng
//            int newTotalPrice = newQuantity * price; // Tổng giá mới
//
//            // Cập nhật số lượng và tổng tiền trong cơ sở dữ liệu
//            ContentValues values = new ContentValues();
//            values.put("quantity", newQuantity);
//            values.put("total_price", newTotalPrice);
//
//            int rowsUpdated = db.update(
//                    "cart",
//                    values,
//                    "user_id = ? AND sp_id = ? AND color = ? AND size = ?",
//                    new String[]{String.valueOf(user_id), String.valueOf(sp_id), color, size});
//            result = rowsUpdated > 0;
//        } else {
//            // Nếu sản phẩm chưa tồn tại, thêm sản phẩm mới vào giỏ hàng
//            ContentValues values = new ContentValues();
//            values.put("user_id", user_id);
//            values.put("sp_id", sp_id);
//            values.put("chitietsp_id", chitietsp_id);
//            values.put("quantity", quantity);
//            values.put("price", price);
//            values.put("total_price", price * quantity);
//            values.put("img_path", imgPath);
//            values.put("color", color);
//            values.put("size", size);
//            long rowInserted = db.insert("cart", null, values); // Thực hiện thêm vào DB
//            result = rowInserted != -1;
//        }
//
//        cursor.close(); // Đảm bảo đóng con trỏ để tránh rò rỉ bộ nhớ
//        return result; // Trả về true nếu thành công, false nếu thất bại
//    }


    public boolean deleteCart(int user_id){
        long check = db.delete("cart", "user_id = ? AND is_selected = 1",
                new String[]{String.valueOf(user_id)});
        return check != -1;
    }

    public ArrayList<GioHang> getAll(int user_id) {
        ArrayList<GioHang> arrayList = new ArrayList<>();

        // Truy vấn SQL với điều kiện WHERE
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE user_id = ?",
                new String[]{String.valueOf(user_id)});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst(); // Di chuyển con trỏ đến dòng đầu tiên
            do {
//
                // Tạo đối tượng GioHang và gán giá trị
                GioHang gioHang = new GioHang();
                gioHang.setCart_id(cursor.getInt(0));
                gioHang.setUser_id(cursor.getInt(1));
                gioHang.setChitietsp_id(cursor.getInt(2));
                gioHang.setQuantity(cursor.getInt(3));
                gioHang.setIs_selected(cursor.getInt(4));
                gioHang.setStatus(cursor.getInt(5));
//                gioHang.setSp_id(cursor.getInt(2));
//                gioHang.setPrice(cursor.getInt(5));
//                gioHang.setTotal_price(cursor.getInt(6));
//                gioHang.setImgPath(cursor.getString(7));
//                gioHang.setColor(cursor.getString(8));
//                gioHang.setSize(cursor.getString(9));


                // Thêm vào danh sách
                arrayList.add(gioHang);
            } while (cursor.moveToNext()); // Lặp qua các dòng tiếp theo
        }

        return arrayList; // Trả về danh sách giỏ hàng
    }
    public SanPham getSanPhamById(int sp_id) {
        SanPham sanPham = null;

        // Truy vấn bảng sanpham để lấy thông tin sản phẩm
        Cursor cursor = db.query("sanpham", null, "sp_id = ?", new String[]{String.valueOf(sp_id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Khởi tạo đối tượng SanPham và gán giá trị từ cursor
            sanPham = new SanPham();
            sanPham.setSpId(cursor.getInt(0));
            sanPham.setTenSp(cursor.getString(1));
            sanPham.setImg(cursor.getString(2));
            sanPham.setStatus(cursor.getInt(3));
            sanPham.setPrice(cursor.getInt(4));
            sanPham.setDescription(cursor.getString(5));

            cursor.close();  // Đảm bảo đóng cursor sau khi sử dụng
        }

        return sanPham;
    }

    public ChiTietSP getSanPhamChiTiet(int sp_id) {
        SanPham sanPham = getSanPhamById(sp_id);  // Giả sử bạn đã có phương thức lấy thông tin sản phẩm cơ bản từ bảng sanpham
        ChiTietSP chiTietSP = new ChiTietSP();
        if (sanPham != null) {
            // Truy vấn chi tiết sản phẩm từ bảng chitietsp
            Cursor cursorChiTiet = db.query("chitietsp", null, "sp_id = ?", new String[]{String.valueOf(sp_id)}, null, null, null);

            if (cursorChiTiet != null && cursorChiTiet.moveToFirst()) {
                // Lấy thông tin chi tiết và gán vào đối tượng SanPham
                chiTietSP.setSize(cursorChiTiet.getString(2));
                chiTietSP.setColor(cursorChiTiet.getString(3));
                chiTietSP.setSoluong(cursorChiTiet.getInt(4));
                cursorChiTiet.close();  // Đảm bảo đóng cursor
            }
        }

        return chiTietSP;
    }
    public boolean updateQuantity(int quantity, int cart_id) {
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
//        values.put("total_price", gioHang.getQuantity() * gioHang.getPrice());

        int result = db.update("cart", values, "cart_id = ?",
                new String[]{String.valueOf(cart_id)});
        return result > 0; // Trả về true nếu cập nhật thành công
    }


    public boolean deleteProduct(int cart_id) {
        int result = db.delete("cart", "cart_id = ?", new String[]{String.valueOf(cart_id)});
        return result!=-1;
    }

        public void updateItemSelection(int cart_id, boolean check) {
            int is_selected = check ? 1 : 0;
            ContentValues values = new ContentValues();
            values.put("is_selected", is_selected);  // Cập nhật trạng thái
            db.update("cart", values, "cart_id = ?", new String[]{String.valueOf(cart_id)});
        }

    public List<GioHang> laySanPhamTrongGioHang(int user_id) {
        List<GioHang> cartList = new ArrayList<>();
//        cart_id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "    user_id INTEGER REFERENCES user," +
//                        "    chitietsp_id INTEGER REFERENCES chitietsp," +
//                        "    quantity INTEGER," +
//                        "is_selected BIT DEFAULT 0," +
//                        "    status BIT DEFAULT 0 -- 1: Chưa thanh toán, 0: Đã thanh toán\n" +
        String query = "SELECT c.cart_id, c.user_id, c.chitietsp_id, s.tensp, c.quantity, s.price, c.is_selected, ctsp.color, ctsp.size, s.img " +
                "FROM cart c " +
                "JOIN chitietsp ctsp ON c.chitietsp_id = ctsp.chitietsp_id " +
                "JOIN sanpham s ON ctsp.sp_id = s.sp_id " +
                "where user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});

        if (cursor.moveToFirst()) {
            do {
                int cartId = cursor.getInt(0);
                int userId = cursor.getInt(1);
                int chitietspId = cursor.getInt(2);
                String productName = cursor.getString(3);
                int quantity = cursor.getInt(4);
                int price = cursor.getInt(5);
                boolean isSelected = cursor.getInt(6) == 0; // Chuyển từ 0/1 thành true/false
                String color = cursor.getString(7);
                String size = cursor.getString(8);
                String img = cursor.getString(9);

                GioHang item = new GioHang();
                item.setCart_id(cartId);
                item.setUser_id(userId);
                item.setChitietsp_id(chitietspId);
                item.setName(productName);
                item.setImgPath(img);
                item.setQuantity(quantity);
                item.setPrice(price);
                item.setIs_selected(isSelected?0:1);
                item.setColor(color);
                item.setSize(size);
                cartList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return cartList;
    }


}
