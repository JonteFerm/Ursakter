package com.example.ursakter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import custom.views.MagicPager;
import custom.views.PreviousButton;
import custom.views.RatingButton;

public class RandomExcusesActivity  extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private PreviousButton previousButton;
    private RatingButton ratingButton;
    private ArrayList<Excuse> excuses;
    private Excuse current;
    private int lastPos = 0;
    private DBHandler dbHandler = new DBHandler(this);
    private MagicPager randomPager;
    private ExcusePagerAdapter excusePagerAdapter;
    private PageListener pageListener;

    private int stack1;
    private int stack2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_excuses);
        initDB();
        previousButton = (PreviousButton) findViewById(R.id.previous_btn);
        previousButton.setNeg();
        ratingButton = (RatingButton) findViewById(R.id.rating_button);

        excuses = dbHandler.getAllExcuses();
        long seed = System.nanoTime();
        Collections.shuffle(excuses, new Random(seed));

        randomPager = (MagicPager)findViewById(R.id.pagerz);
        excusePagerAdapter = new ExcusePagerAdapter(getSupportFragmentManager());
        excusePagerAdapter.setExcuses(excuses);
        randomPager.setAdapter(excusePagerAdapter);

        pageListener = new PageListener();
        randomPager.setOnPageChangeListener(pageListener);

        ratingButton.setCurrentRating(excuses.get(0).getApprovals());
        ratingButton.invalidate();

        stack1 = excuses.size();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(int position){
        randomPager.setCurrentItem(position);
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
        randomPager.setCurrentItem(randomPager.getCurrentItem() + 1);
    }

    public void getPrevious(View view){
        randomPager.setCurrentItem(randomPager.getCurrentItem() - 1);
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
    private class PageListener extends MagicPager.SimpleOnPageChangeListener{
        public void onPageSelected(int position){
            /* **MAGIC**
            ----------------
            */
            if((lastPos == 0 && position == 1) || position > lastPos || (position == lastPos && stack2 - position > stack2 - lastPos)){
                if(previousButton.isNeg()){
                    previousButton.setPos();
                    previousButton.invalidate();
                }

                if(stack2 > 5){
                    stack1++;
                    stack2--;
                }else{
                    stack2++;
                    stack1--;
                }
            }else if(position < lastPos || (position == lastPos && stack2 - position < stack2 - lastPos)){
                if(stack2 != 1){
                    stack1++;
                    stack2--;
                }
            }

            if(!(lastPos == 0 && position == 1) && stack2 == 1){
                previousButton.setNeg();
                previousButton.invalidate();
                //randomPager.setPagingEnabled(false);
            }

            lastPos = position;
            current = excuses.get(position);
            ratingButton.setCurrentRating(excuses.get(position).getApprovals());
            ratingButton.invalidate();
        }
    }
}
