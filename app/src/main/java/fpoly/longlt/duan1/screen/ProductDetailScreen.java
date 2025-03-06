package fpoly.longlt.duan1.screen;

import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.CartDAO;
import fpoly.longlt.duan1.dao.SanPhamChiTietDAO;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.fragment.HomeFragment;
import fpoly.longlt.duan1.fragment.OrderFragment;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.SanPham;
import fpoly.longlt.duan1.model.User;

public class ProductDetailScreen extends AppCompatActivity {
    private Spinner spnSize, spnColors;
    private TextView tvNameSPCT, tvPriceSPCT, tvMoTaSPCT;
    ImageView imgSPCT;
    public static int sp_id;
    private SanPhamDAO dao;
    Button btnAddToCart, btnBuyNow;
    String mauSac, kichCo;
    CardView btn_cong, btn_tru;
    EditText ed_sl;
    ImageButton btnBackCT;
    SanPhamChiTietDAO chiTietDAO;
    int sl = 0;
    String imgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        anhXa();
        btnBackCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutPRDS, HomeFragment.newInstance()).commit();
                finish();
            }
        });

        // Lấy thông tin sản phẩm từ Intent

        // Lấy thông tin từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sp_id = bundle.getInt("id");
        }

        dao = new SanPhamDAO(this);
        ChiTietSP sp = dao.getSPChiTiet(sp_id);
        SanPham sanPham = dao.getSP(sp_id);
        tvNameSPCT.setText(sanPham.getTenSp());
        tvPriceSPCT.setText(sanPham.getPrice() + "VND");
        tvMoTaSPCT.setText(sanPham.getDescription());
        Log.d("anh", "ảnh: " + bundle.getString("img"));
        try {
            imgPath = sanPham.getImg();  // Lấy đường dẫn tệp từ SQLite
            File imgFile = new File(imgPath);  // Tạo đối tượng File từ đường dẫn
            if (imgFile.exists()) {
                Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                imgSPCT.setImageURI(uri);  // Đặt URI vào ImageView
            }
            else {
                imgSPCT.setImageResource(R.drawable.img_2);
            }
        } catch (Exception e) {
            imgSPCT.setImageResource(R.drawable.img_2);
        }
        setUpSpiner(sp_id);

        ed_sl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sl = Integer.parseInt(ed_sl.getText().toString());
                return true;
            }
        });

        btn_cong.setOnClickListener(v -> {
            sl += 1;
            ed_sl.setText(sl+" ");
        });
        btn_tru.setOnClickListener(v -> {
            sl -= 1;
            if (sl<0) {
                sl = 0;
            }
            ed_sl.setText(sl+"");
        });
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sl > 0){
//                    chiTietDAO = new SanPhamChiTietDAO(ProductDetailScreen.this);
                    ChiTietSP chiTietSP = new ChiTietSP();
                    chiTietSP.setColor(mauSac);
                    chiTietSP.setSize(kichCo);
                    chiTietSP.setSp_id(sp_id);
                    chiTietSP.setSoluong(sl);
//                    chiTietSP.setNameSpChiTiet(chiTietDAO.getNameProductByID_SP(sp_id));
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();
                    bundle.putSerializable("chiTietSP", chiTietSP);
                    intent.putExtras(bundle);
                    intent.setClass(ProductDetailScreen.this, KiemLaiDonHang.class);
//                    boolean check = chiTietDAO.insertSpChiTiet(chiTietSP);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ProductDetailScreen.this, "Số lượng phải lớn hơn 0..", Toast.LENGTH_SHORT).show();
                }
                Log.e("mkl", "mau sac: " + mauSac + " kic co: " + kichCo + " So Luong: " + sl);
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSize = spnSize.getSelectedItem() != null ? spnSize.getSelectedItem().toString() : "";
                String selectedColor = spnColors.getSelectedItem() != null ? spnColors.getSelectedItem().toString() : "";
                if (selectedColor.isEmpty()) {
                    Toast.makeText(ProductDetailScreen.this, "Bạn chưa chọn màu", Toast.LENGTH_SHORT).show();
                } else if (selectedSize.isEmpty()) {
                    Toast.makeText(ProductDetailScreen.this, "Bạn chưa chọn size", Toast.LENGTH_SHORT).show();
                }
                if (sl <= 0) {
                    Toast.makeText(ProductDetailScreen.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                } else {
                    CartDAO cartDAO = new CartDAO(ProductDetailScreen.this);
                    SanPham sanPham1 = dao.getSP(sp_id);
                    int idChiTietSP = dao.getIdChiTietSP(mauSac,kichCo,sp_id);
                    if (sanPham1 != null && idChiTietSP != -1) {
                        Log.d("TAG", "onClick: "+idChiTietSP);
//                        User user = getCurrentUser();
//                        int user_id = user.getId_user();
//                        int price = sanPham1.getPrice();
//                        String imgPath = sanPham1.getImg();
//    sl = Integer.parseInt(ed_sl.getText().toString());
                        boolean isAdded = cartDAO.addToCart(id_userHere, idChiTietSP, sl);
                        if (isAdded) {
                            Toast.makeText(ProductDetailScreen.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, CartFragment.newInstance()).commit();
//        startActivity(new Intent(ProductDetailScreen.this, CartFragment.newInstance()));
                        } else {
                            Toast.makeText(ProductDetailScreen.this, "Lỗi!!! Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void setUpSpiner(int id_sp) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO(this);

        // Lấy danh sách màu sắc và kích thước từ cơ sở dữ liệu
        ArrayList<String> listColors = sanPhamDAO.getAllColors(id_sp);
        ArrayList<String> listSize = sanPhamDAO.getAllSizes(id_sp);

        // Tạo ArrayAdapter cho Spinner Size
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSize);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSize.setAdapter(sizeAdapter);

        // Tạo ArrayAdapter cho Spinner Color
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listColors);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnColors.setAdapter(colorAdapter);
        spnSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kichCo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mauSac = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private User getCurrentUser(){
        return new User(1, "dinhbao");
    }

    private void anhXa() {
        btnBackCT = findViewById(R.id.btnBackCT);
        spnSize = findViewById(R.id.spnSize);
        spnColors = findViewById(R.id.spnColor);
        ed_sl = findViewById(R.id.ed_soluong);
        tvNameSPCT = findViewById(R.id.tvNameSPCT);
        imgSPCT = findViewById(R.id.imgSPCT);
        tvPriceSPCT = findViewById(R.id.tvPriceSPCT);
        tvMoTaSPCT = findViewById(R.id.tvMoTaSPCT);
        btn_cong = findViewById(R.id.btn_cong);
        btn_tru = findViewById(R.id.btn_tru);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }
}
