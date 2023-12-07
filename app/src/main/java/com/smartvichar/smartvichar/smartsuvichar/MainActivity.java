package com.smartvichar.smartvichar.smartsuvichar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smartvichar.smartvichar.smartsuvichar.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_nav = findViewById(R.id.bottom_nav);

        bottom_nav.setSelectedItemId(R.id.home);
        load_fragment(new MainListFragment(), true);

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    load_fragment(new MainListFragment(), false);
                } else {
                    load_fragment(new AboutFragment(), false);
                }
                return true;
            }
        });
    }

    public void load_fragment(Fragment fr, boolean flag) {
        FragmentManager frm = getSupportFragmentManager();
        FragmentTransaction ftn = frm.beginTransaction();
        if (flag) {
            ftn.add(R.id.container, fr);
        } else {
            ftn.replace(R.id.container, fr);
        }
        ftn.commit();
    }
}