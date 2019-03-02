/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.barcodereader;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.provider.BaseColumns;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.security.AccessController.getContext;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;

    public static String locationString = "Unknown";

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    public static ItemDBHelper dbHelper;
    public static SQLiteDatabase dbWrite;
    public static SQLiteDatabase dbRead;

    DynamoDBMapper dynamoDBMapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_barcode).setOnClickListener(this);

        findViewById(R.id.view_info).setOnClickListener(this);
        final TextView locationTextView = (TextView) findViewById(R.id.current_location);

        dbHelper = new ItemDBHelper(this);
        System.out.println("Made dbhelper");
        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_TYPE_ID, 1);
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_MUNICIPALITY, "Pittsburgh");
        long newRowId = dbWrite.insert(ItemDatabaseContract.LocationMap.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_TYPE_ID, 2);
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_MUNICIPALITY, "Pittsburgh");
        newRowId = dbWrite.insert(ItemDatabaseContract.LocationMap.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_TYPE_ID, 3);
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_MUNICIPALITY, "Pittsburgh");
        newRowId = dbWrite.insert(ItemDatabaseContract.LocationMap.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_TYPE_ID, 4);
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_MUNICIPALITY, "Pittsburgh");
        newRowId = dbWrite.insert(ItemDatabaseContract.LocationMap.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_TYPE_ID, 5);
        values.put(ItemDatabaseContract.LocationMap.COLUMN_NAME_MUNICIPALITY, "Pittsburgh");
        newRowId = dbWrite.insert(ItemDatabaseContract.LocationMap.TABLE_NAME, null, values);
        dbRead = dbHelper.getReadableDatabase();



        // Location tracking
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationTextView.setText("Finding location..");
            final LocationListener mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location)
                {
                    //your code here
                    System.out.println("Location, location, location");
                    locationTextView.setText("Finding location....");

                    //List<String>  providerList = locationManager.getAllProviders();
                    if(location != null)
                    {
                        setLocationString(location, locationTextView);
                    }
                }

                public void onStatusChanged(String s, int i, Bundle B)
                {
                    // Leave this blank (probably)
                }
                public void onProviderEnabled(String s)
                {
                    // Leave blank???
                }
                public void onProviderDisabled(String s)
                {
                    // Leave blank???
                }
            };

            // Location updates every 60 seconds or 20 meters (1 emerald splash)
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 20, mLocationListener);
            locationTextView.setText("Finding location...");
            Location lastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setLocationString(lastLocation, locationTextView);

            /////// AWS STUFF ///////////
            AWSMobileClient.getInstance().initialize(this).execute();

            AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
            AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

            AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

            this.dynamoDBMapper = DynamoDBMapper.builder()
                    .dynamoDBClient(dynamoDBClient)
                    .awsConfiguration(configuration)
                    .build();
        }

        public void setLocationString(Location location, TextView textView)
        {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if(null!=listAddresses&&listAddresses.size()>0){
                    locationString = listAddresses.get(0).getAddressLine(0).split(",")[1].replaceAll("\\s+","");;
                    textView.setText("Current location: " + locationString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
        else
        {
            startActivity(new Intent(MainActivity.this, ViewInfo.class));
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }*/
    }
}
