package com.example.foodkeepproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class ConsumeItemActivity extends AppCompatActivity {

    private int count;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_item);

        Intent intent = getIntent();
        name = intent.getStringExtra("ITEM_NAME");
        count = intent.getIntExtra("ITEM_COUNT", 0);

        TextView itemCount = (TextView) findViewById(R.id.itemCount);
        TextView itemDescription = (TextView) findViewById(R.id.itemDescription);

        itemCount.setText(String.valueOf(count));
        itemDescription.setText(name + "s in Pantry");
    }

    public void increment(View view) {
        TextView count = (TextView) findViewById(R.id.pickerNumber);
        int curCount = Integer.parseInt(count.getText().toString());
        if (curCount < this.count) {
            curCount += 1;
            count.setText(String.valueOf(curCount));
        }
    }

    public void decrement(View view) {
        TextView count = (TextView) findViewById(R.id.pickerNumber);
        int curCount = Integer.parseInt(count.getText().toString());
        if (curCount > 0) {
            curCount -= 1;
            count.setText(String.valueOf(curCount));
        }
    }

    public void confirmConsume(View view) {
        TextView count = (TextView) findViewById(R.id.pickerNumber);
        Intent resultIntent = new Intent();

        int countInt = Integer.parseInt(count.getText().toString());

        resultIntent.putExtra("name", name);
        resultIntent.putExtra("count", countInt);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}