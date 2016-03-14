package com.onzin.todomanager;

import java.util.ArrayList;
import java.util.List;

/**
 * Narender Latchmansingh
 * 10073264
 * Native App Studio - Android.
 */

public class TodoItem {

    // fields
    String itemName;
    int itemId;
    TodoManager manager = TodoManager.getInstance();


    // constructor
    public TodoItem(String itemNameInput){
        itemName = itemNameInput;
    }

    // other methods

    // function to retrieve the item name
    public String getItemName(){
        return itemName;
    }




}
