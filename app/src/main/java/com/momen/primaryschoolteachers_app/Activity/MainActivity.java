package com.momen.primaryschoolteachers_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.momen.primaryschoolteachers_app.Fragment.HomeFragment;
import com.momen.primaryschoolteachers_app.Fragment.LogFragment;
import com.momen.primaryschoolteachers_app.Fragment.NoticeFragment;
import com.momen.primaryschoolteachers_app.Fragment.ProfileFragment;
import com.momen.primaryschoolteachers_app.R;
import com.momen.primaryschoolteachers_app.api.SaveItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String total;
    SharedPreferences prefs;
    Fragment selectFragment = null ;
    private String sharedPrefFile =
            "com.olivine.primaryschoolteachers_app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        total = prefs.getString(SaveItem.total,"defaultValue");
        if(total.equals("false"))
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                    new ProfileFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                    new HomeFragment()).commit();

        }

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                pullToRefresh.setRefreshing(false);



            }
        });


        BottomNavigationView bottomNv = findViewById(R.id.bottomNav);
        bottomNv.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectFragment = new HomeFragment();
                    break;
                case R.id.nav_log:
                    selectFragment = new LogFragment();
                    break;
                case  R.id.nav_profile:
                    selectFragment = new ProfileFragment();
                    break;
                case R.id.notice:
                    selectFragment = new NoticeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                    selectFragment).commit();

            return true ;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
