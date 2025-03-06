package fpoly.longlt.duan1.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.QuanLiDonHang_Adapter;
import fpoly.longlt.duan1.adapter.QuanLiKhachHang_Adapter;
import fpoly.longlt.duan1.dao.DonHangDao;
import fpoly.longlt.duan1.dao.QuanLiDonHangDao;
import fpoly.longlt.duan1.model.DonHang;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLiDonHang_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLiDonHang_Fragment extends Fragment {
    ArrayList<DonHang> lst = new ArrayList<>();
    ListView lv_donhang;
    QuanLiDonHang_Adapter adapter;
    QuanLiDonHangDao dao;
    public QuanLiDonHang_Fragment() {
        // Required empty public constructor
    }
    public static QuanLiDonHang_Fragment newInstance() {
        QuanLiDonHang_Fragment fragment = new QuanLiDonHang_Fragment();
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
        return inflater.inflate(R.layout.fragment_quan_li_don_hang_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_donhang = view.findViewById(R.id.lv_donhangmanagment);
        dao = new QuanLiDonHangDao(getContext());
        lst = dao.getAllDonHang();
        Collections.reverse(lst);
        adapter = new QuanLiDonHang_Adapter(lst, getContext());
        lv_donhang.setAdapter(adapter);
    }
}