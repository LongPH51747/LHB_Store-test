package fpoly.longlt.duan1.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.longlt.duan1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment_Accont#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment_Accont extends Fragment {


    public AdminFragment_Accont() {
        // Required empty public constructor
    }
    public static AdminFragment_Accont newInstance() {
        AdminFragment_Accont fragment = new AdminFragment_Accont();
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
        return inflater.inflate(R.layout.fragment_admin__accont, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}