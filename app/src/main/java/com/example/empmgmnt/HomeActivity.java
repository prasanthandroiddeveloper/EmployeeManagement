package com.example.empmgmnt;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empmgmnt.Employee.EmployeeListActivity;
import com.example.empmgmnt.Registration.MainActivity;
import com.example.empmgmnt.Utils.Session;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    String ID,USERNAME,USERROLE;
    Session session;
    Button btnaddemp,btndelemp,btnlistemp;
    EditText etid,etname,etdesg,etemail,etaddrs,etcontcno,etlocation;
    Spinner spnrdept;
    Dialog dialog;
    String EMPID,EMPNAME,EMPDESG,EMPEMAIL,EMPCONTACTNO,EMPADDRS,EMPLOCATION,EMPDEPT;
    String [] DEPARTMENTS={"WEB DEVELOPMENT","ANDROID DEVELOPMENT","SERVER TEAM","ACCOUNTS TEAM","HR DEPT","HOUSE KEEPING"};
    TextView tvabtcmpny;
    String msg="This is my new app Pls Check our link"+"\n"+"https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnaddemp=findViewById(R.id.addemployeeBTN);
        btndelemp=findViewById(R.id.delemployeeBTN);
        btnlistemp=findViewById(R.id.listemployeeBTN);
        tvabtcmpny=findViewById(R.id.abtcmpnyTV);
        session=new Session(HomeActivity.this);

        tvabtcmpny.setOnClickListener(view -> {

            Intent intent=new Intent(HomeActivity.this,AboutCompanyActivity.class);
            HomeActivity.this.startActivity(intent);
        });


        ID=session.getUId();
        USERNAME=session.getUName();
        USERROLE=session.getUrole();


        if(USERROLE.equals("HR")){
            btnaddemp.setVisibility(View.VISIBLE);
            btndelemp.setVisibility(View.VISIBLE);
        }else{
            btnaddemp.setVisibility(View.GONE);
            btndelemp.setVisibility(View.GONE);
        }


        btnaddemp.setOnClickListener(view -> {
             dialog=new Dialog(HomeActivity.this);
            dialog.setContentView(R.layout.addemployee);
            Objects.requireNonNull(dialog.getWindow()).setLayout(LinearLayout.MarginLayoutParams.MATCH_PARENT,LinearLayout.MarginLayoutParams.MATCH_PARENT);
            empIDS();
            empDATA();

            dialog.show();
        });

        btnlistemp.setOnClickListener(view -> {
           startActivity(new Intent(HomeActivity.this, EmployeeListActivity.class));
        });
    }

    private void empIDS(){
        etid=dialog.findViewById(R.id.empidET);
        etname=dialog.findViewById(R.id.empnameET);
        etdesg=dialog.findViewById(R.id.empdesgET);
        etemail=dialog.findViewById(R.id.empemailidET);
        etcontcno=dialog.findViewById(R.id.empcntcnoET);
        etaddrs=dialog.findViewById(R.id.empaddrsET);
        etlocation=dialog.findViewById(R.id.emplocaET);
        spnrdept=dialog.findViewById(R.id.empdepSPNR);

    }

    private void empDATA(){
        ArrayAdapter <String> adapter=new ArrayAdapter<>(HomeActivity.this,android.R.layout.simple_spinner_dropdown_item,DEPARTMENTS);
        spnrdept.setAdapter(adapter);
        spnrdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String pos= String.valueOf(adapterView.getItemAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Dialog dialog=new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_profile);
                TextView empid,empname;
                empid=dialog.findViewById(R.id.empidTV);
                empname=dialog.findViewById(R.id.nameTV);
                empid.setText(ID);
                empname.setText(USERNAME);
                Objects.requireNonNull(dialog.getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();

                return true;

            case R.id.item2:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(sendIntent, "Share Link to"));
                startActivity(sendIntent);
                return true;

            case R.id.item3:
                session.logout();
                Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {

        if (exit) {
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(() -> exit = false, 3 * 1000);
        }


    }

}