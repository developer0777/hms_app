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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class U_LOGIN extends AppCompatActivity {

    public  TextView        dont_have_an_account;
    private EditText        email,password;
    private Button          login_btn;
    private ProgressBar     progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u__login);

        dont_have_an_account = findViewById(R.id.dont_have_an_account);
        email                = findViewById(R.id.user_name);
        password             = findViewById(R.id.password);
        login_btn            = findViewById(R.id.login_btn);
        progressBar          = findViewById(R.id.login_progress_bar);





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

        progressBar.setVisibility(View.VISIBLE);
        String url ="https://realprotrainingsolutions.com/andriod/user.php";

        login_btn.setEnabled(false);
        login_btn.setTextColor(Color.argb(50,255,255,255));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            boolean error = jsonObject.getBoolean("error");

                            if (error == false){

                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                jsonObject.getInt("p_id"),
                                                jsonObject.getString("email"),
                                                jsonObject.getString("full_name")
                                        );

                                Intent intent = new Intent(getApplicationContext(),c_appointment.class);
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
                Toast.makeText(getApplicationContext(),"Request Error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                String check = "ahmad";
                params.put("logging_request",check);
                params.put("p_email",email.getText().toString());
                params.put("p_password",password.getText().toString());

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }
}
