package com.example.project;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    StatFragment statFragment = new StatFragment();
    ManageFragment manageFragment=new ManageFragment();
    AccountFragment accountFragment=new AccountFragment();
    private Button buttonAll;
    private HorizontalScrollView horizontalScrollViewButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if  (itemId == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.stat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, statFragment).commit();
                    return true;
                }
                else if (itemId == R.id.account){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();
                    return true;
                }else if (itemId == R.id.manage){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, manageFragment).commit();
                    return true;
                }

                return false;
            }
        });
        highlightSelectedItem(bottomNavigationView, R.id.home);

    }
    private void highlightSelectedItem(BottomNavigationView bottomNavigationView, int itemId) {
        // Loop through all menu items to unselect them
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == itemId);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Set the selected item when the activity starts
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
