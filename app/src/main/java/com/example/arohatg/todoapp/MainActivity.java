package com.example.arohatg.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvtItems;
    EditText etEditText;
    private final int REQUEST_CODE = 200;
    int editPosition;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvtItems = (ListView)findViewById(R.id.lvItems);
        todoItems = new ArrayList<String>();
        populateArrayItems();
        lvtItems.setAdapter(aToDoAdapter);
        setupListViewListener();
        setupItemClickListener();
    }

    /**
     *
     */
    private void setupItemClickListener(){
        lvtItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                    Bundle  bundle = new Bundle();
                    bundle.putString("a", lvtItems.getAdapter().getItem(position).toString());
                    i.putExtras(bundle);
                    editPosition = position;
                    startActivityForResult(i, REQUEST_CODE);
            }

        });
    }


    /**
     *
     */
    private void setupListViewListener(){
        lvtItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                todoItems.remove(position);
                todoItems.trimToSize();

                writeItems();
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    /**
     *
     */
    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    /**
     *
     */
    private void readItems(){
        File fileDir = getFilesDir();
        String filePath = fileDir.getAbsolutePath();
        File file = new File(fileDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    /**
     *
     * @param view
     */
    public void onAddItem(View view){
        EditText etEditText = (EditText)findViewById(R.id.etEditText);
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    /**
     *
     */
    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e){

        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("a");
            readItems();
            todoItems.set(editPosition, name);
            writeItems();
            populateArrayItems();
            lvtItems.setAdapter(aToDoAdapter);

            // Toast the name to display temporarily on screen
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }
}
