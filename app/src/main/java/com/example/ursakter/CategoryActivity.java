package com.example.ursakter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class CategoryActivity extends Activity {
    //DBHandler dbHandler;
    //LinearLayout categoryScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        /*
        categoryScroll = (LinearLayout)findViewById(R.id.categoryScroll);
        dbHandler = new DBHandler(this);
        initDB();
        ArrayList<Category> categories = dbHandler.fetchCategories();

        for(Category category : categories){
            TextView categoryLabel =  new TextView(this);
            categoryLabel.setText(category.getName());
            categoryLabel.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    )
            );
            categoryLabel.setId(category.getId());
            categoryLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView)v;
                    showCategory(textView.getId());
                }
            });

            categoryScroll.addView(categoryLabel);


        }
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }


    /*
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
    */

    public void showCategory(View view){

        Intent intent = new Intent(this, LibraryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("categoryId", Integer.valueOf((String)view.getTag()));
        startActivity(intent);
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
