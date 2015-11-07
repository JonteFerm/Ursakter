package com.example.ursakter;


import android.app.Activity;
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
    DBHandler dbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
