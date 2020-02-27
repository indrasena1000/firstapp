package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    TextView UEmail,Uphone;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     UEmail = findViewById(R.id.UEmail);
      Uphone = findViewById(R.id.phone);
      fStore = FirebaseFirestore.getInstance();
      firebaseAuth = FirebaseAuth.getInstance();

        DocumentReference documentReference =fStore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                 UEmail.setText(documentSnapshot.getString("eMail"));
                 //Uphone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
                    //Uphone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
                  //Uphone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
                    Uphone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
                }

            }
        });
    }
}
