package com.example.foodkeepproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class GroceryItemAddActivity extends AppCompatActivity {

    private static final String[] PRODUCE = new String[] {
            "Alfalfa Sprouts", "Apple", "Apricot", "Artichoke", "Asian Pear", "Asparagus", "Atemoya", "Avocado", "Bamboo Shoots", "Banana", "Bean Sprouts", "Beans", "Beets", "Belgian Endive", "Bell Peppers", "Bitter Melon", "Blackberries", "Blueberries", "Bok Choy", "Boniato", "Boysenberries", "Broccoflower", "Broccoli", "Brussels Sprouts", "Cabbage", "Cactus Pear", "Cantaloupe", "Carambola", "Carrots", "Casaba Melon", "Cauliflower", "Celery", "Chayote", "Cherimoya", "Cherries", "Coconuts", "Collard Greens", "Corn", "Cranberries", "Cucumber", "Dates", "Dried Plums", "Eggplant", "Endive", "Escarole", "Feijoa", "Fennel", "Figs", "Garlic", "Gooseberries", "Grapefruit", "Grapes", "Green Beans", "Green Onions", "Greens", "Guava", "Hominy", "Honeydew Melon", "Horned Melon", "Iceberg Lettuce", "Jerusalem Artichoke", "Jicama", "Kale", "Kiwifruit", "Kohlrabi", "Kumquat", "Leeks", "Lemons", "Lettuce", "Lima Beans", "Limes", "Longan", "Loquat", "Lychee", "Madarins", "Malanga", "Mandarin Oranges", "Mangos", "Mulberries", "Mushrooms", "Napa", "Nectarines", "Okra", "Onion", "Oranges", "Papayas", "Parsnip", "Passion Fruit", "Peaches", "Pears", "Peas", "Peppers", "Persimmons", "Pineapple", "Plantains", "Plums", "Pomegranate", "Potatoes", "Prickly Pear", "Prunes", "Pummelo", "Pumpkin", "Quince", "Radicchio", "Radishes", "Raisins", "Raspberries", "Red Cabbage", "Rhubarb", "Romaine Lettuce", "Rutabaga", "Shallots", "Snow Peas", "Spinach", "Sprouts", "Squash", "Strawberries", "String Beans", "Sweet Potato", "Tangelo", "Tangerines", "Tomatillo", "Tomato", "Turnip", "Ugli Fruit", "Water Chestnuts", "Watercress", "Watermelon", "Waxed Beans", "Yams", "Yellow Squash", "Yuca/Cassava", "Zucchini Squash"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item_add);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PRODUCE);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.itemName);
        textView.setThreshold(1);
        textView.setAdapter(adapter);
    }

    public void confirmAdd(View view) {
        AutoCompleteTextView name = (AutoCompleteTextView) findViewById(R.id.itemName);
        EditText count = (EditText) findViewById(R.id.pickerNumber);
        SwitchMaterial switchMaterial = (SwitchMaterial) findViewById(R.id.favoritesSwitch);
        Intent resultIntent = new Intent();

        String nameString = name.getText().toString();

        if (nameString.equals("")) {
            setResult(RESULT_CANCELED);
        } else {
            resultIntent.putExtra("name", toTitleCase(nameString));
            resultIntent.putExtra("favorite", switchMaterial.isChecked());
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