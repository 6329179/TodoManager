package com.onzin.todomanager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Narender Latchmansingh
 * 10073264
 * Native App Studio - Android.
 */

public class TodoList {

    // fields
    String listName;
    int listId;
    TodoManager manager = TodoManager.getInstance();

    List<TodoItem> todoItems;
    List<String> todoItemsArray = new ArrayList<String>();
    int itemCount;

    String listFileName;

    // constructor
    public TodoList(String Name){
        listName = Name;
        listId = manager.getNewListId();
        listFileName = listName +".txt";
//        todoItems = new ArrayList<TodoItem>();
        itemCount = 0;

        todoItems = new ArrayList<TodoItem>();
        todoItemsArray = new ArrayList<String>();

    }

    // other methods

    // function to add a new TodoItem
    public void addNewTodoItem(String newItemName){

        TodoItem newItem = new TodoItem(newItemName);
        todoItems.add(newItem);

    }

    // function to retrieve the headers of each list
    public List<String> readItems(){

        todoItemsArray.clear();
        for (int i = 0; i < todoItems.size(); i++){
            todoItemsArray.add(todoItems.get(i).itemName.toString());
        }

        return todoItemsArray;

    }


    // add a todoitem to the array list todoItems
    public void addTodoItem(TodoItem newTodoItem) {
        todoItems.add(newTodoItem);
    }

}
