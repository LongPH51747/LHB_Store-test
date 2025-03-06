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

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.QuanLiKhachHang_Adapter;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLIKhachHang_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLIKhachHang_Fragment extends Fragment {
    ArrayList<User> lst = new ArrayList<>();
    UserDAO userDAO;
    User user;
    ListView lv_quanlikh;
    QuanLiKhachHang_Adapter adapter;
    public QuanLIKhachHang_Fragment() {
        // Required empty public constructor
    }

    public static QuanLIKhachHang_Fragment newInstance() {
        QuanLIKhachHang_Fragment fragment = new QuanLIKhachHang_Fragment();
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
        return inflater.inflate(R.layout.fragment_quan_l_i_khach_hang_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(getContext());
        lv_quanlikh = view.findViewById(R.id.lv_quanli_kh);
        lst = userDAO.getAllUser();
        adapter = new QuanLiKhachHang_Adapter(lst, getContext());
        lv_quanlikh.setAdapter(adapter);
    }
}