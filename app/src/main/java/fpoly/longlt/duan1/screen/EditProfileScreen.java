package fpoly.longlt.duan1.screen;

import static fpoly.longlt.duan1.screen.AddSP.PICK_IMAGE_REQUEST_CODE;
import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.fragment.AccountFragment;
import fpoly.longlt.duan1.fragment.HomeFragment;
import fpoly.longlt.duan1.model.User;


public class EditProfileScreen extends AppCompatActivity {
    ImageView ivTurnBack, profileImage;
    TextInputEditText edtNameUpdate, edtPhoneNumberUpdate, edtAddressUpdate;
    Button btnCancel, btnUpdate;
//    String name, phone, address, img;

    public static final int PICK_IMAGE = 1;
    private ActivityResultLauncher<Intent> galleryLauncher;
    Uri imgUri;
    public String imagePath;
    UserDAO userDAO;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ
        anhXa();
        Log.e("id_here", "onCreate: " + id_userHere );
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imgUri = result.getData().getData();
                        if (imgUri != null) {
                            profileImage.setImageURI(imgUri);
                        }
                    }
                }
        );
        // Lay id cua user
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user_id");
        if (user != null){
            id = user.getId_user();
            Log.d("id", "id: "+id);
        }else {
            Log.e("id", "onCreate: " + id);
        }
        // Phan tro ra chuyen doi man hinh tu activity sang fragment
        ivTurnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutInAccount, AccountFragment.newInstance()).commit();
                finish();
            }
        });

        // Huy cac thuoc tinh da them
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAddressUpdate.setText("");
                edtNameUpdate.setText("");
                edtPhoneNumberUpdate.setText("");
                profileImage.setImageResource(R.drawable.baseline_account_circle_24);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameUpdate.getText().toString();
                String phone = edtPhoneNumberUpdate.getText().toString();
                String address = edtAddressUpdate.getText().toString();
                String img = saveImageToInternalStorage(imgUri);
                if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(EditProfileScreen.this, "Con thieu...", Toast.LENGTH_SHORT).show();
                } else {
                    userDAO = new UserDAO(EditProfileScreen.this);
                    User user = new User();
                    user.setNameUser(name);
                    user.setPhoneNumber(phone);
                    user.setAddress(address);
                    user.setImageAvatar(img);
                    if (userDAO.updateUser(user,id_userHere)){
                        Toast.makeText(EditProfileScreen.this, "Cap Nhat Thanh Cong", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutInAccount, HomeFragment.newInstance()).commit();
                        finish();
                    }else {
                        Log.e("updateU", "" + user );
                    }
//                    if (userDAO.updateNameAndAddressAnotherImg(id, name, address, phone)) {
//                        Toast.makeText(EditProfileScreen.this, "Update Success", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutInAccount, AccountFragment.newInstance()).commit();
//                        finish();
//                    }
                }
            }
        });

    }
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
    private void anhXa() {
        ivTurnBack = findViewById(R.id.ivBack);
        profileImage = findViewById(R.id.profileImage);
        edtNameUpdate = findViewById(R.id.etName);
        edtAddressUpdate = findViewById(R.id.etAddress);
        edtPhoneNumberUpdate = findViewById(R.id.etPhone);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnSave);
    }
}