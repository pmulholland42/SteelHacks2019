package com.google.android.gms.samples.vision.barcodereader;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        String location = MainActivity.locationString;

        for (int i = 1; i <= 11; i++)
        {
            ImageView currView = (ImageView) findViewById(getResources().getIdentifier("type" + i, "id", getPackageName()));
            // Query DB for this plastic type
            // SELECT * FROM LocationMap WHERE type_id = i AND municipality = location

            Cursor c = MainActivity.dbRead.rawQuery("SELECT * FROM LocationMap WHERE type_id = ? AND municipality = ?", new String[] {Integer.toString(i), location} );

            if (c.moveToNext()) // If we get a result
            {
                currView.setImageResource(R.drawable.check);
            }
            else
            {
                currView.setImageResource(R.drawable.x);
            }
        }
    }
}
