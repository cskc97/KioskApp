package apps.everythingforward.com.kioskapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.vstechlab.easyfonts.EasyFonts;

import apps.everythingforward.com.kioskapp.SugarRecords.PlaceRecords;
import apps.everythingforward.com.kioskapp.data.KioskContract;
import apps.everythingforward.com.kioskapp.data.KioskDbHelper;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    //GoogleApiClient Necessary for using the Places API.

    //Just to push
    private GoogleApiClient mGoogleApiClient;
    Button button;
    public int PLACE_PICKER_REQUEST = 1;
    TextView welcomeTextView, numberOfRows;
    FloatingActionButton fab;
    private KioskDbHelper mDbHelper;



    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







//        mDbHelper = new KioskDbHelper(this);
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(" SELECT * FROM " + KioskContract.KioskEntry.TABLE_NAME, null);
////
////        try {
////            numberOfRows = (TextView)findViewById(R.id.textView2);
////            numberOfRows.setText("Number of rows = "+ cursor.getCount());
////        }finally {
////            cursor.close();
////        }
////
////        Toast.makeText(getApplicationContext(),"Openend app",Toast.LENGTH_SHORT).show();

//



        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        button = (Button)findViewById(R.id.placepicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        //All the code below is for the FAB. Methinks it's a good idea to replace the Buttons by an FAB. It looks Dope AF.

        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);



        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.searchlogo));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
                invokePlacePicker();

            }
        });



        SubActionButton button2 = createSubActionButton(getResources().getDrawable(R.drawable.databasesicon));



        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else {

                invokePlacePicker();


                }

            }
        });





        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(fab)
                .build();


    }

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                String placeName = (String)place.getName();
                String placeAddress = (String)place.getAddress();
                String placePhNo = (String)place.getPhoneNumber();
                PlaceRecords placeRecord = new PlaceRecords(placeName,placePhNo,placeAddress);
                placeRecord.save();




            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(MainActivity.this, "Could not connect to the internet!", Toast.LENGTH_SHORT).show();

    }

    public SubActionButton createSubActionButton(Drawable drawable)
    {
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(drawable);
        SubActionButton button = itemBuilder.setContentView(itemIcon).build();
        return button;

    }

    private void invokePlacePicker()
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }
}
