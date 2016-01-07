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

import custom.views.Button;
import custom.views.PreviousButton;
import custom.views.RatingButton;

public class FavouriteActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private TextView countView;
    private int numberOfExcuses;
    private android.widget.Button previousButton;
    private android.widget.Button ratingButton;
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
        previousButton = (android.widget.Button) findViewById(R.id.previous_btn);
        //previousButton.setBackgroundResource(R.drawable.ui_app_btn_back_neg);
        ratingButton = (android.widget.Button) findViewById(R.id.rating_button);
        favouritePager = (ViewPager) findViewById(R.id.pager);
        excuses = dbHandler.getFavouriteExcuses(getIntent().getIntExtra("approvals", 5));
        countView = (TextView) findViewById(R.id.textView3);


        excusePagerAdapter = new ExcusePagerAdapter(getSupportFragmentManager());

        if(excuses != null){
            numberOfExcuses = excuses.size();
            excusePagerAdapter.setExcuses(excuses);
            current = excuses.get(0);
            setCurrentRating(current.getApprovals());
            countView.setText("1/"+numberOfExcuses);
            favouritePager.setAdapter(excusePagerAdapter);
            pageListener = new PageListener();
            favouritePager.setOnPageChangeListener(pageListener);
        }else{
            setCurrentRating(0);
        }
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

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class PageListener extends ViewPager.SimpleOnPageChangeListener{
        public void onPageSelected(int position){
            if(position == 0){
                previousButton.setBackgroundResource(R.drawable.ui_app_btn_back_neg);
                previousButton.invalidate();
            }else{
                previousButton.setBackgroundResource(R.drawable.ui_app_btn_back);
                previousButton.invalidate();
            }
            current = excuses.get(position);
            setCurrentRating(excuses.get(position).getApprovals());
            ratingButton.invalidate();
            countView.setText(position+1+"/"+numberOfExcuses);
        }
    }
}
