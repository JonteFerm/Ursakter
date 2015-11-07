package com.example.ursakter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import custom.views.PreviousButton;
import custom.views.RatingButton;

public class OwnExcusesActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{

    private TextView countView;
    private int numberOfExcuses = 0;
    private PreviousButton previousButton;
    private RatingButton ratingButton;
    private Excuse current;
    private ArrayList<Excuse> excuses;

    private DBHandler dbHandler = new DBHandler(this);
    private ViewPager libraryPager;
    private ExcusePagerAdapter excusePagerAdapter;
    private TextView categoryNameView;
    private PageListener pageListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_excuses);
        initDB();
        previousButton = (PreviousButton) findViewById(R.id.previous_btn);
        previousButton.setNeg();
        ratingButton = (RatingButton) findViewById(R.id.rating_button);
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

            //ratingButton.setCurrentRating(excuses.get(0).getApprovals());
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
        ratingButton.setCurrentRating(current.getApprovals());
        saveCurrent();
        ratingButton.invalidate();
    }

    private void saveCurrent(){
        dbHandler.updateExcuse(current);
    }

    private void removeCurrent(View view){
        dbHandler.removeExcuse(current.getId());
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class PageListener extends ViewPager.SimpleOnPageChangeListener{
        public void onPageSelected(int position){
            if(position == 0){
                previousButton.setNeg();
                previousButton.invalidate();
            }else{
                previousButton.setPos();
                previousButton.invalidate();
            }
            current = excuses.get(position);
            //ratingButton.setCurrentRating(excuses.get(position).getApprovals());
            //ratingButton.invalidate();
            countView.setText(position+1+"/"+numberOfExcuses);
        }
    }

    public void openAdd(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}