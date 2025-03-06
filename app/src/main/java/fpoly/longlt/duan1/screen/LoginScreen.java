package fpoly.longlt.duan1.screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.R;
import org.w3c.dom.Text;

import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.model.User;

public class LoginScreen extends AppCompatActivity {
    private TextInputEditText edtUserNameLogIn, edtPassWordLogIn;
    private TextView txtRegisterPage;
    private Button btnLogIn;
    private UserDAO userDAO;
    private String name, pass;
    public static int id_userHere;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhXa();
        txtRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
                finish();
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogIn();
            }
        });
    }

    private void checkLogIn() {
        userDAO = new UserDAO(this);
        name = edtUserNameLogIn.getText().toString();
        pass = edtPassWordLogIn.getText().toString();
        if (name.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "The Box was Empty...404...", Toast.LENGTH_SHORT).show();
        }else {
            userDAO = new UserDAO(this);
            user = new User();
            if (userDAO.checkLogInAdmin(name,pass)){
                Toast.makeText(this, "Welcome Back Sir!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginScreen.this, AdminScreen.class));
                finish();
            }
            if (!userDAO.checkLoginUser(name, pass)) {
                Toast.makeText(this, "Wrong Username or Password...", Toast.LENGTH_SHORT).show();
            }else if (userDAO.checkLoginUser(name, pass) && userDAO.getStatus(name,pass)==1) {
                Intent intent = new Intent(LoginScreen.this, ManHinhChinh.class);
                id_userHere = userDAO.getIDByLogIn(name,pass);
                user = new User(id_userHere);
                intent.putExtra("user_id",user);
                Log.e("idUser", "ID: " + id_userHere );
                startActivity(intent);
//                startActivity(new Intent(LoginScreen.this, ManHinhChinh.class));
                Toast.makeText(this, "Dang Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if (userDAO.getStatus(name,pass) == 0){
                Toast.makeText(this, "Người dùng đã bị chặn bởi admin.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void anhXa() {
        edtUserNameLogIn = findViewById(R.id.edtUserNameLogin);
        edtPassWordLogIn = findViewById(R.id.edtPassWordLogin);
        btnLogIn = findViewById(R.id.btnLogin);
        txtRegisterPage = findViewById(R.id.txtRegisterPage);
    }
}