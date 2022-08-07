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

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt, pwdEdt,cnfPwdEdt;
    private TextView loginTV;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        
        userNameEdt = (TextInputEditText) findViewById(R.id.idETUserName);
        pwdEdt = (TextInputEditText) findViewById(R.id.idETPassword);
        cnfPwdEdt = (TextInputEditText) findViewById(R.id.idETCnPassword);
        registerBtn = (Button) findViewById(R.id.idBtnRegister);
        loadingPB = (ProgressBar) findViewById(R.id.idPBLoading);
        loginTV = (TextView) findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String password = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdEdt.getText().toString();

                if(!pwdEdt.equals(cnfPwdEdt)){
                    Toast.makeText(RegistrationActivity.this, "Please check both password", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(password) && TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText(RegistrationActivity.this, "Please add your credentials. All fields are necessary.", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(userName,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                  loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Registration Failed...", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });
    }
}