package com.example.ursakter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import custom.views.PreviousButton;
import custom.views.RatingButton;

public class OwnExcusesActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private SharedPreferences appSettings;
    private TextView countView;
    private int numberOfExcuses = 0;
    private Button previousButton;
    private Button ratingButton;
    private Excuse current;
    private ArrayList<Excuse> excuses;

    private DBHandler dbHandler = new DBHandler(this);
    private ViewPager libraryPager;
    private ExcusePagerAdapter excusePagerAdapter;
    private TextView categoryNameView;
    private PageListener pageListener;
    String currentTheme;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appSettings = getSharedPreferences("AppSettings", 0);
        currentTheme = appSettings.getString("AppTheme", "OO");

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
        setContentView(R.layout.activity_own_excuses);

        initDB();
        previousButton = (Button) findViewById(R.id.previous_btn);
        //previousButton.setBackgroundResource(R.drawable.ui_app_btn_back_neg);
        ratingButton = (Button) findViewById(R.id.rating_button);
        excuses = dbHandler.getExcusesByCategory(9);

        if(excuses != null){
            numberOfExcuses = excuses.size();
            libraryPager = (ViewPager) findViewById(R.id.pager);
            excusePagerAdapter = new ExcusePagerAdapter(getSupportFragmentManager());
            excusePagerAdapter.setExcuses(excuses);
            libraryPager.setAdapter(excusePagerAdapter);

            pageListener = new PageListener();
            libraryPager.setOnPageChangeListener(pageListener);

            countView = (TextView) findViewById(R.id.textView3);
            countView.setText("1/"+numberOfExcuses);
            current = excuses.get(0);

            //setCurrentRating(excuses.get(0).getApprovals());
        }

        categoryNameView = (TextView) findViewById(R.id.textView1);
        // categoryNameView.setText("Kategori: "+dbHandler.fetchCategory(9).getName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(int position){
        libraryPager.setCurrentItem(position);
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

    public void loadNewExcuse(View view){
        libraryPager.setCurrentItem(libraryPager.getCurrentItem() + 1);
    }

    public void getPrevious(View view){
        libraryPager.setCurrentItem(libraryPager.getCurrentItem() - 1);
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

    public void removeCurrent(View view){
        if(current != null && excusePagerAdapter.getCount() != 0){
            dbHandler.removeExcuse(current.getId());

            excusePagerAdapter.removeItem(currentPosition);

            numberOfExcuses = excusePagerAdapter.getCount();

            if(currentPosition > 0){
                libraryPager.setCurrentItem(currentPosition-1);
            }else if(currentPosition == 0 && excusePagerAdapter.getCount() > 0){
                libraryPager.setCurrentItem(currentPosition+1);
            }else{
                libraryPager.setCurrentItem(0);
                libraryPager.removeViewAt(0);
                countView.setText("");
            }

            if(excusePagerAdapter.getCount() != 0){
                countView.setText(currentPosition+1+"/"+numberOfExcuses);
            }
        }
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class PageListener extends ViewPager.SimpleOnPageChangeListener{
        public void onPageSelected(int position){
            currentPosition = position;
            if(position == 0){
                previousButton.setBackgroundResource(R.drawable.ui_app_btn_back_neg);
                previousButton.invalidate();
            }else{
                if(currentTheme == "OO"){
                    previousButton.setBackgroundResource(R.drawable.ui_app_btn_back);
                }else if(currentTheme == "BB"){
                    previousButton.setBackgroundResource(R.drawable.ui_app_blue_btn_back);
                }else if(currentTheme == "PP"){
                    previousButton.setBackgroundResource(R.drawable.ui_app_purple_btn_back);
                }

                previousButton.invalidate();
            }
            current = excuses.get(position);
            //setCurrentRating(excuses.get(position).getApprovals());
            //ratingButton.invalidate();
            countView.setText(position+1+"/"+numberOfExcuses);
        }
    }

    public void openAdd(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}
