package fpoly.longlt.duan1.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.SP_Admin_Adapter;
import fpoly.longlt.duan1.adapter.SanPhamAdapter;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.model.SanPham;
import fpoly.longlt.duan1.screen.AddSP;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLiSP_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLiSP_Fragment extends Fragment {
    TextView btn_add_sp;
    RecyclerView rc_sp_admin;
    SanPhamDAO sanPhamDAO;
    ArrayList<SanPham> lst = new ArrayList<>();
    SP_Admin_Adapter adapter;

    public QuanLiSP_Fragment() {
        // Required empty public constructor
    }

    public static QuanLiSP_Fragment newInstance() {
        QuanLiSP_Fragment fragment = new QuanLiSP_Fragment();
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
        return inflater.inflate(R.layout.fragment_quan_li_s_p_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_add_sp = view.findViewById(R.id.btn_add_sp);
        rc_sp_admin = view.findViewById(R.id.rc_quanlisp);

        btn_add_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(view.getContext(), AddSP.class));
            }
        });

        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    public void loadData() {
        sanPhamDAO = new SanPhamDAO(getContext());
        lst = sanPhamDAO.getAllSP();
        adapter = new SP_Admin_Adapter(lst, getContext());
        adapter.notifyDataSetChanged();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rc_sp_admin.setLayoutManager(layoutManager);
        rc_sp_admin.setAdapter(adapter);
    }

}