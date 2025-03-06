package fpoly.longlt.duan1.screen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.adapter.SP_Admin_Adapter;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.database.DBHelper;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.SanPham;

public class AddSP extends AppCompatActivity {
    EditText edtTenSP, edtGiaSP, edtMoTaSP, edsoluong;
    CheckBox chk_size_s, chk_size_m, chk_size_l, chk_size_xl, chk_size_xxl;
    ImageView img_back, img_add;
    Button btn_add;
    ArrayList<SanPham> lstSP = new ArrayList<>();
    ArrayList<String> lstSize = new ArrayList<>();
    ArrayList<ChiTietSP> lstCTSP = new ArrayList<>();
    SanPham sp;
    ChiTietSP ctsp;
    SanPhamDAO sanPhamDAO;
    SP_Admin_Adapter adminAdapter;
    int count = 0;
    LinearLayout layoutEditTexts;
    ArrayList<String> values = new ArrayList<>();
    ArrayList<EditText> editTextList = new ArrayList<>();
    static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> galleryLauncher;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_sp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        addNewEditText();
        img_back.setOnClickListener(v -> finish());
        //lấy ảnh từ thư viện
        img_add.setOnClickListener(v -> {
            checkPermission();
        });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        if (imageUri != null) {
                            img_add.setImageURI(imageUri);
                        }
                    }
                }
        );
        Log.d("img", "uri img"+galleryLauncher);
        btn_add.setOnClickListener(v -> {
            String name = edtTenSP.getText().toString();
            String price = edtGiaSP.getText().toString();
            String description = edtMoTaSP.getText().toString();
            String quantity = edsoluong.getText().toString();

            if (name.isEmpty()|| price.isEmpty() || description.isEmpty() || quantity.isEmpty() || imageUri == null){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                sanPhamDAO = new SanPhamDAO(AddSP.this);
                sp = new SanPham();
                ctsp = new ChiTietSP();

                sp.setTenSp(edtTenSP.getText().toString());
                sp.setPrice(Integer.parseInt(edtGiaSP.getText().toString()));
                sp.setStatus(1);
                sp.setImg(saveImageToInternalStorage(imageUri));
                sp.setDescription(edtMoTaSP.getText().toString());
                lstSize = size();

                values = getAllEditTextValues();

                boolean checkSP = sanPhamDAO.insertSP(sp);
                boolean checkCTSP = false;

                for (int i = 0; i < lstSize.size(); i++){
                    for (int j = 0; j < values.size(); j++){

                        int id_sp = sanPhamDAO.getIdSP(sp);
                        ctsp.setSp_id(id_sp);
                        ctsp.setColor(values.get(j));
                        ctsp.setSoluong(Integer.parseInt(edsoluong.getText().toString()));
                        ctsp.setSize(lstSize.get(i));
                        checkCTSP = sanPhamDAO.insertChiTietSP(ctsp);
                        if (checkCTSP){
                            count++;
                        }

                    }
                }

                Log.d("checkSP", checkCTSP + "");
                Log.d("checkSP", checkSP + "");

                if (count == (lstSize.size()*values.size()) && checkSP){
                    Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void anhxa(){
        btn_add = findViewById(R.id.btn_add);
        img_add = findViewById(R.id.img_sp);
        img_back = findViewById(R.id.img_back_screen_admin);
        edtTenSP = findViewById(R.id.name_sp);
        edsoluong = findViewById(R.id.quantity_sp);
        edtGiaSP = findViewById(R.id.price_sp);
        edtMoTaSP = findViewById(R.id.description_sp);
        chk_size_s = findViewById(R.id.chk_size_s);
        chk_size_m = findViewById(R.id.chk_size_m);
        chk_size_l = findViewById(R.id.chk_size_l);
        chk_size_xl = findViewById(R.id.chk_size_xl);
        chk_size_xxl = findViewById(R.id.chk_size_xxl);
        layoutEditTexts = findViewById(R.id.layout_edittexts);
    }

    public ArrayList<String> size(){
        ArrayList<String> lstSizes = new ArrayList<>();
        if (chk_size_s.isChecked()){
            lstSizes.add("S");
        }
        if (chk_size_m.isChecked()){
            lstSizes.add("M");
        }
        if (chk_size_l.isChecked()){
            lstSizes.add("L");
        }
        if (chk_size_xl.isChecked()) {
            lstSizes.add("XL");
        }
        if (chk_size_xxl.isChecked()){
            lstSizes.add("XXL");
        }
        return lstSizes;
    }


    //bộ nhớ trong để lưu ảnh và tái sử dụng lâu dài khi lôi ảnh từ thư viện
    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            // Mở InputStream từ URI
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            // Tạo tệp trong bộ nhớ trong
            File directory = getFilesDir();  // Lấy thư mục trong bộ nhớ trong của ứng dụng
            File file = new File(directory, "image_" + System.currentTimeMillis() + ".jpg");  // Đặt tên cho ảnh

            // Viết ảnh vào tệp
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            // Trả về đường dẫn của tệp
            return file.getAbsolutePath();  // Đường dẫn đến tệp đã lưu trong bộ nhớ trong
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //2 hàm này dùng để thêm editext khi editext có nội dung
    private void addNewEditText() {
        // Tạo một EditText mới
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint("Màu sắc:");
        editText.setPadding(20, 20, 20, 20);

        // Lắng nghe sự kiện thay đổi nội dung
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextList.indexOf(editText) == editTextList.size() - 1 && !s.toString().trim().isEmpty()) {
                    addNewEditText();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nếu EditText cuối cùng có dữ liệu, thêm một EditText mới

                if (s.toString().trim().isEmpty() && editTextList.contains(editText)){
                    editTextList.remove(editText);
                    layoutEditTexts.removeView(editText);
                    editTextList.get(editTextList.size() - 1).requestFocus();
                }

            }
        });

        // Thêm EditText vào danh sách và giao diện
        editTextList.add(editText);
        layoutEditTexts.addView(editText);
    }
    private ArrayList<String> getAllEditTextValues() {
        ArrayList<String> values = new ArrayList<>();
        for (EditText editText : editTextList) {
            String value = editText.getText().toString().trim();
            if (!value.isEmpty()) {
                values.add(value);
            }
        }
        return values;
    }


    //các hàm dùng để chọc an từ thư viện ảnh
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_IMAGE_REQUEST_CODE
            );
        } else {
            openGallery();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

//    private void saveImageToInternalStorage(Uri imageUri) {
//        try {
//            // Mở InputStream từ URI
//            InputStream inputStream = getContentResolver().openInputStream(imageUri);
//
//            // Tạo tên file duy nhất dựa trên thời gian
//            File file = new File(getFilesDir(), "image_from_photos_" + System.currentTimeMillis() + ".jpg");
//            OutputStream outputStream = new FileOutputStream(file);
//
//            // Sao chép nội dung từ InputStream vào OutputStream (lưu vào bộ nhớ trong)
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, length);
//            }
//
//            inputStream.close();
//            outputStream.close();
//            sp.setTenSp(edtTenSP.getText().toString());
//            sp.setPrice(Integer.parseInt(edtGiaSP.getText().toString()));
//            sp.setStatus(1);
//            sp.setImg(imageUri.toString());
//            // Lưu vào SQLite với đường dẫn của ảnh vừa lưu
//            sanPhamDAO = new SanPhamDAO(this);
//            sanPhamDAO.insertSP(sp);  // Lưu thông tin sản phẩm và ảnh vào DB
//            Log.d("img", "Image saved to: " + file.getAbsolutePath());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private void saveImagePathToDatabase(SanPham sp) {
        sanPhamDAO = new SanPhamDAO(this);
        sanPhamDAO.insertSP(sp);
    }
}


//hiển thị hình ảnh từ uri
//String imageUri = getImageUriFromDatabase(1); // ID là 1
//if (imageUri != null) {
//Uri uri = Uri.parse(imageUri);
//    img_add.setImageURI(uri);
//}
