package com.example.ursakter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends Activity {
    private SharedPreferences appSettings;
    private SharedPreferences.Editor settingsEditor;
    private int currentTextSize;
    private TextView sampleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appSettings = getSharedPreferences("AppSettings", 0);
        settingsEditor = appSettings.edit();
        String currentTheme = appSettings.getString("AppTheme", "OO");
        currentTextSize = appSettings.getInt("TextSize", 14);

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

        setContentView(R.layout.activity_settings);

        sampleText = (TextView) findViewById(R.id.sample_text);
        sampleText.setTextSize(currentTextSize);


    }

    public void setThemeOriginalOrange(View view){
        settingsEditor.putString("AppTheme", "OO");
        settingsEditor.commit();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public void setThemePornoPurple(View view){
        settingsEditor.putString("AppTheme", "PP");
        settingsEditor.commit();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public void setThemeBabyBlue(View view){
        settingsEditor.putString("AppTheme", "BB");
        settingsEditor.commit();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public void increaseTextSize(View view){
        settingsEditor.putInt("TextSize", currentTextSize + 3);
        settingsEditor.commit();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public void decreaseTextSize(View view){
        settingsEditor.putInt("TextSize", currentTextSize - 3);
        settingsEditor.commit();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
