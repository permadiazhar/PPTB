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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PetugasLoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mRegister;
    private String USER, user_id;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas_login);

        mEmail = (EditText) findViewById(R.id.email2);
        mPassword = (EditText) findViewById(R.id.password2);

        mLogin = (Button) findViewById(R.id.login2);
        mRegister = (TextView) findViewById(R.id.register2);

        USER = "PETUGAS";

        mAuth = FirebaseAuth.getInstance();
        /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user==null){
                    Intent intent = new Intent(PetugasLoginActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };*/


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(PetugasLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(PetugasLoginActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                        }else{
                            Map<String, String> mUser = new HashMap<String, String>();

                            user_id = mAuth.getCurrentUser().getUid();
                            mUser.put("Id", USER);
                            DatabaseReference newmUser = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            newmUser.setValue(mUser);
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
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(PetugasLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(PetugasLoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(PetugasLoginActivity.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });

            }
        });




    }

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

