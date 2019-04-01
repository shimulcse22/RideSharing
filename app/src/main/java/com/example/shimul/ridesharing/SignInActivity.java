package com.example.shimul.ridesharing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST =234;
    private static final int PICK_IMAGE_REQUEST_NID = 1;

    private Uri filePath;

    private ImageView imageView,imageViewNID;
    private Button btUpload,btsignup,btUploadNID;
    private TextView textViewRegister;
    private StorageReference storageReference;
    FirebaseAuth mauth;
    private EditText etname,etmail,etpassword,etage,etnid,etcarlisense,etphone;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        storageReference = FirebaseStorage.getInstance().getReference();

        mauth = FirebaseAuth.getInstance();

        etname = findViewById(R.id.editTextName);
        etmail = findViewById(R.id.editTextEmail);
        etpassword = findViewById(R.id.editTextPassword);
        etage = findViewById(R.id.editAge);
        etnid = findViewById(R.id.editNid);
        etcarlisense = findViewById(R.id.editCarLisense);
        etphone = findViewById(R.id.editPhone);

        btUpload = findViewById(R.id.btUpload);
        btUploadNID = findViewById(R.id.btUploadNid);

        imageView = findViewById(R.id.image1);
        imageViewNID = findViewById(R.id.imageNID);

        btsignup = (Button) findViewById(R.id.buttonSignUp);

        textViewRegister = findViewById(R.id.textViewRegister);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        btUpload.setOnClickListener(this);
        btUploadNID.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        btsignup.setOnClickListener(this);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"), PICK_IMAGE_REQUEST);
    }
    private void showFileChooserNID() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"), PICK_IMAGE_REQUEST_NID);
    }
    private void setTextViewRegister()
    {
        Intent i = new Intent(SignInActivity.this,SignUpActivity.class);
        startActivity(i);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE_REQUEST_NID && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageViewNID.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void signUpdata()
    {
        String name = etname.getText().toString().trim();
        String emial = etmail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        String age = etage.getText().toString().trim();
        String nid = etnid.getText().toString().trim();
        String carlisense = etcarlisense.getText().toString().trim();
        String phone = etphone.getText().toString().trim();

        if((emial.equals("") )|| (password.equals("")))
        {
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            mauth.createUserWithEmailAndPassword(emial,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"User Created Succesfuly",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"User Cannot Be Created",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        SignUpData signUpData = new SignUpData(name,emial,password,age,nid,carlisense,phone);

        databaseReference.push().setValue(signUpData);


    }
    public void uploadFile(){
        if(filePath !=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving ....");
            progressDialog.show();
            StorageReference riversRef = storageReference.child("images/picture.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"File Save Successfully",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage((int)progress + "% Saving");
                }
            })
            ;
        }

    }

    public void onClick(View v)
    {
        if(v==btUpload)
        {
            showFileChooser();
        }
        else if(v==btUploadNID)
        {
            showFileChooserNID();
        }
        else if(v== textViewRegister)
        {
            setTextViewRegister();
        }
        else if(v==btsignup)
        {
            signUpdata();
            uploadFile();

        }
    }


}
