package com.example.dhritiwasan.nightlife;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    private Button start;
    private ListView list;
    private TextView text;
    private EditText et;
    public static boolean[] selectedPlaces = {false, false, false, false, false, false};
    private RadioButton checkbox;
    String[] locations = {"Marina Bay Sands", "Singapore Flyer", "Vivocity Singapore", "Resorts World Sentosa", "Orchard Road", "Zouk Singapore"};
    ArrayList<String> visitingLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_selection);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");
        start = (Button)findViewById(R.id.getStarted);
        start.setTypeface(custom_font);
        text = (TextView) findViewById(R.id.textView2);
        text.setTypeface(custom_font);
        et = (EditText) findViewById(R.id.editText);
        //CustomAdapter places = new CustomAdapter();
        //list = (ListView) findViewById(R.id.listView5);
        //list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //list.setAdapter(places);

    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return locations.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.list_view, null);
            CheckBox cb = (CheckBox) view.findViewById(R.id.label);
            cb.setText(locations[i]);
            cb.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (!compoundButton.isChecked()) {
                                System.out.println("setting to false");
                                compoundButton.setChecked(false);
                            } else {
                                System.out.println("setting to true");
                                compoundButton.setChecked(true);
                            }
                        }
                    }
            );
            return view;
        }
    }
    public void onClickButton(View view) {
        Log.i("dhriit", "button clicked");
        /*int count = 0;
        for (int i = 0; i < list.getChildCount(); i++) {
            selectedPlaces[i] = ((CheckBox)list.getChildAt(i).findViewById(R.id.label)).isChecked();
            if(selectedPlaces[i]) count++;
        }
        if (count!=0) {
            Intent intent = new Intent(this, SelectionCurrent.class);
            intent.putExtra("LOCATIONS", selectedPlaces);
            try {
                intent.putExtra("CREDIT", Double.parseDouble(et.getText().toString()));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Please enter a valid credit value", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(this, "Please select places to visit", Toast.LENGTH_LONG).show();
        }
        */
        try {
            Intent intent = new Intent(this, SelectionActivity2.class);
            intent.putExtra("CREDIT", Double.parseDouble(et.getText().toString()));
            intent.putExtra("LOCATIONS", selectedPlaces);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid credit value", Toast.LENGTH_LONG).show();
            Log.i("Shobhit", e.getMessage());
        }
    }
}
