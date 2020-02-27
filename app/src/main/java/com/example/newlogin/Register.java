package com.example.newlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
     Button btnRegister;
     EditText fName,eMail,password;
     TextView loginNow;
     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
     ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginNow = findViewById(R.id.loginNow);
       btnRegister = findViewById(R.id.btnLogininto);
        fName = findViewById(R.id.fName);
        eMail = findViewById(R.id.eMail);
        fAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fStore = FirebaseFirestore.getInstance();

       /* if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),login.class));
           finish();
        }*/


       btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Email = eMail.getText().toString();
               String Password = password.getText().toString();
               //validate Email and Password
               if(TextUtils.isEmpty(Email)){
                eMail.setError("please Enter EmailID");
               return;
               }
               if(TextUtils.isEmpty(Password)){
                   password.setError("Enter Password");
                   return;
               }
                if(Password.length()< 6){
                  password.setError("password must be atleast 6 letters");
                  return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //create User with Email and Password
                   fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){

                        // DocumentReference docRef = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
                         //verify User EmailID
                         fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     Toast.makeText(Register.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                     progressBar.setVisibility(View.GONE);
                                     startActivity(new Intent(getApplicationContext(),login.class));
                                     finish();

                                 }else{
                                     Toast.makeText(Register.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });

                     }
                     else{
                         Toast.makeText(Register.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                     }
                    }
                });
            }
        });
       loginNow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             startActivity(new Intent(getApplicationContext(),login.class));
           }
       });

    }

}
