package com.starla.resepmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.starla.resepmau.fragment.FragmentOther;
import com.starla.resepmau.fragment.FragmentRecipe;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private static boolean openFirst = true;
    private static int navStatus = -1;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComp();
        isLoggedIn();
        if(savedInstanceState == null){
            openFirst = true;
            MenuItem item = navigationView.getMenu().getItem(0).setChecked(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.user));
            onNavigationItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            SharedPreferences settings = getSharedPreferences("TOKEN", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        }else if(id == R.id.action_refresh){
            if(navStatus == 0){
                FragmentRecipe.refresh();
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment f = null;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            if(navStatus == 0 && !openFirst){
                drawer.closeDrawer(GravityCompat.START);
            }else{
                openFirst = false;
                navStatus = 0;
                fab.show();
                f = new FragmentRecipe();
                toolbar.setTitle(getResources().getString(R.string.recipe));
            }
        } else if (id == R.id.nav_gallery) {
            if(navStatus == 1){
                drawer.closeDrawer(GravityCompat.START);
            }else{
                fab.hide();
                navStatus = 1;
                f = new FragmentOther();
                toolbar.setTitle(getResources().getString(R.string.others));
            }
        }else if(id == R.id.nav_send){
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }else{
            openFirst = false;
            navStatus = 0;
            f = new FragmentRecipe();
            toolbar.setTitle(getResources().getString(R.string.user));
            fab.show();
        }

        if(f != null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container_fragment, f);
            ft.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initComp(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(MainActivity.this, UploadActivity.class));}
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setSelected(true);
    }

    private void isLoggedIn(){
        SharedPreferences settings = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = settings.getString("TOKEN", "UNDEFINED");
        if(token == null || token.equals("UNDEFINED")){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
