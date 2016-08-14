package com.example.ursakter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomExcusesActivity extends Activity{
    private SharedPreferences appSettings;
    private TextView excuseTextView;
    private Button ratingButton;
    private ArrayList<Excuse> excuses;
    private HashSet<Integer> usedExcuses;
    private Excuse current;
    private DBHandler dbHandler = new DBHandler(this);
    private Random random;
    int newRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appSettings = getSharedPreferences("AppSettings", 0);
        String currentTheme = appSettings.getString("AppTheme", "OO");
        int currentTextSize = appSettings.getInt("TextSize", 14);

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
        setContentView(R.layout.activity_random_excuses);

        initDB();

        excuseTextView = (TextView) findViewById(R.id.excuseTextView);
        ratingButton = (Button) findViewById(R.id.rating_button);
        excuses = dbHandler.getAllExcuses();
        usedExcuses = new HashSet();
        random = new Random();

        excuseTextView.setTextSize(currentTextSize);


        loadNewExcuse();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
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

    public void loadNewExcuse(){
        newRandom = random.nextInt(excuses.size());

        if(usedExcuses.size() == excuses.size()){
            usedExcuses = new HashSet<>();
        }

        if(!usedExcuses.contains(newRandom)){
            current = excuses.get(newRandom);
            usedExcuses.add(newRandom);
        }else{
            loadNewExcuse();
        }

        setCurrentRating(current.getApprovals());
        excuseTextView.setText(current.getText());
        ratingButton.invalidate();
        excuseTextView.invalidate();
    }

    public void rateCurrent(View view){
        current.setApprovals(current.getApprovals() + 1);
        setCurrentRating(current.getApprovals());
        saveCurrent();
        ratingButton.invalidate();
    }

    public void setCurrentRating(int rating){
        switch(rating){
            case 0:
                ratingButton.setBackgroundResource(R.drawable.ui_app_menu_btn_rate_0);
                break;
            case 1:
                ratingButton.setBackgroundResource(R.drawable.ui_app_menu_btn_rate_1);
                break;
            case 2:
                ratingButton.setBackgroundResource(R.drawable.ui_app_menu_btn_rate_2);
                break;
            case 3:
                ratingButton.setBackgroundResource(R.drawable.ui_app_menu_btn_rate_3);
                break;
            case 4:
                ratingButton.setBackgroundResource(R.drawable.ui_app_menu_btn_rate_4);
                break;
            case 5:
                ratingButton.setBackgroundResource(R.drawable.ui_app_menu_btn_rate_5);
                break;
        }
    }

    private void saveCurrent(){
        dbHandler.updateExcuse(current);
    }

    public void randomize(View view){
        loadNewExcuse();
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
