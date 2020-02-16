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
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    private EditText fullname,email,password,phone,address;

    private Button Sign_up_btn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public  String check        = "Ahmad";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname     = findViewById(R.id.name_reg);
        email        = findViewById(R.id.email_reg);
        password     = findViewById(R.id.pass_reg);
        phone        = findViewById(R.id.phone_reg);
        address        = findViewById(R.id.pass_adress);
        Sign_up_btn  = findViewById(R.id.sign_up_btn);
        progressBar  = findViewById(R.id.progressBar);

        address.addTextChangedListener(new TextWatcher() {
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

                    if(!TextUtils.isEmpty(address.getText())){

                        if(!TextUtils.isEmpty(phone.getText())){

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

            progressBar.setVisibility(View.VISIBLE);
            String url ="https://realprotrainingsolutions.com/andriod/user.php";

            Sign_up_btn.setEnabled(false);
            Sign_up_btn.setTextColor(Color.argb(50,255,255,255));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                boolean error = jsonObject.getBoolean("error");

                                if (error == false){

                                    Intent intent = new Intent(getApplicationContext(),U_LOGIN.class);
                                    startActivity(intent);
                                    finish();

                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
                    params.put("add_patient",check);
                    params.put("p_name",fullname.getText().toString());
                    params.put("p_email",email.getText().toString());
                    params.put("p_pass",password.getText().toString());
                    params.put("p_phone",phone.getText().toString());
                    params.put("p_add",address.getText().toString());

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }else{
            email.setError("Invalid Email Address");
        }
    }

}
