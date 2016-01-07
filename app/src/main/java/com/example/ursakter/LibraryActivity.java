package com.example.ursakter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import custom.views.Button;
import custom.views.PreviousButton;
import custom.views.RatingButton;

public class LibraryActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private TextView countView;
    private int numberOfExcuses;
    private android.widget.Button previousButton;
    private android.widget.Button ratingButton;
    private Excuse current;
    private ArrayList<Excuse> excuses;

    private DBHandler dbHandler = new DBHandler(this);
    private ViewPager libraryPager;
    private ExcusePagerAdapter excusePagerAdapter;
    private TextView categoryNameView;
    private int categoryId;
    private PageListener pageListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initDB();
        previousButton = (android.widget.Button) findViewById(R.id.previous_btn);
        //previousButton.setBackgroundResource(R.drawable.ui_app_btn_back_neg);
        ratingButton = (android.widget.Button) findViewById(R.id.rating_button);
        categoryId = getIntent().getIntExtra("categoryId", 0);
        excuses = dbHandler.getExcusesByCategory(categoryId);
        numberOfExcuses = excuses.size();
        categoryNameView = (TextView) findViewById(R.id.textView1);
        categoryNameView.setText("Kategori: "+dbHandler.fetchCategory(categoryId).getName());

        libraryPager = (ViewPager) findViewById(R.id.pager);
        excusePagerAdapter = new ExcusePagerAdapter(getSupportFragmentManager());
        excusePagerAdapter.setExcuses(excuses);
        libraryPager.setAdapter(excusePagerAdapter);

        pageListener = new PageListener();
        libraryPager.setOnPageChangeListener(pageListener);

        countView = (TextView) findViewById(R.id.textView3);
        countView.setText("1/"+numberOfExcuses);

        current = excuses.get(0);
        setCurrentRating(current.getApprovals());
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

