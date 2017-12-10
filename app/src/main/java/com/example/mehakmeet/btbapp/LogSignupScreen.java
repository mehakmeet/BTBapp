package com.example.mehakmeet.btbapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogSignupScreen extends AppCompatActivity {

    EditText username;
    EditText password;
    Button log_sign;
    DatabaseReference mDatabase;
    int c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_signup_screen);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        log_sign=(Button)findViewById(R.id.log_in);


       log_sign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               mDatabase=FirebaseDatabase.getInstance().getReference().child("User Admin "+String.valueOf(c));

               String user=username.getText().toString();
               String pass=password.getText().toString();

               mDatabase.child("Username").setValue(user);
               mDatabase.child("Password").setValue(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(LogSignupScreen.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                           finish();
                       }
                   }
               });
               c++;


           }
       });


    }
}
