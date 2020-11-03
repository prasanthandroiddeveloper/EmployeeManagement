package com.example.empmgmnt.Employee;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.empmgmnt.R;
import com.example.empmgmnt.Rest.Config;
import com.example.empmgmnt.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDetailsActivity extends AppCompatActivity {
    TextView tvempdetupdt,tvempdetsave;
    Bundle bundle;
    EditText etemail, etaddrs, etlocation, etdept;
    String EMAIL,ADDRSS,LOCATION,DEPT,NAME,ID,PHONE;
    LinearLayout lnrempdetails;
    String UPEMAIL,UPADDRS,UPLOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        bundle=getIntent().getExtras();

        etemail =findViewById(R.id.empemailTV);
        etaddrs =findViewById(R.id.empadrsTV);
        etlocation =findViewById(R.id.emplocaTV);
        etdept =findViewById(R.id.empdeptTV);
        lnrempdetails=findViewById(R.id.employeedetaisLNR);
        tvempdetupdt=findViewById(R.id.empdetupdtTV);
        tvempdetsave=findViewById(R.id.empdetsaveTV);

        EMAIL=bundle.getString("empemail");
        ADDRSS=bundle.getString("empadrs");
        LOCATION=bundle.getString("emplocation");
        DEPT=bundle.getString("empdept");
        NAME=bundle.getString("empname");
        ID=bundle.getString("empid");
        PHONE=bundle.getString("empcontact");


        etemail.setEnabled(false);
        etaddrs.setEnabled(false);
        etlocation.setEnabled(false);
        etdept.setEnabled(false);

        etemail.setText(EMAIL);
        etaddrs.setText(ADDRSS);
        etlocation.setText(LOCATION);
        etdept.setText(DEPT);

        TextView tv=new TextView(EmployeeDetailsActivity.this);
        tv.setText("Hii"+" "+NAME+" "+"Uncle");
        tv.setTextColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        tv.setLayoutParams(params);
        lnrempdetails.addView(tv);


        tvempdetupdt.setOnClickListener(view -> {
            etemail.setEnabled(true);
            etaddrs.setEnabled(true);
            etlocation.setEnabled(true);
            etemail.setFocusable(true);
            etemail.requestFocus();
        });

        tvempdetsave.setOnClickListener(view -> {
               UPEMAIL= etemail.getText().toString().trim();
               UPADDRS= etaddrs.getText().toString().trim();
               UPLOCATION= etlocation.getText().toString().trim();
               empdetailsUpdate();
        });





    }

    private void empdetailsUpdate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EMPUPDATE , response -> {

            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            Log.i("responsedata", "empdetailsUpdate: "+response);

        },
                error -> Toast.makeText(EmployeeDetailsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("empid",ID);
                params.put("upemail",UPEMAIL);
                params.put("uplocation",UPLOCATION);
                params.put("upadrs",UPADDRS);
                Utils.printData("upadrs", String.valueOf(params));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EmployeeDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

}