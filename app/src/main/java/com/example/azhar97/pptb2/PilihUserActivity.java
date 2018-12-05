package com.example.azhar97.pptb2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PilihUserActivity extends AppCompatActivity {

    private CardView mPetugas, mUser;
    public String uid,  uservalue, USER, PETUGAS;
    private DatabaseReference Fireuser;
    //private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_user);

        USER = "USER";
        PETUGAS = new String("PETUGAS");


        mPetugas = (CardView) findViewById(R.id.petugas);
        mUser = (CardView) findViewById(R.id.user);

        mAuth = FirebaseAuth.getInstance();
        /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(user !=null){
                    cekuser();
                    if (uservalue==USER){
                        Intent intent = new Intent(PilihUserActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    if (uservalue==PETUGAS){
                        Intent intent = new Intent(PilihUserActivity.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }
        };*/





        mPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(PilihUserActivity.this, uservalue, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PilihUserActivity.this, PetugasLoginActivity.class);
                startActivity(intent);
                return;

            }
        });
        mUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(PilihUserActivity.this, uservalue, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PilihUserActivity.this, UserLoginAcrivity.class);
                startActivity(intent);
                return;


            }
        });

    }

   /* private void cekuser(){

        Fireuser = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        Fireuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    uservalue = (String) dataSnapshot.child("User_ID").getValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

     @Override
        protected void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(firebaseAuthListener);

        }
        @Override
        protected void onStop() {
            super.onStop();
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }*/
}
