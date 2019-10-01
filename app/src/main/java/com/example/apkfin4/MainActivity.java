package com.example.apkfin4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.apkfin4.ui.Favorite.FavoriteFragment;
import com.example.apkfin4.ui.Film.FilmFragment;
import com.example.apkfin4.ui.TvShow.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragment = new FilmFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment,fragment,fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigation_notifications:
                    fragment = new FavoriteFragment();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment,fragment,fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigation_dashboard:
                    fragment = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment,fragment,fragment.getClass().getSimpleName())
                            .commit();
                    return true;


            }
            return false;
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setOnNavigationItemSelectedListener( onNavigation);
        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_home);
    }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
