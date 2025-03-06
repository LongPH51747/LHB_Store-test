package fpoly.longlt.duan1.screen;

import static fpoly.longlt.duan1.adapter.KiemLaiAdapter.priceAmount;
import static fpoly.longlt.duan1.adapter.KiemLaiAdapter.priceOneProduct;
import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;
import static fpoly.longlt.duan1.screen.ProductDetailScreen.sp_id;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.KiemLaiAdapter;
import fpoly.longlt.duan1.dao.CartDAO;
import fpoly.longlt.duan1.dao.QuanLiDonHangDao;
import fpoly.longlt.duan1.dao.SanPhamChiTietDAO;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.fragment.OrderFragment;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.DonHang;
import fpoly.longlt.duan1.model.DonHangChiTiet;
import fpoly.longlt.duan1.model.GioHang;
import fpoly.longlt.duan1.model.User;

public class KiemLaiDonHang extends AppCompatActivity {
    ImageButton imgBtnEdit;
    Button btnPlaceOrder;
    RadioButton rd_offline, rd_online;
    TextView tvNameUserKL, tvPhoneKL, tv_payment_kiemdon, tvAddressKL, tvTotal, tvDiscount, tvShip, tvTotalPayment;
    UserDAO userDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    RecyclerView rc_kiem_lai;
    RadioGroup rgPaymentMethod;
    KiemLaiAdapter kiemLaiAdapter;
    int total_price = 0;
    ChiTietSP chiTietSP;
    SanPhamChiTietDAO sanPhamChiTietDAO;
    ArrayList<ChiTietSP> list = new ArrayList<>();
    ArrayList<User> userArrayList = new ArrayList<>();
//    List<GioHang> selectedItems = new ArrayList<>();
    ArrayList<GioHang> listCart = new ArrayList<>();
    ArrayList<DonHangChiTiet> donHangChiTietArrayList = new ArrayList<>();
    DBHelper dbHelper;
    int discount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kiem_lai_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhXa();
        // Xết view cho recycile
//        sanPhamChiTietDAO = new SanPhamChiTietDAO(KiemLaiDonHang.this);
//        list = sanPhamChiTietDAO.getAllSpByID(sp_id);
        tvTotal.setText("");
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            chiTietSP = (ChiTietSP) bundle.getSerializable("chiTietSP");
            listCart = (ArrayList<GioHang>) bundle.getSerializable("cartItems");
//            Log.d("TAG", "listcart: "+listCart.size());
            if (chiTietSP!=null){
                Log.e("truyLung", "SanPham " + sp_id );
                list.add(chiTietSP);
    //            userDAO = new UserDAO(KiemLaiDonHang.this);
                sanPhamChiTietDAO = new SanPhamChiTietDAO(KiemLaiDonHang.this);
                priceOneProduct = sanPhamChiTietDAO.getPriceByID(chiTietSP.getSp_id());
                kiemLaiAdapter = new KiemLaiAdapter(KiemLaiDonHang.this, list, listCart);
                rc_kiem_lai.setLayoutManager(new LinearLayoutManager(KiemLaiDonHang.this));
                rc_kiem_lai.setAdapter(kiemLaiAdapter);
                for (ChiTietSP sp : list){
                    total_price += priceOneProduct * sp.getSoluong();
                }
                tvTotal.setText("Tổng Tiền: "+total_price);
            } else if (listCart!=null){
                Log.d("TAG", "số lượng sản phẩm: "+listCart.size());
                kiemLaiAdapter = new KiemLaiAdapter(KiemLaiDonHang.this, list, listCart);
                rc_kiem_lai.setLayoutManager(new LinearLayoutManager(KiemLaiDonHang.this));
                rc_kiem_lai.setAdapter(kiemLaiAdapter);
                kiemLaiAdapter.notifyDataSetChanged();
                for (GioHang item : listCart) {
                    total_price += item.getQuantity() * item.getPrice();
                }
                tvTotal.setText("Tổng tiền: " + total_price + " VND");
            }
        }
        else{
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
        }
