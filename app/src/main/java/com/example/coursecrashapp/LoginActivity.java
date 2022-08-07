package com.example.coursecrashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt, pwdEdt;
    private Button loginBtn;
    private ProgressBar loadingPB;
    private TextView registerTv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEdt = (TextInputEditText) findViewById(R.id.idETUserName);
        pwdEdt = (TextInputEditText) findViewById(R.id.idETPassword);
        loginBtn = (Button) findViewById(R.id.idBtnLogin);
        loadingPB = (ProgressBar) findViewById(R.id.idPBLoading);
        registerTv = (TextView) findViewById(R.id.idTVRegister);
        mAuth = FirebaseAuth.getInstance();

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();

                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this, "Please enter your credentials.All fields are necessary", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.signInWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               loadingPB.setVisibility(View.GONE);
                               Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                               Intent i = new Intent(LoginActivity.this, MainActivity.class);
                               startActivity(i);
                               finish();
                           }else{
                               loadingPB.setVisibility(View.GONE);
                               Toast.makeText(LoginActivity.this, "Failed to Login...", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user  =  mAuth.getCurrentUser();
        if(user!= null){
          Intent i = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(i);
          this.finish();
        }
    }
}