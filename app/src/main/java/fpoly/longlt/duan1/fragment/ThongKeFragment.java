package fpoly.longlt.duan1.fragment;

import static android.view.View.GONE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.ThongKeAdapter;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.dao.ThongKeDao;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeFragment extends Fragment {
    private ListView lv_thongke;
    private ThongKeAdapter adapter;
    private List<HashMap<String, Object>> statisticsList;
    private ThongKeDao sanPhamDAO;
     Button btn_pick_date, btn_found_item, btn_lon_be, btn_be_lon, btn_veBanDau;
    public ThongKeFragment() {
        // Required empty public constructor
    }

    public static ThongKeFragment newInstance() {
        ThongKeFragment fragment = new ThongKeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_thongke = view.findViewById(R.id.lv_thongke);
        btn_pick_date = view.findViewById(R.id.btn_pick_date);
        btn_found_item = view.findViewById(R.id.btn_search_loc);
        btn_lon_be = view.findViewById(R.id.btn_lon_be);
        btn_be_lon = view.findViewById(R.id.btn_be_lon);
        btn_veBanDau = view.findViewById(R.id.btn_trangThai_banDau);

        sanPhamDAO = new ThongKeDao(getContext()); // Khởi tạo DAO

        // Lấy dữ liệu thống kê từ DAO
        statisticsList = sanPhamDAO.getMonthlyStatistics();

        // Gắn Adapter cho ListView
        adapter = new ThongKeAdapter(getContext(), statisticsList);
        lv_thongke.setAdapter(adapter);
        // Tạo biến lưu trữ dữ liệu đã chọn
        final Date[] selectedData = {null};

        btn_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);

                MonthYearPickerDialog dialog = new MonthYearPickerDialog(getContext(),
                        (view, selectedYear, selectedMonth, dayOfMonth) -> {
                            // Xử lý kết quả chọn tháng và năm
                            String formattedDate = String.format(Locale.getDefault(), "%02d-%d", selectedMonth + 1, selectedYear);
                            calendar.set(selectedYear, selectedMonth,dayOfMonth);
                            selectedData[0] = calendar.getTime();
                            btn_pick_date.setText("Tháng và Năm đã chọn: " + formattedDate);
                        }, y, m);

                dialog.show();
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                        (view1, year, month, dayOfMonth) -> {
//                            // Luu ngay da chon duoi dang date
//                            Calendar selectedCalender = Calendar.getInstance();
//                            selectedCalender.set(year,month,dayOfMonth);
//                            selectedData[0] = selectedCalender.getTime();
//                            // Dinh dang ngay cho hien thi
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//                            btn_pick_date.setText("Ngay da chon: " + sdf.format(selectedData[0]));
//                        },y,m,d);
//                datePickerDialog.show();
            }
        });
        btn_found_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedData[0] == null){
                    Toast.makeText(getContext(), "Dung bo trong phan nhap.....", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedData[0]);

                int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0
                int year = calendar.get(Calendar.YEAR);

                String formattedMonthYear = String.format("%02d-%04d", month, year);
                // Xoa tam con list cu di nha
                statisticsList.clear();
                // Goi ham tim kiem ==> Luu vao danh sach ==> Hien cmn ra cai list moi
                statisticsList.addAll(sanPhamDAO.getSearch(formattedMonthYear));
                adapter.notifyDataSetChanged();
                // Oach Xa lach vo cung/-strong/-heart:>:o:-((:-h if (statisticsList.isEmpty()){
//                Toast.makeText(getContext(), "Khong co ban ghi", Toast.LENGTH_SHORT).show();
            }
        });
        btn_lon_be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsList.clear();
                statisticsList = sanPhamDAO.getTopToUnder();
                adapter = new ThongKeAdapter(getContext(), statisticsList);
                lv_thongke.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        btn_be_lon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsList.clear();
                statisticsList = sanPhamDAO.getUnderToTop();
                adapter = new ThongKeAdapter(getContext(),statisticsList);
                lv_thongke.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        btn_veBanDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsList.clear();
                statisticsList = sanPhamDAO.getMonthlyStatistics();
                adapter = new ThongKeAdapter(getContext(),statisticsList);
                lv_thongke.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public class MonthYearPickerDialog extends DatePickerDialog {

        public MonthYearPickerDialog(Context context, OnDateSetListener listener, int year, int month) {
            super(context, listener, year, month, 1); // Ngày đặt là 1 để bỏ qua
            // Ẩn thành phần chọn ngày
            View dayView = this.getDatePicker().findViewById(
                    context.getResources().getIdentifier("day", "id", "android")
            );
            if (dayView!=null){
                dayView.setVisibility(GONE);
            }
        }
    }
}