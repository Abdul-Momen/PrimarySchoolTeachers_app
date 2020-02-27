package com.momen.primaryschoolteachers_app.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.momen.primaryschoolteachers_app.Adapter.LogAdapter;
import com.momen.primaryschoolteachers_app.Model.Log;
import com.momen.primaryschoolteachers_app.R;
import com.momen.primaryschoolteachers_app.api.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LogFragment extends Fragment {
    SharedPreferences prefs;
    String schoolid;
    /*String url = "http://demo.olivineltd.com/primary_attendance/api/school/attend_student/log/";*/
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Log> logList;
    private RecyclerView.Adapter adapter;
    private String sharedPrefFile =
            "com.olivine.primaryschoolteachers_app";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_log, container, false);
        prefs = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        schoolid = prefs.getString("school_id","defaultValue");
        mList = rootView.findViewById(R.id.rvLog);

        logList = new ArrayList<>();
        adapter = new LogAdapter(getActivity(),logList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();

        return rootView ;
    }
    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Util.BASE_URL+Util.attendance_log +schoolid, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                        String date =  jsonObject.getString("attendance_date");
                        String present_male = jsonObject.getString("present_male");
                        String present_female = jsonObject.getString("present_female");
                        Log log = new Log(present_male,present_female,date);


                        logList.add(log);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }



}
