package com.example.dhritiwasan.nightlife;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectionCurrent extends AppCompatActivity {

    ListView list;
    String curr;
    double credit;
    boolean[] visitingLocations;
    TextView Text;
    final String[] locations = {"Marina Bay Sands", "Singapore Flyer", "Vivocity Singapore", "Resorts World Sentosa", "Orchard Road", "Zouk Singapore"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent openingIntent = getIntent();
        visitingLocations = openingIntent.getBooleanArrayExtra("LOCATIONS");
        credit = openingIntent.getDoubleExtra("CREDIT", 0.0);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_selection_current);
        Button nextButton = (Button) findViewById(R.id.getStarted);
        Text = (TextView) findViewById(R.id.textView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");
        //Typeface font1 = Typeface.createFromAsset(getAssets(), "minimal.otf");
        nextButton.setTypeface(custom_font);
        Text.setTypeface(custom_font);
        CustomAdapter places = new CustomAdapter();
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(places);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("dhriit", "button clicked");
                for (int i = 0; i < list.getChildCount(); i++) {
                    CheckBox cb = ((CheckBox)list.getChildAt(i).findViewById(R.id.label));
                    if (cb.isChecked()) {
                        curr = cb.getText().toString();
                    }
                }
                startMapActivity(view, visitingLocations, credit);
            }
        });
    }
    public void startMapActivity(View view, boolean[] visitingLocations, double credit) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("HOTEL", curr);
        intent.putExtra("LOCATIONS", visitingLocations);
        intent.putExtra("CREDIT", credit);
        startActivity(intent);
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
                                for (int j = 0; j < list.getChildCount(); j++) {
                                    ((CompoundButton)list.getChildAt(j)).setChecked(false);
                                }
                                compoundButton.setChecked(true);
                            }
                        }
                    }
            );
            return view;
        }
    }
}
