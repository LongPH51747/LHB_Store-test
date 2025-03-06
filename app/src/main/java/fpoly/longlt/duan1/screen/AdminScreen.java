package fpoly.longlt.duan1.screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.fragment.AdminFragment_Home;

public class AdminScreen extends AppCompatActivity {
    BottomNavigationView btnBottomNavigation;
    FrameLayout framelayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_admin, AdminFragment_Home.newInstance())
                .commit();
        btnBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_home_admin){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_admin, AdminFragment_Home.newInstance())
                            .commit();
                } else if (menuItem.getItemId() == R.id.nav_logout_admin) {
                    if (!isFinishing() && !isDestroyed()){
                        finish();
                    }
                    else {
                        Log.e("AdminScreen", "Activity đã kết thúc hoặc đang bị hủy.");
                    }
                    startActivity(new Intent(AdminScreen.this, LoginScreen.class));
                    finish();
                }
                return true;
            }
        });
    }

    public void anhxa(){
        btnBottomNavigation = findViewById(R.id.btnBottomNavigation);
        framelayout = findViewById(R.id.framelayout_admin);
    }
}