package com.momen.primaryschoolteachers_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.momen.primaryschoolteachers_app.Model.ClassModel;
import com.momen.primaryschoolteachers_app.Model.ModelClassAdapter;
import com.momen.primaryschoolteachers_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddInformation extends AppCompatActivity {
    RecyclerView recyclerView;
    private String URLstring = "http://192.168.10.108:1000/api/class/list";
    private static ProgressDialog mProgressDialog;
    ArrayList<ClassModel> dataModelArrayList;
    ModelClassAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinfo);
        recyclerView = findViewById(R.id.addClassInfo);

        fetchClassList();
    }
    private void fetchClassList() {



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URLstring,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {


                                dataModelArrayList = new ArrayList<>();
                                //JSONArray dataArray  = new JSONArray(response);

                                for (int i = 0; i < response.length(); i++) {

                                    ClassModel classModel = new ClassModel();
                                    JSONObject dataobj = response.getJSONObject(i);

                                    classModel.setClassname(dataobj.getString("class_name_bn"));



                                    dataModelArrayList.add(classModel);

                                }

                                setupRecycler();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);


    }

    private void setupRecycler(){

        adapter = new ModelClassAdapter(this,dataModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
