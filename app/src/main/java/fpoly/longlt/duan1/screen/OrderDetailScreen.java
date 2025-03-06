package fpoly.longlt.duan1.screen;

import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.BillDetailAdapter;
import fpoly.longlt.duan1.dao.QuanLiDonHangDao;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.fragment.HomeFragment;
import fpoly.longlt.duan1.model.DonHangChiTiet;

public class OrderDetailScreen extends AppCompatActivity {
    TextView txtNameUserDT, txtMaDT, txtSoLuongDT, txtDateDT,txtGia, txtFeeVCDt, txtGiamGiaDT, txtThanhTien, tv_tongtien_2mathang;
    ImageView imgAvatarDT, imgOrderDetailProduct;
    ListView lvOrderDetail;
    BillDetailAdapter billDetailAdapter;
    QuanLiDonHangDao dao;
    ArrayList<DonHangChiTiet> lst = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhXa();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            dao = new QuanLiDonHangDao(OrderDetailScreen.this);
            lst = dao.getDonHangChiTiet(bundle.getInt("id"));
            Log.d("TAG", "list đơn hàng chi tiết: "+lst.size());
            billDetailAdapter = new BillDetailAdapter(lst, OrderDetailScreen.this, bundle.getString("ma_bills"), bundle.getInt("tongtien"));
            lvOrderDetail.setAdapter(billDetailAdapter);
            if (lst.size()<=1){
                txtThanhTien.setVisibility(View.GONE);
                txtFeeVCDt.setVisibility(View.GONE);
                tv_tongtien_2mathang.setVisibility(View.GONE);
            }
            else {
                txtFeeVCDt.setVisibility(View.VISIBLE);
                txtThanhTien.setVisibility(View.VISIBLE);
                tv_tongtien_2mathang.setVisibility(View.VISIBLE);
                txtFeeVCDt.setText("Phí vận chuyển: 20.000 VND");
                txtThanhTien.setText("Tổng tiền: "+bundle.getInt("tongtien")+" VND");
                tv_tongtien_2mathang.setText("Tổng tiền "+lst.size()+" sản phẩm: "+(bundle.getInt("tongtien")-20000)+" VND");
            }
////            txtNameUserDT.setText(bundle.getString("name"));
//            txtMaDT.setText(bundle.getString("ma_bills"));
////            txtSoLuongDT.setText(bundle.getString("soLuong"));
//            txtDateDT.setText(bundle.getString("date_bills"));
//            txtGia.setText(bundle.getString("gia"));
//            txtFeeVCDt.setText(bundle.getString("feeVC"));
//            txtGiamGiaDT.setText(bundle.getString("giamGia"));
//            txtThanhTien.setText(bundle.getString("thanhTien"));
        }

        if (bundle!=null){
            UserDAO userDAO = new UserDAO(this);
            if (id_userHere==0){
                int idUser = bundle.getInt("id_user");
                String name = userDAO.getNameUserByID(idUser);
                txtNameUserDT.setText("Tên khách hàng: "+name);
            }
        }
        imgAvatarDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutODDT, HomeFragment.newInstance()).commit();
                finish();
            }
        });

    }

    private void anhXa() {
        txtNameUserDT = findViewById(R.id.tvUserNameDT);
        txtMaDT = findViewById(R.id.tvMaOrderTD);
        txtSoLuongDT = findViewById(R.id.tvOrderQuantity);
        txtDateDT = findViewById(R.id.tvOrderDateDT);
        txtGia = findViewById(R.id.tvOrderPriceDT);
        txtFeeVCDt = findViewById(R.id.tvShip_Screen);
        tv_tongtien_2mathang = findViewById(R.id.tv_tongtien_2mathang);
//        txtGiamGiaDT = findViewById(R.id.tvDiscountDT);
        lvOrderDetail = findViewById(R.id.lv_orderdetail);
        txtThanhTien = findViewById(R.id.tvTotalAmountDT_Screen);
        imgAvatarDT = findViewById(R.id.imgUserAvatarDT);
        imgOrderDetailProduct = findViewById(R.id.imgOrderProductDT);
    }
}