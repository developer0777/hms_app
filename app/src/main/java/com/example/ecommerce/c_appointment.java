package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class c_appointment extends AppCompatActivity {

    private EditText specialization,doctor;
    private TextView appoint_date;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_appointment);

        specialization = (EditText) findViewById(R.id.specs_select);
        doctor         = (EditText) findViewById(R.id.doctor_select);
        appoint_date   = (TextView) findViewById(R.id.appoint_date);

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
                        appoint_date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }
}
