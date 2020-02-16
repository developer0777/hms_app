package com.example.ecommerce;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    private EditText fullname,email,password,phone;

    private Button Sign_up_btn;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public  String check        = "Ahmad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname     = findViewById(R.id.name_reg);
        email        = findViewById(R.id.email_reg);
        password     = findViewById(R.id.pass_reg);
        phone        = findViewById(R.id.phone_reg);
        Sign_up_btn  = findViewById(R.id.sign_up_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkInputs();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fullname.addTextChangedListener(new TextWatcher() {
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

        phone.addTextChangedListener(new TextWatcher() {
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

        Sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkEmailPassword();

            }
        });
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fullname.getText())){

                if(!TextUtils.isEmpty(password.getText()) && password.length() >= 8){
                    Sign_up_btn.setEnabled(true);
                    Sign_up_btn.setTextColor(Color.rgb(255,255,255));
                }else{
                    Sign_up_btn.setEnabled(false);
                    Sign_up_btn.setTextColor(Color.argb(50,255,255,255));
                }

            }else{
                Sign_up_btn.setEnabled(false);
                Sign_up_btn.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            Sign_up_btn.setEnabled(false);
            Sign_up_btn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void checkEmailPassword(){
        if(email.getText().toString().matches(emailPattern)){


            String url ="https://realprotrainingsolutions.com/andriod/insert_user.php";

            Sign_up_btn.setEnabled(false);
            Sign_up_btn.setTextColor(Color.argb(50,255,255,255));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("add_user",check);
                    params.put("full_name",fullname.getText().toString());
                    params.put("email",email.getText().toString());
                    params.put("password",password.getText().toString());
                    params.put("phone",phone.getText().toString());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

//            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//
//                                Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_SHORT).show();
//                                Intent mainIntent = new Intent(register.this,MainActivity.class);
//                                startActivity(mainIntent);
//                                finish();
//                            }else{
//                                String error = task.getException().getMessage();
//                                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

        }else{
            email.setError("Invalid Email Address");
        }
    }

}
