package com.example.azhar97.pptb2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LokasiPetugasActivity extends AppCompatActivity {

    private RecyclerView recycler_menu;
    private DatabaseReference Lokasi;
    private String mPost_key = null;
    FirebaseRecyclerAdapter<Blog3, BlogViewHolder> adapter2;

    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_petugas);

        recycler_menu = (RecyclerView) findViewById((R.id.myrecycleview3));
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new LinearLayoutManager(this));

        Lokasi = FirebaseDatabase.getInstance().getReference().child("Locations");

        if (getIntent() != null)
            mPost_key = getIntent().getExtras().getString("blog_id");


        if (!mPost_key.isEmpty() && mPost_key != null) {
            ListPetugas(mPost_key);

        }

    }




    private void ListPetugas(final String mPost_key) {
        adapter2 = new FirebaseRecyclerAdapter<Blog3, BlogViewHolder>(Blog3.class,
                R.layout.blog_row3,
                BlogViewHolder.class,
                Lokasi.orderByChild("Petugas").equalTo(mPost_key)) {
            @Override
            protected void populateViewHolder(final LokasiPetugasActivity.BlogViewHolder viewHolder, Blog3 model, final int position) {
                final String post_key = getRef(position).getKey();

                viewHolder.setAddress(model.getAddress());
                viewHolder.setLongitude(model.getLongitude());
                viewHolder.setLatitude(model.getLatitude());

                final String longitude = model.getLongitude();
                final String latitude = model.getLatitude();
                final String longlat = latitude + "," + longitude;


                viewHolder.mView0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Buat Uri dari intent string. Gunakan hasilnya untuk membuat Intent.
                        gmmIntentUri = Uri.parse("google.navigation:q=" + longlat);

                        // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                        // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                        mapIntent.setPackage(goolgeMap);

                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        } else {
                            Toast.makeText(LokasiPetugasActivity.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        };
        recycler_menu.setAdapter(adapter2);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView0;
        public  BlogViewHolder(View itemView)
        {
            super(itemView);
            mView0=itemView;
        }

        public void setAddress(String address) {
            TextView post_address = (TextView) mView0.findViewById(R.id.Alamat);
            post_address.setText(address);
        }
        public void setLongitude(String longitude) {
            TextView post_longitude= (TextView) mView0.findViewById(R.id.Longitude);
            post_longitude.setText(longitude);
        }
        public void setLatitude(String latitude) {
            TextView post_latitude = (TextView) mView0.findViewById(R.id.Latitude);
            post_latitude.setText(latitude);
        }

    }
}