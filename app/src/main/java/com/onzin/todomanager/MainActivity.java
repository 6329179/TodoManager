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

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    // initialize variables, adapter, view list etc
    List<String> todoArray = new ArrayList<String>();
    List<String> todoListsNames = new ArrayList<String>();

    EditText todoEdit;
    Button todoButton;
    Button clearButton;
    ListView todoList;

    ArrayAdapter<String> todoAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    TodoManager manager = TodoManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // assign to each view a variable name
        todoEdit = (EditText) findViewById(R.id.todoEdit);
        todoButton = (Button) findViewById(R.id.todoButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        todoList = (ListView) findViewById(R.id.todoList);

        // setting up adapter and link it to the data Array
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoArray);

        // linking adapter to the ListView
        todoList.setAdapter(todoAdapter);

          // load the todolists from file
        loadTodoListsFromFile();

        // create a longclick event on a list item to remove item
        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String positionItem = parent.getItemAtPosition(position).toString();
                manager.removeList(position, parent, positionItem);
                loadTodoLists();
                storeTodo();
                todoAdapter.notifyDataSetChanged();
                return false;

            }
        });

        // set a click listener to go to the item list of the clicked to do list
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent todoListIntent = new Intent(MainActivity.this, TodoListActivity.class);
                String positionItem = parent.getItemAtPosition(position).toString();
                todoListIntent.putExtra("listName", positionItem);
                int listPosition = manager.searchListPosition(position, parent,positionItem);
                todoListIntent.putExtra("listPosition", listPosition);

                startActivity(todoListIntent);
                finish();
            }

        });

        // set a event on button click to add the edittect content
        todoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (todoEdit.getText().toString() == "") sendToast("insert a to-do item");
                else {

                    boolean duplicate;
                    duplicate = manager.addNewTodoList(todoEdit.getText().toString());
                    if (duplicate){
                        sendToast("List name already exist!");
                    }
                    todoAdapter.notifyDataSetChanged();
                    loadTodoLists();
                    todoEdit.setText("");
                    storeTodo();
                }
            }

        });

        // set an event on button click to clear the todolists
        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                manager.clearAllTodo();
                loadTodoLists();
                storeTodo();
                todoAdapter.notifyDataSetChanged();

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    // function to load the old to-do list from a text file
    public void loadTodoListsFromFile() {

        manager.todoLists.clear();

        // read a file and store each line in array
        try {
            Scanner scan = new Scanner(openFileInput("todoManager.txt"));

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                manager.addNewTodoList(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        loadTodoLists();

    }

    // function to reload the todoList in to array for the adapter
    public void loadTodoLists(){
        todoListsNames = manager.readTodos();
        todoArray.clear();
        for (int i = 0; i < todoListsNames.size(); i++){
            todoArray.add(todoListsNames.get(i).toString());
        }

    }

    // functie that stores array to txtfile
    public void storeTodo() {

        // write to a file
        PrintStream out = null;
        try {
            out = new PrintStream(openFileOutput("todoManager.txt", MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // creating text string by first deleting the whole file.
        if (!todoArray.isEmpty()) {
            for (int i = 0; i < todoArray.size(); i++) {
                out.println(todoArray.get(i).toString());
            }
        }

        out.close();
    }



    // function to display a popup messgage
    public void sendToast(String message) {

        // show toast message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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

