package com.example.empmgmnt.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.empmgmnt.Model.Employee;
import com.example.empmgmnt.R;
import com.example.empmgmnt.Rest.Config;
import com.example.empmgmnt.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        recyclerView=findViewById(R.id.recyclerview);
        pd=new ProgressDialog(EmployeeListActivity.this);
        fetchEmployeeDetails();
        pd.show();
        pd.setMessage("Please Wait Loading");
    }


    private void fetchEmployeeDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EMPPERSONALINFO, response -> {
            Log.i("Leaveresponse", response);

            try {
                pd.dismiss();
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                JSONArray jarr = new JSONArray(response);

                List<Employee> list = new ArrayList<>();


                for (int i = 0; i < jarr.length(); i++) {

                    Employee adapter = new Employee();
                    JSONObject json = jarr.getJSONObject(i);

                    adapter.setEmpid(json.getString("id"));
                    adapter.setEmpname(json.getString("empname"));
                    adapter.setEmpdesg(json.getString("empdesg"));
                    adapter.setEmpconatctno(json.getString("empcontactno"));
                    adapter.setEmpemail(json.getString("empemail"));
                    adapter.setEmpaddrs(json.getString("empadrs"));
                    adapter.setEmplocation(json.getString("emplocation"));
                    adapter.setEmpdept(json.getString("empdept"));
                    adapter.setEmpimage(json.getString("empimage"));

                    list.add(adapter);
                }

                employeeAdapter = new EmployeeAdapter(list, EmployeeListActivity.this);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(employeeAdapter);

            } catch (JSONException js) {
               /* Toast.makeText(this,"No data Found", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);*/
               Toast.makeText(this, js.getMessage(), Toast.LENGTH_SHORT).show();//yes yes



            }finally {

            }

        }, error -> Toast.makeText(EmployeeListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show()

        ) ;

        RequestQueue requestQueue = Volley.newRequestQueue(EmployeeListActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        fetchEmployeeDetails();
        super.onResume();
    }

    public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {

        List<Employee> EmployeeList;

        Context context;


        public EmployeeAdapter(List<Employee> productList, Context context) {
            this.EmployeeList = productList;
            this.context = context;

        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

            final Employee employee = EmployeeList.get(position);

            holder.tvempname.setText(employee.getEmpname());
            holder.tvempid.setText(employee.getEmpid());
            holder.tvempdesg.setText(employee.getEmplocation());
            holder.tvempcontactno.setText(employee.getEmpconatctno());
            Glide.with(context).load(employee.getEmpimage()).into(holder.imgvwemp);


            holder.tvempcontactno.setOnClickListener(view -> {
                Uri call = Uri.parse("tel:" + employee.getEmpconatctno());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
                context.startActivity(callIntent);

            });

            holder.tvempdesg.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + employee.getEmplocation()));
                EmployeeListActivity.this.startActivity(intent);

            });

        }

        @Override
        public int getItemCount() {

            return EmployeeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvempid, tvempname, tvempdesg, tvempcontactno, tvempdelete;
            Bundle bundle;
            ImageView imgvwemp;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tvempid = itemView.findViewById(R.id.empidTV);
                tvempname = itemView.findViewById(R.id.empnameTV);
                tvempdesg = itemView.findViewById(R.id.empdesgTV);
                tvempcontactno = itemView.findViewById(R.id.empcontactnoTV);
                tvempdelete = itemView.findViewById(R.id.empdeleteTV);
                imgvwemp = itemView.findViewById(R.id.empIV);
                bundle = new Bundle();

                itemView.setOnClickListener(view -> {

                    String EMPEMAIL = EmployeeList.get(getAdapterPosition()).getEmpemail();
                    String EMPADRS = EmployeeList.get(getAdapterPosition()).getEmpaddrs();
                    String EMPLOCATION = EmployeeList.get(getAdapterPosition()).getEmplocation();
                    String EMPDEPT = EmployeeList.get(getAdapterPosition()).getEmpdept();
                    String EMPNAME = EmployeeList.get(getAdapterPosition()).getEmpname();
                    String EMPID = EmployeeList.get(getAdapterPosition()).getEmpid();
                    String EMPPHONE = EmployeeList.get(getAdapterPosition()).getEmpconatctno();
                    Intent intent = new Intent(context, EmployeeDetailsActivity.class);
                    bundle.putString("empemail", EMPEMAIL);
                    bundle.putString("empadrs", EMPADRS);
                    bundle.putString("emplocation", EMPLOCATION);
                    bundle.putString("empdept", EMPDEPT);
                    bundle.putString("empname", EMPNAME);
                    bundle.putString("empid", EMPID);
                    bundle.putString("empcontact", EMPPHONE);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                });

                tvempdelete.setOnClickListener(view -> {
                    String EMPID = EmployeeList.get(getAdapterPosition()).getEmpid();
                    Toast.makeText(EmployeeListActivity.this, EMPID, Toast.LENGTH_SHORT).show();
                    pd.show();
                    pd.setMessage("Loading ");

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EMPDELETE, response -> {


                        Utils.printData("dbdata", response);


                        if (response.contains("Record deleted successfully")) {
                            pd.dismiss();
                            fetchEmployeeDetails();

                            Toast.makeText(EmployeeListActivity.this, "Employee Deleted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EmployeeListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }


                    },
                            error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show()) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();


                            params.put("userid", EMPID);
                            Utils.printData("deleteparams", String.valueOf(params));
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                });

            }
        }
    }}


