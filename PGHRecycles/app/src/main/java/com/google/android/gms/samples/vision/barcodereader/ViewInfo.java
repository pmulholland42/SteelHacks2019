package com.google.android.gms.samples.vision.barcodereader;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.samples.vision.barcodereader.awsDb.DynamoInteractions;
import com.google.android.gms.samples.vision.barcodereader.awsDb.LocationMapDo;

import java.util.concurrent.ExecutionException;

public class ViewInfo extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);

        /////// AWS STUFF ///////////
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();
        ////// AWS STUFF //////

        String location = MainActivity.locationString;

        for (int i = 1; i <= 7; i++)
        {
            ImageView currView = (ImageView) findViewById(getResources().getIdentifier("type" + i, "id", getPackageName()));
            // Query DB for this plastic type
            // SELECT * FROM LocationMap WHERE type_id = i AND municipality = location

            Cursor c = MainActivity.dbRead.rawQuery("SELECT * FROM LocationMap WHERE type_id = ? AND municipality = ?", new String[] {Integer.toString(i), location} );

            LocationMapDo maybeLocation = null;
            try {
                maybeLocation = DynamoInteractions.getLocMap(location, dynamoDBMapper);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (maybeLocation!= null) // If we get a result
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
