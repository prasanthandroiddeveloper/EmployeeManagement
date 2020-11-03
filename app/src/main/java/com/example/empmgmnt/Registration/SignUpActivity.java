package com.example.empmgmnt.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.empmgmnt.R;
import com.example.empmgmnt.Rest.Config;
import com.example.empmgmnt.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameet,emailidet,contactnoet,passwordet,confirmpasswrdet;
    Button btnsubmit;
    String USERNAME,EMAILID,CONTACTNO,PASSWORD,CNFPASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameet=findViewById(R.id.usernameET);
        emailidet=findViewById(R.id.emailidET);
        contactnoet=findViewById(R.id.contactnoET);
        passwordet=findViewById(R.id.passwordET);
        confirmpasswrdet=findViewById(R.id.confirmpwdeT);
        btnsubmit=findViewById(R.id.submitBTN);

        btnsubmit.setOnClickListener(view -> {
            getData();
            validateData();

        });

    }

    private void getData(){
        USERNAME=usernameet.getText().toString().trim();
        EMAILID=emailidet.getText().toString().trim();
        CONTACTNO=contactnoet.getText().toString().trim();
        PASSWORD=passwordet.getText().toString().trim();
        CNFPASSWORD=confirmpasswrdet.getText().toString().trim();
    }


    private void validateData(){

        if(!(USERNAME.length()>=4)){

            Utils.showMessage(SignUpActivity.this,"Enter valid user Name");
        }else if(!(EMAILID.contains("@"))){
            Toast.makeText(this, "Enter Valid Email id", Toast.LENGTH_SHORT).show();
        }else if(!(CONTACTNO.length()==10)){
            Toast.makeText(this, "Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
        }else if(!(PASSWORD.equals(CNFPASSWORD))){
            Toast.makeText(this, "Password and Confirm password are not same", Toast.LENGTH_SHORT).show();
        }else{

            inserttoDb(USERNAME,EMAILID,CONTACTNO,PASSWORD,CNFPASSWORD);
        }

    }

    private void inserttoDb(String username,String emailid,String contactno,String password,String confirmpassword){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGURL , response -> {

            Utils.showMessage(SignUpActivity.this,response);
            Utils.printData("dbdata",response);


            if(response.contains("Success")){
                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);

            }else{
                Utils.showMessage(SignUpActivity.this,"Registration Failed");
            }


        },
        error -> Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("username",username);
                params.put("emailid",emailid);
                params.put("contactno",contactno);
                params.put("password",password);
                params.put("confirmpassword",confirmpassword);
                Utils.printData("dbparams", String.valueOf(params));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);
    }

}