//        selectedItems = getIntent().getParcelableArrayListExtra("cartItems");
//        kiemLaiAdapter = new KiemLaiAdapter(KiemLaiDonHang.this, selectedItems);

        // Xét các view khác
        userDAO = new UserDAO(KiemLaiDonHang.this);
        userArrayList = userDAO.getUserByID(id_userHere);

        tvNameUserKL.setText("Tên Người Nhận: "+userArrayList.get(0).getNameUser());
        tvAddressKL.setText("Địa Chỉ: " +userArrayList.get(0).getAddress());
        tvPhoneKL.setText("SĐT: "+userArrayList.get(0).getPhoneNumber());
        tvDiscount.setText("Giảm Giá: "+0);
        tvShip.setText("Phí Vận Chuyển: "+20000);
        tvTotalPayment.setText("Tổng thanh toán: "+(total_price+20000));
        rd_offline.setChecked(true);
        rd_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_payment_kiemdon.setVisibility(View.VISIBLE);
                tv_payment_kiemdon.setText("Số dư ví: "+userDAO.getMoney(id_userHere));
            }
        });
        rd_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_payment_kiemdon.setVisibility(View.GONE);
            }
        });
        // Chỉnh sửa chỗ nhận bla bla bla
        imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KiemLaiDonHang.this, EditProfileScreen.class));
            }
        });
        // Chuyển qua màn hình đơn hàng cùng giữ liệu
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String paymentMethod = (rgPaymentMethod.getCheckedRadioButtonId() == R.id.rbCashOnDelivery) ? "COD" : "Online";

                if (rd_online.isChecked()){
                    if (userDAO.getMoney(id_userHere)<total_price+20000){
                    AlertDialog.Builder builder = new AlertDialog.Builder(KiemLaiDonHang.this);
                    builder.setTitle("Bạn không đủ tiền để thanh toán");
                    builder.setMessage("Bạn có muốn nạp thêm tiền không?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(KiemLaiDonHang.this);
                            View view = getLayoutInflater().inflate(R.layout.payment, null);
                            builder1.setView(view);
                            AlertDialog dialog1 = builder1.create();
                            EditText ed_payment = view.findViewById(R.id.ed_money_onl);
                            Button btn_naptien = view.findViewById(R.id.btn_naptien);
                            TextView tv_sdtk = view.findViewById(R.id.tv_sdtk);
                            tv_sdtk.setText("số dư: "+userDAO.getMoney(id_userHere));
                            btn_naptien.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int money = Integer.parseInt(ed_payment.getText().toString());
                                    if (ed_payment.getText().toString().isEmpty()){
                                        Toast.makeText(KiemLaiDonHang.this, "Vui Long Nhap So Tien", Toast.LENGTH_SHORT).show();
                                    }else {
                                        userDAO = new UserDAO(KiemLaiDonHang.this);
                                        boolean check = userDAO.updateMoney(money,id_userHere);
                                        if (check){
                                            tv_sdtk.setText("số dư: "+userDAO.getMoney(id_userHere));
                                            tv_payment_kiemdon.setText("Số dư ví: "+userDAO.getMoney(id_userHere));
                                        }else {
                                            Toast.makeText(KiemLaiDonHang.this, "Cap Nhat That Bai", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                            dialog1.show();
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                    }
                    else {
                        userDAO = new UserDAO(KiemLaiDonHang.this);
                        int newmoney = 0-total_price-20000;
                        userDAO.updateMoney(newmoney,id_userHere);
                        Log.d("newmoney", "new money: "+newmoney);
                        if (chiTietSP!=null){
                            muahang(1,chiTietSP, list.get(0).getSoluong(), priceOneProduct);
                        }
                        else if (listCart!=null){
                            muahangByCart(1,listCart);
                        }
                    }
                }
                else {
                    if (chiTietSP!=null){
                        muahang(0,chiTietSP, list.get(0).getSoluong(), priceOneProduct);
                    }
                    else if (listCart!=null){
                        muahangByCart(0,listCart);
                    }
                }


//                String voucherCode = etVoucherCode.getText().toString();
//                boolean isPlaced = dao.createOrder(userArrayList.get(0).getId_user(), );
//                if (isPlaced) {
//                    Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    public void muahangByCart(int status, ArrayList<GioHang> lst){
        QuanLiDonHangDao dao = new QuanLiDonHangDao(KiemLaiDonHang.this);
        DonHang donHang = new DonHang();
        donHang.setUser_id(id_userHere);
        donHang.setTotal_price(total_price+20000);
        try {
            donHang.setOd_date(sdf.parse(getDate()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        donHang.setStatus(status);
        boolean isPlaced = dao.createBills(donHang, lst);
        if (isPlaced){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_kiem_lai, OrderFragment.newInstance()).commit();
            CartDAO dao1 = new CartDAO(KiemLaiDonHang.this);
            dao1.deleteCart(id_userHere);
            finish();
        }
        else {
            Toast.makeText(KiemLaiDonHang.this, "Không mua được sản phẩm", Toast.LENGTH_SHORT).show();
        }

    }
    public void muahang(int status, ChiTietSP chiTietSP, int quantity, int price){
        QuanLiDonHangDao dao = new QuanLiDonHangDao(KiemLaiDonHang.this);
        int chitietsp_id = dao.getIdSPDetail(chiTietSP.getColor(), chiTietSP.getSize(),sp_id);
        DonHang donHang = new DonHang();
        donHang.setUser_id(id_userHere);
        donHang.setTotal_price(total_price+20000);
        try {
            donHang.setOd_date(sdf.parse(getDate()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        donHang.setStatus(status);
        boolean isPlaced = dao.createBill(donHang, chitietsp_id, quantity, price);
        if (isPlaced){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_kiem_lai, OrderFragment.newInstance()).commit();
            finish();
        }
        else {
            Toast.makeText(KiemLaiDonHang.this, "Không mua được sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        kiemLaiAdapter.notifyDataSetChanged();
    }

    private void anhXa() {
        rd_offline = findViewById(R.id.rbCashOnDelivery);
        rd_online = findViewById(R.id.rbEWallet);
        rc_kiem_lai = findViewById(R.id.recyclerViewProducts_kiemlai);
        tvNameUserKL = findViewById(R.id.tvUserNameKL);
        tv_payment_kiemdon = findViewById(R.id.tv_payment_kiemdon);
        tvPhoneKL = findViewById(R.id.tvUserPhoneKL);
        tvAddressKL = findViewById(R.id.tvUserAddressKL);
        imgBtnEdit = findViewById(R.id.btnEditAddress);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        rgPaymentMethod = findViewById(R.id.radioGroupPayment);
        tvTotal = findViewById(R.id.tvSubtotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvShip = findViewById(R.id.tvShippingFee);
    }
    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Tháng bắt đầu từ 0 (January = 0)
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String toDay = year + "-" + (month + 1) + "-" + day;
        return toDay;
    }
}