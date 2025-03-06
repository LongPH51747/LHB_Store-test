package fpoly.longlt.duan1.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.model.User;

public class RegisterScreen extends AppCompatActivity {


    private TextInputEditText edtPhoneNumber ,edtUserName, edtPassWord, edtPassConfirm;
    private Button btnLogUp;
    private UserDAO userDAO;
    private String phone, name, pass, passConfirm;
    //hari
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhXa();

        btnLogUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogUp();
            }
        });
    }

    private void checkLogUp() {
        phone = edtPhoneNumber.getText().toString();
        name = edtUserName.getText().toString();
        pass = edtPassWord.getText().toString();
        passConfirm = edtPassConfirm.getText().toString();
        if (phone.isEmpty() || name.isEmpty() || pass.isEmpty() || passConfirm.isEmpty() || !passConfirm.equals(pass)){
            Toast.makeText(this, "Nhap Lai....", Toast.LENGTH_SHORT).show();
        }else {
            userDAO = new UserDAO(RegisterScreen.this);
            User user = new User();
            user.setUserName(name);
            user.setPhoneNumber(phone);
            user.setPassWord(pass);
            user.setRole(0);
            user.setStatus(1);
            user.setImageAvatar("img_2");
            boolean check = userDAO.insertUser(user);
            if (check){
                Toast.makeText(this, "Dang Ky Thanh Cong", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
                finish();
            }else {
                Toast.makeText(this, "Dang Ky That Bai", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void anhXa() {
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtUserName = findViewById(R.id.edtUserNameLogUp);
        edtPassWord = findViewById(R.id.edtPassWordLogUp);
        edtPassConfirm = findViewById(R.id.edtPassConfirm);
        btnLogUp = findViewById(R.id.btnLogUp);
    }
}
