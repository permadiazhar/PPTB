package com.example.azhar97.pptb2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserLoginAcrivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mRegister;
    private String uservalue, user_id, USER;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DatabaseReference Fireuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_acrivity);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        USER = "user";
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (TextView) findViewById(R.id.register);



        mAuth = FirebaseAuth.getInstance();
        /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    cekuser();

                    if (uservalue!= null && uservalue.toString().equals(USER.toString())){
                        Intent intent = new Intent(UserLoginAcrivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }

                    *//*Intent intent = new Intent(UserLoginAcrivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;*//*
                }
                else {
                    Toast.makeText(UserLoginAcrivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                }
            }
        };*/





        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(UserLoginAcrivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(UserLoginAcrivity.this, "sign up error" , Toast.LENGTH_SHORT).show();
                        }else{
                            Map<String, String> mUser = new HashMap<String, String>();

                            user_id = mAuth.getCurrentUser().getUid();
                            mUser.put("Id", USER);
                            DatabaseReference newmUser = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            newmUser.setValue(mUser);


                            /*user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("User").child(user_id);
                            current_user_db.setValue(USER);*/
                        }
                    }
                });
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(UserLoginAcrivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(UserLoginAcrivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(UserLoginAcrivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });

            }
        });




    }

    /*private void cekuser() {

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Fireuser = FirebaseDatabase.getInstance().getReference();
        Fireuser.child("Users").child(String.valueOf(user_id)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uservalue = String.valueOf(dataSnapshot.child("Id").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Toast.makeText(UserLoginAcrivity.this, uservalue, Toast.LENGTH_SHORT).show();
    }*/


        /*@Override
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
