package com.example.arohatg.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    ArrayAdapter<String> aToDoAdapter;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Bundle bundle = getIntent().getExtras();
        etEditText = (EditText)findViewById(R.id.etEditText);
        etEditText.setText(bundle.getString("a"));

    }
}
