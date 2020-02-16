package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class U_LOGIN extends AppCompatActivity {

    public  TextView        dont_have_an_account;
    private EditText        email,password;
    private Button          login_btn;
    private FirebaseAuth    firebaseAuth;
    private FirebaseUser    user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u__login);

        dont_have_an_account = findViewById(R.id.dont_have_an_account);
        email                = findViewById(R.id.user_name);
        password             = findViewById(R.id.password);
        login_btn            = findViewById(R.id.login_btn);
        firebaseAuth         = FirebaseAuth.getInstance();
        user                 = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }


        dont_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_signUp = new Intent(U_LOGIN.this,register.class);
                startActivity(open_signUp);
                finish();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });
    }

    public void checkInputs(){

        if(!TextUtils.isEmpty(email.getText())){

            if(!TextUtils.isEmpty(password.getText())){

                login_btn.setEnabled(true);
                login_btn.setTextColor(Color.rgb(255,255,255));

            }else{

                password.setError("Required");
                login_btn.setEnabled(false);
                login_btn.setTextColor(Color.argb(50,255,255,255));

            }

        }else{

            email.setError("Required");
            login_btn.setEnabled(false);
            login_btn.setTextColor(Color.argb(50,255,255,255));

        }

    }

    public void login_user(){

    }
}
