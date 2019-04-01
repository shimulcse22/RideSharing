package com.example.shimul.ridesharing;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    FirebaseAuth auth;
    private EditText email,passowrd;
    private Button btsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(SignUpActivity.this);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.etTextEmail);
        passowrd = findViewById(R.id.etPassword);
        btsignup = findViewById(R.id.buttonLogin);
        auth =FirebaseAuth.getInstance();

        textView = findViewById(R.id.textViewLogin);
        textView.setOnClickListener(this);
        btsignup.setOnClickListener(this);
    }
    private void setSignIn()
    {
        Intent i = new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(i);
    }
    public void onClick(View v)
    {
        if(v==textView)
        {
            setSignIn();
        }
        else if (v==btsignup)
        {
            signup();
        }
    }
    public  void signup()
    {

        if((email.getText().toString().equals("") )|| (passowrd.getText().toString().equals("")))
        {
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            auth.signInWithEmailAndPassword(email.getText().toString(),passowrd.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"User Succefully Sign In",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"User Not found",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
