package com.example.ursakter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import custom.views.NextButton;
import custom.views.PreviousButton;
import custom.views.RatingButton;

public class ExcuseActivity extends Activity {
    private Random rand;
	private ArrayList<Integer> used = new ArrayList<Integer>();
    private Excuse current;
    private Excuse previous;
    private TextView text;
    private DBHandler dbHandler;
    private NextButton nextButton;
    private PreviousButton prevButton;
    private RatingButton ratingButton;
    private int numOfExc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_excuse);
        text = (TextView)findViewById(R.id.textView1);
        nextButton = (NextButton)findViewById(R.id.next_btn);
        prevButton = (PreviousButton)findViewById(R.id.previous_btn);
        prevButton.setEnabled(false);
        ratingButton = (RatingButton)findViewById(R.id.rating_button);

        dbHandler = new DBHandler(this);
        initDB();
        numOfExc = dbHandler.countExcuses();
        try {
           text.setText(Randomize().getText());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.randomization, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public Excuse Randomize() throws IOException{
        int num;
        this.previous = current;

        do{
            rand = new Random();
            num = rand.nextInt(numOfExc)+1;
        }while(used.contains(num) && used.size()<numOfExc);

        if(used.size() == numOfExc){
            initDB();
            used = new ArrayList<Integer>();
        }

        Excuse randomExcuse = dbHandler.getExcuse(num);

        this.used.add(randomExcuse.getId());
        this.current = randomExcuse;

        updateView();

        return randomExcuse;
	}
	 
	public void loadNewExcuse(View view){
        if(!prevButton.isEnabled()){
            prevButton.setEnabled(true);
            prevButton.setPos();
            prevButton.invalidate();
        }
        if(used.size() == 1){
        }
		try {
            text.setText(Randomize().getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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

    public void getPrevious(View view){
        text.setText(previous.getText());
        ratingButton.setCurrentRating(previous.getApprovals());
        ratingButton.invalidate();
        current = previous;
        previous = null;
        prevButton.setEnabled(false);
        prevButton.setNeg();
        prevButton.invalidate();
    }

    public void rateCurrent(View view){
        current.setApprovals(current.getApprovals()+1);
        updateView();
    }
	
	public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void updateView(){
        ratingButton.setCurrentRating(current.getApprovals());
        saveCurrent();
        ratingButton.invalidate();
    }

    private void saveCurrent(){
        dbHandler.updateExcuse(current);
    }
	
	
}
