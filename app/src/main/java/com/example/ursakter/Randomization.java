package com.example.ursakter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Randomization extends ActionBarActivity{
    private Random rand;
    DBHandler dbHandler;
	private ArrayList<Integer> used = new ArrayList<Integer>();
    private int current;
    private TextView text;
    private Button newButton;
    private int numOfExc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_randomization);
        text = (TextView)findViewById(R.id.textView1);
        newButton = (Button)findViewById(R.id.newButton);
        dbHandler = new DBHandler(this);

        try {
            dbHandler.createDB();

            dbHandler.openDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        do{
            rand = new Random();
            num = rand.nextInt(numOfExc)+1;
        }while(used.contains(num) && used.size()<numOfExc);

        if(used.size() == numOfExc){
            newButton.setText("Du har sett alla. Fortsätt?");
            used = new ArrayList<Integer>();
        }

        Excuse randomExcuse = dbHandler.getExcuse(num);
        this.current = num;
        this.used.add(num);

        return randomExcuse;
	}
	 
	public void getAnother(View view){
        if(used.size() == 1){
            newButton.setText("Ny ursäkt");
        }
		try {
            text.setText(Randomize().getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

    public void getPrevious(View view){


    }
	
	
	
	
}
