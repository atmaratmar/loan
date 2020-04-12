package com.example.loanandrepay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

            NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }



    //This is used whenever you click on the burger menu the menu bar will open
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClickRequestLoan(View view) {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        String showLogUser = sharedPref.getString("savedUser", "");

        if (Objects.equals(token, "")) {

            Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(goToLogin);
    }
        else {
            Intent goToRequestLoan = new Intent(getApplicationContext(), RequestLoan.class);
            startActivity(goToRequestLoan);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout)
        {
            SharedPreferences preferences =getSharedPreferences("userInfo",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            // set the new task and clear flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);


    }
        return false;
}
}


