package com.example.ursakter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import custom.views.PreviousButton;
import custom.views.RatingButton;

public class RandomExcuseActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private TextView countView;
    private int numberOfExcuses;
    private PreviousButton previousButton;
    private RatingButton ratingButton;
    private Excuse current;
    private ArrayList<Excuse> excuses;

    private DBHandler dbHandler = new DBHandler(this);
    private ViewPager libraryPager;
    private LibraryPagerAdapter libraryPagerAdapter;
    private TextView categoryNameView;
    private int categoryId;
    private PageListener pageListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initDB();
        previousButton = (PreviousButton) findViewById(R.id.previous_btn);
        previousButton.setNeg();
        ratingButton = (RatingButton) findViewById(R.id.rating_button);
        categoryId = getIntent().getIntExtra("categoryId", 0);
        excuses = dbHandler.getExcusesByCategory(categoryId);
        numberOfExcuses = excuses.size();
        categoryNameView = (TextView) findViewById(R.id.textView1);
        categoryNameView.setText("Kategori: "+dbHandler.fetchCategory(categoryId).getName());

        libraryPager = (ViewPager) findViewById(R.id.pager);
        libraryPagerAdapter = new LibraryPagerAdapter(getSupportFragmentManager());
        libraryPagerAdapter.setExcusesInCategory(excuses);
        libraryPager.setAdapter(libraryPagerAdapter);

        pageListener = new PageListener();
        libraryPager.setOnPageChangeListener(pageListener);

        countView = (TextView) findViewById(R.id.textView3);
        countView.setText("1/"+numberOfExcuses);

        ratingButton.setCurrentRating(excuses.get(0).getApprovals());
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
            ratingButton.setCurrentRating(excuses.get(position).getApprovals());
            ratingButton.invalidate();
            countView.setText(position+1+"/"+numberOfExcuses);
        }
    }
}

