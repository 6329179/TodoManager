/**
 * Narender Latchmansingh
 * 10073264
 * Native App Studio - Android.
 */

package com.onzin.todomanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoListActivity extends AppCompatActivity {

    // initialize variables, adapter, view list etc
    List<String> todoItemArray = new ArrayList<String>();
    List<String> todoItemsNames = new ArrayList<String>();

    EditText todoItemEdit;
    Button todoItemButton;
    Button clearButton;
    Button backButton;
    ListView todoItemList;
    TextView listNameText;

    ArrayAdapter<String> todoItemAdapter;

    String listName;
    int listPosition;

    TodoManager manager;
    TodoList todoList;
    String fileName;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // collect the extras put in to this activity and instances
        Bundle extras = getIntent().getExtras();
        listPosition = extras.getInt("listPosition", listPosition);
        listName = extras.getString("listName", listName);

        manager = TodoManager.getInstance();
        todoList = manager.todoLists.get(listPosition);
        fileName = todoList.listFileName;

        // assign to each view a variable name
        todoItemEdit = (EditText) findViewById(R.id.todoItemEdit);
        todoItemButton = (Button) findViewById(R.id.todoItemButton);
        clearButton = (Button) findViewById(R.id.clearItemListButton);
        backButton = (Button) findViewById(R.id.backButton);
        todoItemList = (ListView) findViewById(R.id.todoItemList);
        listNameText = (TextView) findViewById(R.id.listNameText);

        // changes the header text
        listNameText.setText(listName);

        // setting up adapter and link it to the data Array
        todoItemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItemArray);

        // linking adapter to the ListView
        todoItemList.setAdapter(todoItemAdapter);

        // load the todoitems from file
        loadTodoListsFromFile(fileName);

        // set a event on button click to add the edittect content
        todoItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (todoItemEdit.getText().toString() == "") sendToast("insert a to-do item");
                else {
                    todoList.addNewTodoItem(todoItemEdit.getText().toString());
                    todoItemAdapter.notifyDataSetChanged();
                    loadTodoItems();
                    todoItemEdit.setText("");
                    storeTodoItems();
                }
            }
        });

        // set an event on button click to clear the list
        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clearTodoItems();
                loadTodoItems();
                storeTodoItems();
                todoItemAdapter.notifyDataSetChanged();
                sendToast("todo list cleared");


            }
        });

        // set an event on back button
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(TodoListActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();

            }
        });

        // create a longclick event on a list item to remove item
        todoItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String positionItem = parent.getItemAtPosition(position).toString();

                todoList.todoItems.remove(position);
                loadTodoItems();
                storeTodoItems();
                todoItemAdapter.notifyDataSetChanged();
                return false;

            }
        });




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    // function to display a popup messgage
    public void sendToast(String message) {

        // show toast message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // function to load the old to-do list from a text file
    public void loadTodoListsFromFile(String fileName) {

        todoItemArray.clear();

        // read a file and store each line in array
        try {
            Scanner scan = new Scanner(openFileInput(fileName));

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                todoList.addNewTodoItem(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        loadTodoItems();

    }


    // function to load the old to-do list from a text file
    public void loadTodoItems() {

        todoItemsNames = todoList.readItems();
        todoItemArray.clear();
        for (int i = 0; i < todoItemsNames.size(); i++) {
            todoItemArray.add(todoItemsNames.get(i).toString());
        }

    }

    // functie that stores array to txtfile
    public void storeTodoItems() {

        // write to a file
        PrintStream out = null;
        try {
            out = new PrintStream(openFileOutput(fileName, MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // creating text string by first deleting the whole file.
        if (!todoItemArray.isEmpty()) {
            for (int i = 0; i < todoItemArray.size(); i++) {
                out.println(todoItemArray.get(i).toString());
            }
        }

        out.close();
    }


    // function to clear all todoItems
    public void clearTodoItems(){

        File ItemsFile = new File(fileName);
        ItemsFile.delete();
        todoList.todoItems.clear();

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TodoList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.onzin.todomanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TodoList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.onzin.todomanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
