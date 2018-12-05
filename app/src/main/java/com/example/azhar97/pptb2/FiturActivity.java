package com.example.azhar97.pptb2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FiturActivity extends AppCompatActivity implements View.OnClickListener{

    CardView mMaps, mCall;
    private String nomorhp, value, post_key;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef, Nomor;
    private String mPost_key0 = null;

    private static final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur);

        mCall = (CardView) findViewById(R.id.Call);
        mMaps = (CardView) findViewById(R.id.Maps);

        mCall.setOnClickListener(this);
        mMaps.setOnClickListener(this);

        if (getIntent() != null)
            mPost_key0 = getIntent().getExtras().getString("blog_id");

        Nomor = FirebaseDatabase.getInstance().getReference("Nomor");
        Nomor.keepSynced(false);


        Nomor.child(mPost_key0).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomorhp = (String) dataSnapshot.child("Nomorhp").getValue();
                post_key = dataSnapshot.getKey();            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        Uri uri =null;
        Intent intent = null;

        switch (v.getId()){
            case R.id.Call:
                uri = Uri.parse("tel:" + nomorhp);
                intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                break;
            case R.id.Maps:

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(FiturActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                /*Intent MapsActivity = new Intent(FiturActivity.this, MapsActivity.class);
                MapsActivity.putExtra("blog_id2", post_key);
                startActivity(MapsActivity);*/
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                /*String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();*/

                final String PlaceID = (String) place.getAddress();
                final double latitude = place.getLatLng().latitude;
                final double longitude = place.getLatLng().longitude;

                SimpankeFirebase( post_key, PlaceID, longitude, latitude);

            }
        }
    }

    private void SimpankeFirebase(String post_key, String PlaceID, double longitude, double latitude) {
        Map<String, String> mLocations = new HashMap<String, String>();

        mLocations.put("Petugas", post_key);
        mLocations.put("Address", PlaceID);
        mLocations.put("Longitude", String.valueOf(longitude));
        mLocations.put("Latitude", String.valueOf(latitude));
        DatabaseReference newLocations = FirebaseDatabase.getInstance().getReference().child("Locations").push();
        newLocations.setValue(mLocations);

    }
}
