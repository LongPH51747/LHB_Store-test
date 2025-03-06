package fpoly.longlt.duan1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.ThongKe;

public class ThongKeDao {
    DBHelper dbHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    SQLiteDatabase database;
    public ThongKeDao(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public List<HashMap<String, Object>> getMonthlyStatistics() {
        List<HashMap<String, Object>> statistics = new ArrayList<>();

        // Câu truy vấn SQL để lấy thống kê doanh thu và số lượng bán ra
        String sql = "SELECT " +
                "    strftime('%m-%Y',  b.od_date) AS month_year, " + // Lấy tháng và năm từ od_date
                "    SUM(od.quantity) AS total_quantity, " +        // Tổng số lượng bán ra
                "    SUM(od.price * od.quantity) AS total_revenue " + // Tổng doanh thu
                "FROM bills b " +
                "JOIN orderdetail od ON b.od_id = od.od_id " +    // Liên kết bảng bills và orderdetail
                "WHERE b.status = 2 " +                            // Trạng thái đơn hàng là đã thanh toán (hoặc hoàn thành)
                "GROUP BY month_year " +                           // Nhóm theo tháng
                "ORDER BY b.od_date DESC";                         // Sắp xếp theo ngày giảm dần


        // Mở cơ sở dữ liệu để đọc
        Cursor cursor = database.rawQuery(sql, null);

// Đọc dữ liệu từ Cursor và thêm vào danh sách
        while (cursor.moveToNext()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("month_year", cursor.getString(cursor.getColumnIndexOrThrow("month_year")));
            data.put("total_quantity", cursor.getInt(cursor.getColumnIndexOrThrow("total_quantity")));
            data.put("total_revenue", cursor.getInt(cursor.getColumnIndexOrThrow("total_revenue")));
            statistics.add(data);
        }

        cursor.close();
        return statistics;
    }

    public List<HashMap<String, Object>> getSearch(String monthYearFilter) {
        List<HashMap<String, Object>> statistics = new ArrayList<>();

        String sql = "SELECT " +
                "    strftime('%m-%Y', b.od_date) AS month_year, " +
                "    SUM(od.quantity) AS total_quantity, " +
                "    SUM(od.price * od.quantity) AS total_revenue " +
                "FROM bills b " +
                "JOIN orderdetail od ON b.od_id = od.od_id " +
                "WHERE b.status = 2 ";

        if (monthYearFilter != null && !monthYearFilter.isEmpty()) {
            sql += "AND strftime('%m-%Y', b.od_date) = ? ";
        }

        sql += "GROUP BY month_year " +
                "ORDER BY b.od_date DESC";


        Cursor cursor;
        if (monthYearFilter != null && !monthYearFilter.isEmpty()) {
            cursor = database.rawQuery(sql, new String[]{monthYearFilter});
        } else {
            cursor = database.rawQuery(sql, null);
        }

        while (cursor.moveToNext()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("month_year", cursor.getString(cursor.getColumnIndexOrThrow("month_year")));
            data.put("total_quantity", cursor.getInt(cursor.getColumnIndexOrThrow("total_quantity")));
            data.put("total_revenue", cursor.getInt(cursor.getColumnIndexOrThrow("total_revenue")));
            statistics.add(data);
        }

        cursor.close();
        return statistics;
    }
    // Xeo theo tu lon toi be
    public List<HashMap<String, Object>> getTopToUnder() {
        List<HashMap<String, Object>> statistics = new ArrayList<>();

        String sql = "SELECT " +
                "    strftime('%m-%Y', b.od_date) AS month_year, " +
                "    SUM(od.quantity) AS total_quantity, " +
                "    SUM(od.price * od.quantity) AS total_revenue " +
                "FROM bills b " +
                "JOIN orderdetail od ON b.od_id = od.od_id " +
                "WHERE b.status = 2 " +
                "GROUP BY month_year " +
                "ORDER BY total_revenue DESC";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("month_year", cursor.getString(cursor.getColumnIndexOrThrow("month_year")));
            data.put("total_quantity", cursor.getInt(cursor.getColumnIndexOrThrow("total_quantity")));
            data.put("total_revenue", cursor.getInt(cursor.getColumnIndexOrThrow("total_revenue")));
            statistics.add(data);
        }

        cursor.close();
        return statistics;
    }
    public List<HashMap<String, Object>> getUnderToTop() {
        List<HashMap<String, Object>> statistics = new ArrayList<>();
        String sql = "SELECT " +
                "    strftime('%m-%Y', b.od_date) AS month_year, " +
                "    SUM(od.quantity) AS total_quantity, " +
                "    SUM(od.price * od.quantity) AS total_revenue " +
                "FROM bills b " +
                "JOIN orderdetail od ON b.od_id = od.od_id " +
                "WHERE b.status = 2 " +
                "GROUP BY month_year " +
                "ORDER BY total_revenue ASC";

        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("month_year", cursor.getString(cursor.getColumnIndexOrThrow("month_year")));
            data.put("total_quantity", cursor.getInt(cursor.getColumnIndexOrThrow("total_quantity")));
            data.put("total_revenue", cursor.getInt(cursor.getColumnIndexOrThrow("total_revenue")));
            statistics.add(data);
        }

        cursor.close();
        return statistics;
    }
//    public ArrayList<ThongKe> getThongKe() {
//        ArrayList<ThongKe> lst = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT " +
//                "    strftime('%m-%Y', b.od_date / 1000, 'unixepoch') AS month_year," +
//                "    SUM(od.quantity) AS total_quantity," +
//                "    SUM(od.price * od.quantity) AS total_revenue " +
//                "FROM bills b " +
//                "JOIN " +
//                "    orderdetail od ON b.od_id = od.od_id " +
//                "WHERE " +
//                "    b.status = 1 " +
//                "    AND strftime('%m-%Y', b.od_date / 1000, 'unixepoch') = ? " +
//                "GROUP BY " +
//                "    month_year",new String[]{monthYear});
//        return lst;
//    }



}
