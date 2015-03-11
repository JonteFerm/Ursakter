package com.example.ursakter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jonathan on 2015-03-06.
 */
public class Excuse {
    private int id;
    private String text;
    private ArrayList<Category> categories;
    private int approvals;

    public Excuse(int id, String text, int approvals){
        this.id = id;
        this.text = text;
        //this.categories = new ArrayList<Category>(Arrays.asList(categories));
        this.approvals = approvals;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public String getCategoriesAsString(){
        //TODO convert arraylist to string
        return null;
    }

    public int getApprovals() {
        return approvals;
    }
}
