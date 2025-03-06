package fpoly.longlt.duan1.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.longlt.duan1.adapter.BannerAdapter;
import fpoly.longlt.duan1.adapter.SanPhamAdapter;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.model.SanPham;
import fpoly.longlt.duan1.R;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    SanPhamDAO sanPhamDAO;
    EditText ed_search;
    private RecyclerView recyclerView;
    private Handler sliderHandler = new Handler();
    private int currentPage = 0;
    SanPhamAdapter adapter;
    private static final int SLIDE_DELAY = 3000; // Thời gian chờ giữa các slide, tính bằng ms
    private ArrayList<SanPham> arrayList;

    public HomeFragment() {
        // Constructor mặc định
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mở hoặc tạo database (cập nhật tên của database theo cấu trúc dự án của bạn)

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        ed_search = view.findViewById(R.id.ed_search);
        viewPager2 = view.findViewById(R.id.viewPagerBanner);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                arrayList = sanPhamDAO.search(query);  // Tìm kiếm sản phẩm từ cơ sở dữ liệu
                adapter.updataData(arrayList); // Cập nhật dữ liệu trong adapter
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        // Danh sách ảnh banner
        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4
        );

        // Thiết lập Adapter cho ViewPager2
        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), bannerImages);
        viewPager2.setAdapter(bannerAdapter);

        // Thiết lập vòng lặp tự động
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
            }
        });
        LoadData();
        // Thiết lập RecyclerView cho danh sách sản phẩm
        return view;
    }


    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getAdapter() != null) {
                int itemCount = viewPager2.getAdapter().getItemCount();
                currentPage = (currentPage + 1) % itemCount;
                viewPager2.setCurrentItem(currentPage, true);
                sliderHandler.postDelayed(this, SLIDE_DELAY);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    public void LoadData() {
//        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getContext(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//        sanPhamDAO = new SanPhamDAO(getContext());
//        arrayList =sanPhamDAO.getAllSP();
//        adapter = new SanPhamAdapter(getContext(), arrayList, sanPhamDAO);
//        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        sanPhamDAO = new SanPhamDAO(getContext());
        arrayList = sanPhamDAO.getAllSP();
        adapter = new SanPhamAdapter(getContext(), arrayList, sanPhamDAO);
        recyclerView.setAdapter(adapter);
    }
//    public void LoadData() {
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//        sanPhamDAO = new SanPhamDAO(getContext());
//        arrayList = sanPhamDAO.getAllSP();
//        adapter = new SanPhamAdapter(getContext(),arrayList,sanPhamDAO);
//        recyclerView.setAdapter(adapter);
//    }

//    public void clickProduct(){
//
//        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getContext(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//        sanPhamDAO = new SanPhamDAO(getContext());
//        arrayList =sanPhamDAO.getAllSP();
//        adapter = new SanPhamAdapter(getContext(), arrayList, sanPhamDAO);
//        recyclerView.setAdapter(adapter);
//    }


}
