package com.example.arohatg.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvtItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvtItems = (ListView)findViewById(R.id.lvItems);
        todoItems = new ArrayList<String>();
        populateArrayItems();
        lvtItems.setAdapter(aToDoAdapter);

        setupListViewListener();
        setupItemClickListener();
    }

    private void setupItemClickListener(){
        lvtItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                    Bundle  bundle = new Bundle();
                    bundle.putString("a", lvtItems.getAdapter().getItem(position).toString());
                    i.putExtras(bundle);
                    startActivity(i);
            }

        });
    }


    private void setupListViewListener(){
        lvtItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    public void onAddItem(View view){
        EditText etEditText = (EditText)findViewById(R.id.etEditText);
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e){
             e.printStackTrace();
        }
    }
}
