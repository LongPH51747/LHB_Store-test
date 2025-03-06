package fpoly.longlt.duan1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDB";
    public static final int DATABASE_VERSION = 19;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng `user`
        String createUser = "CREATE TABLE user(" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "name TEXT," +
                "sdt TEXT," +
                "address TEXT DEFAULT 'Ha Noi'," +
                "moneyonl INTEGER DEFAULT 0," +
                "role INTEGER DEFAULT 0," +
                "status BIT DEFAULT 1," +
                "imgavatar TEXT)";
        db.execSQL(createUser);

        // Tạo bảng `sanpham`
        String createSP = "CREATE TABLE sanpham(" +
                "sp_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tensp TEXT," +
                "img TEXT," +
                "status BIT DEFAULT 1," +
                "price INTEGER," +
                "description TEXT)";
        db.execSQL(createSP);

        // Tạo bảng `chitietsp`
        String createChiTietSP = "CREATE TABLE chitietsp(" +
                "chitietsp_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sp_id INTEGER REFERENCES sanpham," +
                "size TEXT," +
                "color TEXT," +
                "soluong INTEGER)";
        db.execSQL(createChiTietSP);

        // Tạo bảng `bills`
        String createBills = "CREATE TABLE bills(" +
                "od_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER REFERENCES user," +
                "vc_id INTEGER REFERENCES voucher," +
//                "chitietsp_id INTEGER REFERENCES chitietsp," +
//                "oddetail_id INTEGER REFERENCES orderdetail," +
//                "oddetail_id INTEGER REFERENCES orderdetail," +
                "od_date DATE NOT NULL," +
                "total_price integer," +
                "status INTEGER)";

        db.execSQL(createBills);

        // Tạo bảng `orderdetail`
        String createOrderDetail = "CREATE TABLE orderdetail(" +
                "oddetail_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "od_id INTEGER REFERENCES bills," +
                "chitietsp_id INTEGER REFERENCES sanpham," +
                "quantity INTEGER," +
                "price INTEGER)";
        db.execSQL(createOrderDetail);

        // Tạo bảng `voucher`
        String createVoucher = "CREATE TABLE voucher(" +
                "vc_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT," +
                "discount_price INTEGER," +
                "start_date TEXT," +
                "end_date TEXT," +
                "dieukien INTEGER," +
                "status BIT DEFAULT 0)";
        db.execSQL(createVoucher);
        String createCart = "CREATE TABLE cart(" +
                "    cart_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    user_id INTEGER REFERENCES user," +
                "    chitietsp_id INTEGER REFERENCES chitietsp," +
                "    quantity INTEGER," +
                "    is_selected BIT DEFAULT 0," +
                "    status BIT DEFAULT 0 -- 1: Chưa thanh toán, 0: Đã thanh toán\n" +
                ")";
        db.execSQL(createCart);
        // Tạo bảng `cart`
