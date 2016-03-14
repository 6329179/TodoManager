/**
 * Narender Latchmansingh
 * 10073264
 * Native App Studio - Android.
 */

package com.onzin.todomanager;


import android.content.Context;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 11-3-2016.
 */
public class TodoManager {


    // fields: properties
    boolean isLastScreenHome;
    int listIdEnumerator;
    List<TodoList> todoLists;
    List<String> todoListsArray;
    int listPosition = -500;


    // the one single instance for the entire app
    private static TodoManager ourInstance = new TodoManager();

    // method to get the one single instance
    public static TodoManager getInstance() {
        return ourInstance;
    }

    //constructor
    public TodoManager() {
        listIdEnumerator  = 0;
        todoLists = new ArrayList<TodoList>();
        todoListsArray = new ArrayList<String>();
    }

    // other methods

    // Method to get a unique list id number
    public int getNewListId(){
        listIdEnumerator = listIdEnumerator + 1;
        return listIdEnumerator;

    }

    // Function to add new todolist
    public boolean addNewTodoList(String newListName){

        boolean duplicate = false;
        for(int i = 0; i < todoLists.size(); i++){
            if (newListName == todoLists.get(i).listName){
                duplicate = true;
            }
        }

        if (!duplicate){
            TodoList newList = new TodoList(newListName);
            todoLists.add(newList);
        }

        return duplicate;
    }

    // function to retrieve the headers of each list
    public List<String> readTodos(){

        todoListsArray.clear();
        for (int i = 0; i < todoLists.size(); i++){
            todoListsArray.add(todoLists.get(i).listName.toString());
        }

        return todoListsArray;

    }

    // function to search the right id with the clicked position
    public int searchListPosition(int position, AdapterView<?> parent, String positionItem)
    {
        for (int i = 0; i< todoLists.size(); i++) {
            if (todoLists.get(i).listName == positionItem) {
                listPosition = i;
                break;
            }
        }
        return listPosition;
    }

    // function to remove the a todolist
    public void removeList(int position, AdapterView<?> parent, String positionItem){

        for (int i = 0; i< todoLists.size(); i++){
            if (todoLists.get(i).listName == positionItem) {
                listPosition = i;
                break;
            }
        }

        String fileName = todoLists.get(listPosition).listFileName;
        File file = new File(fileName);
//                    PrintStream out = new PrintStream(context.openFileOutput("todoManager.txt", Context.MODE_PRIVATE));
        file.delete();
        todoLists.remove(listPosition);
    }

    // function to delete all todolists
    public void clearAllTodo(){

            // creating text string by first deleting the whole file.
            for (int i = 0; i < todoLists.size(); i++) {
                String fileName = todoLists.get(i).listFileName;
                File todoFile = new File(fileName);
//                    PrintStream out = new PrintStream(context.openFileOutput("todoManager.txt", Context.MODE_PRIVATE));
                todoFile.delete();
                todoLists.get(i).todoItems.clear();
            }

            File managerFile = new File("todoManager.txt");
            managerFile.delete();
            todoLists.clear();
            listIdEnumerator = 0;

    }

}
