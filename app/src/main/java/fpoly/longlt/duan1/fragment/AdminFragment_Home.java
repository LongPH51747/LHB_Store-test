package fpoly.longlt.duan1.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.longlt.duan1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment_Home extends Fragment {
    CardView card_sp, card_dh, card_kh, card_tk, card_vc;


    public AdminFragment_Home() {
        // Required empty public constructor
    }

    public static AdminFragment_Home newInstance() {
        AdminFragment_Home fragment = new AdminFragment_Home();
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
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card_sp = view.findViewById(R.id.card_sp);
        card_dh = view.findViewById(R.id.card_dh);
        card_kh = view.findViewById(R.id.card_kh);
        card_tk = view.findViewById(R.id.card_tk);
        card_kh = view.findViewById(R.id.card_kh);
        card_vc = view.findViewById(R.id.card_vch);
        card_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_admin, QuanLiSP_Fragment.newInstance())
                        .commit();
            }
        });

        card_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_admin, QuanLiDonHang_Fragment.newInstance())
                        .commit();
            }
        });

        card_kh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_admin, QuanLIKhachHang_Fragment.newInstance())
                        .commit();
            }
        });

        card_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_admin, ThongKeFragment.newInstance())
                        .commit();
            }
        });

        card_vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_admin, Voucher_Fragment.newInstance())
                        .commit();
            }
        });
    }
}