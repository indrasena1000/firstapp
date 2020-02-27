package com.example.newlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
   EditText eMail,password,phoneNum;
   Button btnLogin;
   FirebaseAuth firebaseAuth;
   FirebaseFirestore fStore;
  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eMail = findViewById(R.id.eMail);
        password = findViewById(R.id.password);
        phoneNum = findViewById(R.id.phoneNum);
        firebaseAuth=FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLogin = findViewById(R.id.btnLogininto);
        userID = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference documentReference =fStore.collection("users").document(userID);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              final String UEmail = eMail.getText().toString();
              final String UPassword = password.getText().toString();
              if(TextUtils.isEmpty(UEmail)) {
                    eMail.setError("enter EmailID");
                    return;
                }if (TextUtils.isEmpty(UPassword)) {
                    password.setError("enter Password");
              return;
              }
                if( UPassword.length()<6){
                  password.setError("password must be atleast 6 letters");
                 return;
                }
              firebaseAuth.signInWithEmailAndPassword(UEmail,UPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Map<String,Object> user = new HashMap<>();
                          user.put("eMail",UEmail);
                          documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   Toast.makeText(login.this, "login successful", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(getApplicationContext(),MainActivity.class));

                               }
                               else{
                                   Toast.makeText(login.this, "login failed", Toast.LENGTH_SHORT).show();

                               }
                              }
                          });
                          }
                      else{
                          Toast.makeText(login.this, "login failed try again now", Toast.LENGTH_SHORT).show();
                      }
                  }
              });
            }
        });


    }
}
