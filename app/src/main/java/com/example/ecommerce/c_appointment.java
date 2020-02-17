package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class c_appointment extends AppCompatActivity {

    private EditText doc_fee,ahmad;
    private TextView appoint_date;
    private Calendar calendar;
    private Spinner spec_spinner,doctor_spinner;
    private DatePickerDialog datePickerDialog;
    String  URL ="https://realprotrainingsolutions.com/andriod/user.php";
    ArrayList<String> specializations_list;
    ArrayList<String> doctors_list;
    private ProgressBar progressBar;
    private Button     create_appoint_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_appointment);

        appoint_date                 =  findViewById(R.id.appoint_date);
        specializations_list         =  new ArrayList<>();

        progressBar                  =  findViewById(R.id.progressBar);
        doctor_spinner               =  findViewById(R.id.doctor_select);
        spec_spinner                 =  findViewById(R.id.specs_select);
        doc_fee                      =  findViewById(R.id.doc_fee);
        create_appoint_btn           =  findViewById(R.id.create_appoint_btn);

        progressBar.setVisibility(View.VISIBLE);
        loadSpecializations(URL);

        create_appoint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAppointment(URL);
            }
        });

        doctor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!(i == 0)) {
                    progressBar.setVisibility(View.VISIBLE);
                    String doctor = doctor_spinner.getItemAtPosition(doctor_spinner.getSelectedItemPosition()).toString();
                    loadFee(URL,doctor);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spec_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!(i == 0)) {
                    progressBar.setVisibility(View.VISIBLE);
                    String specs = spec_spinner.getItemAtPosition(spec_spinner.getSelectedItemPosition()).toString();
                    loadDoctors(URL,specs);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        appoint_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar    = Calendar.getInstance();
                int day     = calendar.get(Calendar.DAY_OF_MONTH);
                int month   = calendar.get(Calendar.MONTH);
                int year    = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(c_appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        appoint_date.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.show();
                create_appoint_btn.setEnabled(true);
            }
        });

    }

    private void insertAppointment(String url){

        StringRequest stringRequestdocfee =   new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(),"Error in inserting Data",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                String check_result = "aa";
                params.put("get_fee",check_result);
                params.put("p_id",check_result);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequestdocfee);

    }

    private void loadFee(String url, final String doctor){

        StringRequest stringRequestdocfee =   new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String fee = jsonObject.getString("fee");
                            doc_fee.setText(fee + "Rs");
                            doc_fee.setVisibility(View.VISIBLE);
                            appoint_date.setVisibility(View.VISIBLE);
                            create_appoint_btn.setVisibility(View.VISIBLE);

                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(),"Invalid Doctor",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                String check_result = "aa";
                params.put("get_fee",check_result);
                params.put("doctor_selected",doctor);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequestdocfee);

    }

    private void loadDoctors(String url, final String specs){

        doctors_list                 =  new ArrayList<>();

        StringRequest stringRequestdoc =   new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("doctors_array");
                            doctors_list.add("Select Doctor");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String result_special  = jsonObject1.getString("doctors");
                                doctors_list.add(result_special);
                            }

                            doctor_spinner.setAdapter(new ArrayAdapter<String>(c_appointment.this, android.R.layout.simple_spinner_dropdown_item, doctors_list){
                                public boolean isEnabled(int position){
                                    if(position == 0)
                                    {
                                        return false;
                                    }
                                    else
                                    {
                                        return true;
                                    }
                                }
                            });
                        }catch (JSONException e){e.printStackTrace();}

                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                String check_result = "aa";
                params.put("get_doctors",check_result);
                params.put("spec_selected",specs);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequestdoc);

    }

    private void loadSpecializations(String url) {

        StringRequest stringRequest =   new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("specs_array");
                            specializations_list.add("Select Specialization");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String result_special  = jsonObject1.getString("specialization");
                                specializations_list.add(result_special);
                            }

                            spec_spinner.setAdapter(new ArrayAdapter<String>(c_appointment.this, android.R.layout.simple_spinner_dropdown_item, specializations_list){
                                public boolean isEnabled(int position){
                                    if(position == 0)
                                    {
                                        return false;
                                    }
                                    else
                                    {
                                        return true;
                                    }
                                }
                            });
                        }catch (JSONException e){e.printStackTrace();}
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                String test_purpose = "Ahmad";
                params.put("get_specializations",test_purpose);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
