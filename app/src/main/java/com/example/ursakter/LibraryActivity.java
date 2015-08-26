package com.example.ursakter;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class LibraryActivity extends FragmentActivity implements ExcuseFragment.OnFragmentInteractionListener{
    private DBHandler dbHandler = new DBHandler(this);
    private ViewPager libraryPager;
    private LibraryPagerAdapter libraryPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initDB();
        libraryPager = (ViewPager) findViewById(R.id.pager);
        libraryPagerAdapter = new LibraryPagerAdapter(getSupportFragmentManager());

        ArrayList<Excuse> excuses = dbHandler.getExcusesByCategory(getIntent().getIntExtra("categoryId", 0));

        libraryPagerAdapter.setExcusesInCategory(excuses);
        libraryPager.setAdapter(libraryPagerAdapter);

        /*
        for(Excuse excuse : excuses){
            System.out.println(excuse.getText());
        }*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