//        String createCart = "CREATE TABLE cart(" +
//                "cart_id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "user_id INTEGER REFERENCES user," +
////                "sp_id INTEGER REFERENCES sanpham," +
//                "chitietsp_id REFERENCES chitietsp," +
//                "quantity INTEGER DEFAULT 1," +
//                "price INTEGER," +
//                "total_price INTEGER," +
//                "img_path TEXT," +
//                "color TEXT," +
//                "size TEXT," +
//                "status INTEGER)";
//        db.execSQL(createCart);

        // Thêm dữ liệu mẫu
        addSampleData(db);
    }

    private void addSampleData(SQLiteDatabase db) {
        // Dữ liệu mẫu cho `user`
//        String insertUser = "INSERT INTO user(username, password, name, sdt, address, role, status, imgavatar) VALUES " +
//                "('admin','admin123','Vien Kiem Soat','0971297489','Ha Noi',1,1,'img_1')," +
//                "('hai','123','HaiViet','0971296368','Ha Tay',0,1,'1234')," +
//                "('bao','123','GiaBao','0987654412','Ha Nam',0,1,'345')," +
//                "('long','123','ThanhLong','091234567','Ha Dong',0,1,'678')";
//        db.execSQL(insertUser);

        // Dữ liệu mẫu cho `sanpham`
//        String insertSP = "INSERT INTO sanpham(tensp, img, status, price, description) VALUES " +
//                "('áo khoác gió nam phong cách hàn quốc', 'img_2', 1, 10000, 'chất liệu cotton siêu mát')," +
//                "('Quần jean trắng', 'img_3', 1, 20000, 'vải nhung tăm dày dặn, thoải mái, ấm áp')";
//        db.execSQL(insertSP);
//
//        // Dữ liệu mẫu cho `chitietsp`
//        String insertChiTietSP = "INSERT INTO chitietsp(sp_id, size, color, soluong) VALUES " +
//                "(1, 'M', 'Red', 100)," +
//                "(1, 'L', 'White', 70)," +
//                "(1, 'L', 'Red', 70)," +
//                "(1, 'S', 'Blue', 50)," +
//                "(1, 'M', 'Black', 60)," +
//                "(1, 'L', 'White', 40)," +
//                "(2, 'XL', 'Black', 30)," +
//                "(2, 'M', 'White', 60)," +
//                "(2, 'L', 'Black', 40)," +
//                "(2, 'X', 'Gray', 24)";
//        db.execSQL(insertChiTietSP);

//        // Dữ liệu mẫu cho `bills`
//        String insertBills = "INSERT INTO bills(user_id, vc_id, chitietsp_id, oddetail_id, od_date, status) VALUES " +
//                "(1, NULL, 1, NULL, '2024-11-11', 0)";
//        db.execSQL(insertBills);
//        String insertBillDetail = "insert into orderdetail values " +
//                "(1,1,1,2,2000),"+
//                "(2,2,1,3,2000),"+
//                "(3,3,2,1,2000),"+
//                "(4,4,2,2,2000)";
//        String insertBills = "insert into bills values" +
//                "(1,1,'2024-11-22',4000,2)," +
//                "(2,1,'2024-11-25',6000,1),"+
//                "(3,2,'2024-10-20',2000,2),"+
//                "(4,2,'2024-11-27',4000,0)";
//                db.execSQL(insertBills);
//                db.execSQL(insertBillDetail);
//        String createVoucher = "create table voucher(" +
//                "vc_id integer primary key autoincrement," +
//                "code text," +
//                "discount_price integer," +
//                "start_date text," +
//                "end_date text," +
//                "dieukien integer," +
//                "status bit default 0)";
//        db.execSQL(createVoucher);

        // Them cac du lieu fix cung de test o day
        String addAdmin = ("insert into user values" +
                "(0,'admin','admin123','Vien Kiem Soat','0971297489','Ha Noi',0,1,1,'img_1')"
        );
        db.execSQL(addAdmin);
        String insertUser = "insert into user values" +
                "(1,'hai','123','HaiViet','0971296368','Ha Tay',1,0,1,'aaaa')," +
                "(2,'bao','123','GiaBao','0987654412','Ha Nam',1,0,1,'aaa')," +
                "(3,'long','123','ThanhLong','091234567','Ha Dong',1,0,1,'aaaa')";
        db.execSQL(insertUser);
        String insertIntoVoucher = "insert into voucher values" +
                "(0,'sales20',20000,'2024-11-20','2024-12-20',2000,1)," +
                "(1,'sales30',30000,'2024-11-25','2024-12-25',3000,1)";
                db.execSQL(insertIntoVoucher);
//        String createCart = "create table cart(" +
//                "cart_id integer primary key autoincrement," +  // ID tự tăng cho từng mục trong giỏ hàng
//                "user_id integer references user," +            // ID người dùng, liên kết với bảng users (nếu có)
//                "sp_id integer references sanpham," +           // ID sản phẩm, liên kết với bảng sản phẩm
//                "quantity integer default 1," +                 // Số lượng sản phẩm, mặc định là 1
//                "price integer," +                              // Giá của một sản phẩm
//                "total_price integer" +                         // Tổng giá = quantity * price
//                ")";
//        db.execSQL(createCart);
//        String creeateThongKe = "create table thongke (" +
//                "od_id integer references bills," +
//                "order_date text," +
//                "total_rice integer)";
//        db.execSQL(creeateThongKe);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS sanpham");
            db.execSQL("DROP TABLE IF EXISTS chitietsp");
            db.execSQL("DROP TABLE IF EXISTS bills");
            db.execSQL("DROP TABLE IF EXISTS orderdetail");
            db.execSQL("DROP TABLE IF EXISTS voucher");
            db.execSQL("DROP TABLE IF EXISTS cart");
            onCreate(db);
        }
    }
}
