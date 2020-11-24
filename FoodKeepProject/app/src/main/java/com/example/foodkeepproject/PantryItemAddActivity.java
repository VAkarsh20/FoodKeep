package com.example.foodkeepproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class PantryItemAddActivity extends AppCompatActivity {

    private static final String[] PRODUCE = new String[] {
            "Alfalfa Sprouts", "Apple", "Apricot", "Artichoke", "Asian Pear", "Asparagus", "Atemoya", "Avocado", "Bamboo Shoots", "Banana", "Bean Sprouts", "Beans", "Beets", "Belgian Endive", "Bell Peppers", "Bitter Melon", "Blackberries", "Blueberries", "Bok Choy", "Boniato", "Boysenberries", "Broccoflower", "Broccoli", "Brussels Sprouts", "Cabbage", "Cactus Pear", "Cantaloupe", "Carambola", "Carrots", "Casaba Melon", "Cauliflower", "Celery", "Chayote", "Cherimoya", "Cherries", "Coconuts", "Collard Greens", "Corn", "Cranberries", "Cucumber", "Dates", "Dried Plums", "Eggplant", "Endive", "Escarole", "Feijoa", "Fennel", "Figs", "Garlic", "Gooseberries", "Grapefruit", "Grapes", "Green Beans", "Green Onions", "Greens", "Guava", "Hominy", "Honeydew Melon", "Horned Melon", "Iceberg Lettuce", "Jerusalem Artichoke", "Jicama", "Kale", "Kiwifruit", "Kohlrabi", "Kumquat", "Leeks", "Lemons", "Lettuce", "Lima Beans", "Limes", "Longan", "Loquat", "Lychee", "Madarins", "Malanga", "Mandarin Oranges", "Mangos", "Mulberries", "Mushrooms", "Napa", "Nectarines", "Okra", "Onion", "Oranges", "Papayas", "Parsnip", "Passion Fruit", "Peaches", "Pears", "Peas", "Peppers", "Persimmons", "Pineapple", "Plantains", "Plums", "Pomegranate", "Potatoes", "Prickly Pear", "Prunes", "Pummelo", "Pumpkin", "Quince", "Radicchio", "Radishes", "Raisins", "Raspberries", "Red Cabbage", "Rhubarb", "Romaine Lettuce", "Rutabaga", "Shallots", "Snow Peas", "Spinach", "Sprouts", "Squash", "Strawberries", "String Beans", "Sweet Potato", "Tangelo", "Tangerines", "Tomatillo", "Tomato", "Turnip", "Ugli Fruit", "Water Chestnuts", "Watercress", "Watermelon", "Waxed Beans", "Yams", "Yellow Squash", "Yuca/Cassava", "Zucchini Squash"
    };

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_item_add);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PRODUCE);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.itemName);
        textView.setThreshold(1);
        textView.setAdapter(adapter);

        TextView date = (TextView) findViewById(R.id.datePicker);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormat.format(calendar.getTime());
        date.setText(strDate);

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PantryItemAddActivity.this, datePicker, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    private void updateLabel() {
        TextView date = (TextView) findViewById(R.id.datePicker);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormat.format(calendar.getTime());
        date.setText(strDate);
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
        AutoCompleteTextView name = (AutoCompleteTextView) findViewById(R.id.itemName);
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
            resultIntent.putExtra("name", toTitleCase(nameString));
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

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}