package com.example.arohatg.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    EditText etText;
    ArrayAdapter<String> aToDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Bundle bundle = getIntent().getExtras();
        etText = (EditText)findViewById(R.id.etText);
        etText.setText(bundle.getString("a"));
        onSetFocusChangeListener();
    }


    private void onSetFocusChangeListener(){

        etText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //etText.setTextColor(ColorStateList.valueOf(999));
                if(hasFocus) {
                    etText.setSelection(((TextView)v).getText().length());
                }
             }
        });
    }

    public void onSaveItem(View view){
        EditText etText = (EditText)findViewById(R.id.etText);
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("a", etText.getText().toString());
        data.putExtra("code", 200);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent


    }




}
