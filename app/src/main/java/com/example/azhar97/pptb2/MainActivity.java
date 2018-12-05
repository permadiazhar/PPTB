package com.example.azhar97.pptb2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button mLogout;
    private RecyclerView recycler_menu;
    private DatabaseReference Nomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_menu = (RecyclerView) findViewById((R.id.myrecycleview));
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new LinearLayoutManager(this));

        Nomor = FirebaseDatabase.getInstance().getReference().child("Nomor");

        mLogout = (Button) findViewById(R.id.logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, PilihUserActivity.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (Blog.class,R.layout.blog_row,BlogViewHolder.class,Nomor) {


            @Override
            protected void populateViewHolder(final BlogViewHolder viewHolder, Blog model, final int position) {
                final String post_key = getRef(position).getKey();

                /*viewHolder.setNomorhp(model.getNomorhp());*/
                viewHolder.setNamapetugas(model.getNamapetugas());


                viewHolder.mView0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ListpetugasActivity = new Intent(MainActivity.this, FiturActivity.class);
                        ListpetugasActivity.putExtra("blog_id", post_key);

                        startActivity(ListpetugasActivity);
                    }
                });

            }
        };
        recycler_menu.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView0;
        public  BlogViewHolder(View itemView)
        {
            super(itemView);
            mView0=itemView;
        }

        public void setNamapetugas(String namapetugas) {
            TextView post_namapetugas = (TextView) mView0.findViewById(R.id.post_namapetugas);
            post_namapetugas.setText(namapetugas);
        }

    }
}
