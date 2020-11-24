package com.example.foodkeepproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PantryItemShoppingAddActivity extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_item_shopping_add);

        TextView textView = (TextView) findViewById(R.id.itemName);
        textView.setText(getIntent().getStringExtra("name"));

        //TextView date = (TextView) findViewById(R.id.datePicker);
        //DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //String strDate = dateFormat.format(calendar.getTime());
        //date.setText(strDate);
    }

    public void increment(View view) {
        EditText count = (EditText) findViewById(R.id.pickerNumber);
        int curCount = Integer.parseInt(count.getText().toString());
        curCount += 1;
        count.setText(String.valueOf(curCount));
    }

    public void decrement(View view) {
        EditText count = (EditText) findViewById(R.id.pickerNumber);
        int curCount = Integer.parseInt(count.getText().toString());
        if (curCount > 0) {
            curCount -= 1;
            count.setText(String.valueOf(curCount));
        }
    }

    public void confirmAdd(View view) {
        TextView name = (TextView) findViewById(R.id.itemName);
        EditText count = (EditText) findViewById(R.id.pickerNumber);
        Intent resultIntent = new Intent();

        Slider freshness = (Slider) findViewById(R.id.freshnessSlider);

        if (freshness.getValue() == 0.0f) {
            calendar.add(Calendar.DAY_OF_MONTH, 20);
        } else if (freshness.getValue() == 1.0f) {
            calendar.add(Calendar.DAY_OF_MONTH, 15);
        } else if (freshness.getValue() == 2.0f) {
            calendar.add(Calendar.DAY_OF_MONTH, 10);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 5);
        }

        Date expiryDate = calendar.getTime();

        String nameString = name.getText().toString();
        int countInt = Integer.parseInt(count.getText().toString());

        if (nameString.equals("") || countInt == 0) {
            setResult(RESULT_CANCELED);
        } else {
            resultIntent.putExtra("name", nameString);
            resultIntent.putExtra("count", countInt);
            resultIntent.putExtra("expiryDate", expiryDate.getTime());
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}