package com.josh2.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText editText;
    int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        noteIndex = intent.getIntExtra("position", -1);

        editText = (EditText) findViewById(R.id.editText);

        if(noteIndex != -1) {
            editText.setText(MainActivity.notes.get(noteIndex));
        } else {
            MainActivity.notes.add("");
            editText.setText("");
            noteIndex = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteIndex, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                sharedPreferences = getApplicationContext().getSharedPreferences("com.josh2.notes", Context.MODE_PRIVATE);
                try {
                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*sharedPreferences = this.getSharedPreferences("com.josh2.notes", Context.MODE_PRIVATE);
        try {
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
