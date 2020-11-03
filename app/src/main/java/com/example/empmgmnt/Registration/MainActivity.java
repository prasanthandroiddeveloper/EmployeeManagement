package com.example.empmgmnt.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.empmgmnt.HomeActivity;
import com.example.empmgmnt.R;
import com.example.empmgmnt.Rest.Config;
import com.example.empmgmnt.Utils.Session;
import com.example.empmgmnt.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView imgvemp;
    EditText etusername,etpassword;
    String USERNAME,PASSWORD,name,id,role;
    Button btnsubmit;
    TextView tvsignup;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new Session(MainActivity.this);

        imgvemp=findViewById(R.id.empIMGV);
        etusername=findViewById(R.id.usernameET);
        etpassword=findViewById(R.id.passwordET);
        btnsubmit=findViewById(R.id.submitBTN);
        tvsignup=findViewById(R.id.signupTV);


        etusername.setFocusable(true);
        etusername.requestFocus();

        btnsubmit.setOnClickListener(view -> {
            USERNAME=etusername.getText().toString().trim();
            PASSWORD=etpassword.getText().toString().trim();

            validateuserData(USERNAME,PASSWORD);
            Log.i("100",USERNAME+"\n"+PASSWORD);
        });

        tvsignup.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
        });


        if(session.loggedin()){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    private void validateuserData(String username,String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGINURL, response -> {

            if(!(response.contains("fail"))){

                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                try {
                    JSONObject jObj = new JSONObject(response);

                    name = jObj.getString("user_name");
                    id = jObj.getString("id");
                    role = jObj.getString("role");

                    session.setUserDet(id,name,role);
                    session.setLoggedin(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },
                error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("username",username);
                params.put("password",password);
                Utils.printData("mainactdbparams", String.valueOf(params));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }


}
