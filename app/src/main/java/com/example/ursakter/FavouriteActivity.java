package com.example.ursakter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import custom.views.PreviousButton;
import custom.views.RatingButton;

public class FavouriteActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private TextView countView;
    private int numberOfExcuses;
    private PreviousButton previousButton;
    private RatingButton ratingButton;
    private Excuse current;
    private ArrayList<Excuse> excuses;

    private DBHandler dbHandler = new DBHandler(this);
    private ViewPager favouritePager;
    private ExcusePagerAdapter excusePagerAdapter;
    private PageListener pageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        initDB();
        previousButton = (PreviousButton) findViewById(R.id.previous_btn);
        previousButton.setNeg();
        ratingButton = (RatingButton) findViewById(R.id.rating_button);
        excuses = dbHandler.getFavouriteExcuses();
        numberOfExcuses = excuses.size();
        favouritePager = (ViewPager) findViewById(R.id.pager);
        excusePagerAdapter = new ExcusePagerAdapter(getSupportFragmentManager());
        excusePagerAdapter.setExcuses(excuses);
        favouritePager.setAdapter(excusePagerAdapter);

        pageListener = new PageListener();
        favouritePager.setOnPageChangeListener(pageListener);

        countView = (TextView) findViewById(R.id.textView3);
        countView.setText("1/"+numberOfExcuses);

        ratingButton.setCurrentRating(excuses.get(0).getApprovals());
    }


    @Override
    public void onFragmentInteraction(int position){
        favouritePager.setCurrentItem(position);
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
        favouritePager.setCurrentItem(favouritePager.getCurrentItem() + 1);
    }

    public void getPrevious(View view){
        favouritePager.setCurrentItem(favouritePager.getCurrentItem() - 1);
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
