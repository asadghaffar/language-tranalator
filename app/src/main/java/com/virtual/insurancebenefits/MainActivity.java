package com.virtual.insurancebenefits;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Locale myLocale;
    private static final String SELECTED_LANGUAGE_ID = "Selected_language_ID";
    private static final String SELECTED_LANGUAGE = "Selected_language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myLocale = new Locale(TextUtils.isEmpty(PrefUtil.getString(MainActivity.this, SELECTED_LANGUAGE))?"en":PrefUtil.getString(MainActivity.this, SELECTED_LANGUAGE));
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_language, R.id.nav_english_forum, R.id.nav_spanish_forum)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_settings)
            alertBoxForLanguageSelection();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void alertBoxForLanguageSelection() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.language_dialog, null);
        ImageView dismiss = mView.findViewById(R.id.dismiss_btn);
        RadioGroup language = mView.findViewById(R.id.language_radio);
        language.check(Integer.parseInt(TextUtils.isEmpty(PrefUtil.getString(MainActivity.this, SELECTED_LANGUAGE_ID))?"0":
                PrefUtil.getString(MainActivity.this, SELECTED_LANGUAGE_ID))
                ==0?R.id.english:Integer.parseInt(PrefUtil.getString(MainActivity.this, SELECTED_LANGUAGE_ID)));
        language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.english:
                        PrefUtil.putString(MainActivity.this, SELECTED_LANGUAGE_ID, String.valueOf(R.id.english));
                        PrefUtil.putString(MainActivity.this, SELECTED_LANGUAGE, "en");
                        MainActivity.this.setLocale("en");
                        break;
                    case R.id.spanish:
                        PrefUtil.putString(MainActivity.this, SELECTED_LANGUAGE_ID, String.valueOf(R.id.spanish));
                        PrefUtil.putString(MainActivity.this, SELECTED_LANGUAGE, "es");
                        MainActivity.this.setLocale("es");
                        break;

                }
            }
        });
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);

        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
    }
}