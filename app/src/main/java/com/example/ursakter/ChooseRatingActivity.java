package com.example.ursakter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class ChooseRatingActivity extends Activity {
    private SharedPreferences appSettings;

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
        setContentView(R.layout.activity_choose_rating);

    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openFavourites(View view){
        Intent intent = new Intent(this, FavouriteActivity.class);
        intent.putExtra("approvals", Integer.valueOf((String)view.getTag()));
        startActivity(intent);
    }

}
