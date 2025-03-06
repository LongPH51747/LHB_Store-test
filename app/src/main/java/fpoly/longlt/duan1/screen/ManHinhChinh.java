package fpoly.longlt.duan1.screen;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fpoly.longlt.duan1.fragment.AccountFragment;
import fpoly.longlt.duan1.fragment.CartFragment;
import fpoly.longlt.duan1.fragment.HomeFragment;
import fpoly.longlt.duan1.fragment.OrderFragment;
import fpoly.longlt.duan1.R;

public class ManHinhChinh extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.framelayout, HomeFragment.newInstance())
            .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, HomeFragment.newInstance())
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, HomeFragment.newInstance())
                .commit();

        bottomNavigationView =findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if(menuItem.getItemId() == R.id.nav_home){
                    fragment = HomeFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_order) {
                    fragment = OrderFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_cart) {
                    fragment = CartFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_account) {
                    fragment = AccountFragment.newInstance();
                }
                if(fragment != null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout, fragment)
                            .commit();
                }
//                return true;
//                    if (menuItem.getItemId() == R.id.nav_home) {
//                        fragment = HomeFragment.newInstance();
//                    } else if (menuItem.getItemId() == R.id.nav_order) {
//                        fragment = OrderFragment.newInstance();
//                    } else if (menuItem.getItemId() == R.id.nav_cart) {
//                        fragment = CartFragment.newInstance();
//                    } else if (menuItem.getItemId() == R.id.nav_account) {
//                        fragment = AccountFragment.newInstance();
//                    }
//                    if (fragment != null) {
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.framelayout, fragment)
//                                .commit();
//                    }
                    return true;
            }
        });
    }
}
