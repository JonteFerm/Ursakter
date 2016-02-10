package com.example.ursakter;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends Activity {
    private SharedPreferences appSettings;
    private DBHandler dbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        appSettings = getSharedPreferences("AppSettings", 0);
        String currentTheme = appSettings.getString("AppTheme", "OO");

        switch (currentTheme){
            case "OO":
                this.setTheme(R.style.OriginalOrange);
                break;
            case "PP":
                this.setTheme(R.style.PornoPurple);
                break;
            case "BB":
                this.setTheme(R.style.BabyBlue);
                break;
        }
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);
        initDB();
    }

    private void initDB(){
        try {
            dbHandler.createDB();

            dbHandler.openDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void openExcuse(View view){
		Intent intent = new Intent(this, RandomExcusesActivity.class);
		startActivity(intent);
	}

    public void openLibrary(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    public void openOwnExcuses(View view){
        Intent intent = new Intent(this, OwnExcusesActivity.class);
        startActivity(intent);
    }

    public void openFavourites(View view){
        Intent intent = new Intent(this, ChooseRatingActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
