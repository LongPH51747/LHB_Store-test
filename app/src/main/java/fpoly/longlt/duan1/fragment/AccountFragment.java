package fpoly.longlt.duan1.fragment;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.SanPhamAdapter;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.model.SanPham;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.model.User;
import fpoly.longlt.duan1.screen.EditProfileScreen;
import fpoly.longlt.duan1.screen.LoginScreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    SanPhamDAO sanPhamDAO;
    Button btnPayment, btnVoucher, btnEdit;
    RecyclerView rvProduct;
    SanPhamAdapter adapter;
    ArrayList<SanPham> arrayList;

    int id_user;

    ImageView imgLogOut, imgAvatar;
    TextView txtNameAccount;
    Button btn_update_profile;

    public int id;
    public String nameUser;
    UserDAO userDAO;
    public AccountFragment() {

    }


    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_account, container, false);
            rvProduct = view.findViewById(R.id.rv_products);

            LoadData();


        LoadData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Phan Anh Xa
        imgLogOut = view.findViewById(R.id.imgLogOut);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtNameAccount = view.findViewById(R.id.txtNameAccount);
        btn_update_profile = view.findViewById(R.id.btn_update_profile);
        btnPayment = view.findViewById(R.id.btnPayment);
        userDAO = new UserDAO(getContext());
        // Lay ra phan id cua nguoi duoi day
//        Bundle bundle = requireActivity().getIntent().getExtras();
//        if (bundle != null){
//            id = bundle.getInt("id");
//            nameUser = userDAO.getNameUserByID(id);
//        }

        // LogOut: Ben tai khoan User
        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hen Gap Lai Quy Khach...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginScreen.class));
                getActivity().finish();
            }
        });
        // Phan chinh sua thong tin ==> do user tu chinh sua
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "....Updating....soon", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), EditProfileScreen.class));
            }
        });
        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user_id");
        if (user != null){
            id = user.getId_user();
            nameUser = userDAO.getNameUserByID(id);
        }else {
            Log.e("Loi", "Khong lay dc username tu id" + id );
        }

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.payment, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                EditText ed_payment = view.findViewById(R.id.ed_money_onl);
                Button btn_naptien = view.findViewById(R.id.btn_naptien);
                TextView tv_sdtk = view.findViewById(R.id.tv_sdtk);
                Intent intent = getActivity().getIntent();
                User user = (User) intent.getSerializableExtra("user_id");
                if (user != null){
                    id_user = user.getId_user();
                }
                tv_sdtk.setText("số dư: "+userDAO.getMoney(id_user));
                btn_naptien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int money = Integer.parseInt(ed_payment.getText().toString());
                        if (ed_payment.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Vui Long Nhap So Tien", Toast.LENGTH_SHORT).show();
                        }else {
                            UserDAO userDAO = new UserDAO(getContext());
                            boolean check = userDAO.updateMoney(money,id_user);
                            if (check){
                                tv_sdtk.setText("số dư: "+userDAO.getMoney(id_user));
                            }else {
                                Toast.makeText(getContext(), "Cap Nhat That Bai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();

            }
        });

        txtNameAccount.setText(nameUser);
    }

    @Override
    public void onResume() {
        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user_id");
        if (user != null){
            id = user.getId_user();
            nameUser = userDAO.getNameUserByID(id);
        }else {
            Log.e("Loi", "Khong lay dc username tu id" + id );
        }
        txtNameAccount.setText(nameUser);
        super.onResume();
    }

    public void LoadData(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvProduct.setLayoutManager(layoutManager);
        sanPhamDAO = new SanPhamDAO(getContext());
        arrayList = sanPhamDAO.getAllSP();
        adapter = new SanPhamAdapter(getContext(), arrayList, sanPhamDAO);
        rvProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}