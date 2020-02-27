package com.momen.primaryschoolteachers_app.Fragment;

import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.momen.primaryschoolteachers_app.Activity.MainActivity;
import com.momen.primaryschoolteachers_app.Model.Data;
import com.momen.primaryschoolteachers_app.R;
import com.momen.primaryschoolteachers_app.api.SaveItem;
import com.momen.primaryschoolteachers_app.api.Util;
import com.goodiebag.pinview.Pinview;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.roger.catloadinglibrary.CatLoadingView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    FragmentActivity homeFragment;
    CatLoadingView mView;
    Util util = new Util();
    String headMasterName , Boys , Girls , schoolName, schooleiin;
    TextView total_male_one, total_male_two,total_male_three, total_male_four, total_male_five, total_female_one,
            total_female_two, total_female_three, total_female_four, total_female_five;

    Pinview pvBoys1,pvGirls1 , pvBoys2 , pvGirls2 , pvBoys3 , pvGirls3 , pvBoys4 , pvGirls4 , pvBoys5 , pvGirls5 ;

    int totalBoys , totalGirls ;

    int boys1 , boys2 , boys3 , boys4 , boys5 , girls1 , girls2, girls3 , girls4 , girls5 ;
    List<Data> dataArray;

    int absentBoys, absentGirls;
    String presentboys, presentgirls;

    Button btnDetail;
    SharedPreferences prefs;
    private String sharedPrefFile =
            "com.olivine.primaryschoolteachers_app";
    String schoolid;
    String boysone,boystwo,boysthree,boysfour,boysfive,girlsone,girlstwo,girlsThree,girlsFour,girlsFive;
    int one_boy, one_girl,two_boy,two_girl,three_boy,three_girl,four_boy,four_girl,five_boy,five_girl;

    private TextView tvTodayDate , tvSchoolName, tvEiinNo;
    private RequestQueue mqueue , sendQueue;
    String  attendance_date,today_date;
    public android.app.AlertDialog dialog;
    SwipeRefreshLayout pullToRefresh;
    RelativeLayout homelayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount() == 0) getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
      pullToRefresh = rootView.findViewById(R.id.pullToRefresh);
        homelayout = rootView.findViewById(R.id.homelayout);
        //setupUI(homelayout);


        prefs = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

// then you use
        headMasterName = prefs.getString("school_teacher_name","defaultValue");
        schoolName = prefs.getString("school_name_bn","defaultValue");
        schoolid = prefs.getString("school_id","defaultValue");
        schooleiin = prefs.getString("school_eiin_no","defaultValue");


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        today_date = simpleDateFormat.format(new Date()).replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯");

        tvTodayDate = rootView.findViewById(R.id.tvtoday_date);
        tvTodayDate.setText(today_date);

        //tvHeadMasterName.setText(headMasterName);
        tvSchoolName = rootView.findViewById(R.id.tvSchoolName);
        tvSchoolName.setText(schoolName);
        tvEiinNo = rootView.findViewById(R.id.eiinno);
        tvEiinNo.setText(schooleiin);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        attendance_date = simpleDateFormat.format(new Date());




        pvBoys1 = rootView.findViewById(R.id.pvBoys1);
        pvBoys2 = rootView.findViewById(R.id.pvBoys2);
        pvBoys3 = rootView.findViewById(R.id.pvBoys3);
        pvBoys4 = rootView.findViewById(R.id.pvBoys4);
        pvBoys5 = rootView.findViewById(R.id.pvBoys5);
        pvGirls1 = rootView.findViewById(R.id.pvGirls1);
        pvGirls2 = rootView.findViewById(R.id.pvGirls2);
        pvGirls3 = rootView.findViewById(R.id.pvGirls3);
        pvGirls4 = rootView.findViewById(R.id.pvGirls4);
        pvGirls5 = rootView.findViewById(R.id.pvGirls5);
        btnDetail = rootView.findViewById(R.id.details);
        total_male_one = rootView.findViewById(R.id.textView6_Id);
        total_female_one = rootView.findViewById(R.id.textView7Id);

        total_male_two = rootView.findViewById(R.id.textView11Id);
        total_female_two = rootView.findViewById(R.id.textView12Id);

        total_male_three =rootView.findViewById(R.id.textView16Id);
        total_female_three = rootView.findViewById(R.id.tv3rdId);

        total_male_four = rootView.findViewById(R.id.textView20Id);
        total_female_four = rootView.findViewById(R.id.textView21Id);

        total_male_five = rootView.findViewById(R.id.textView24Id);
        total_female_five = rootView.findViewById(R.id.textView25Id);

        showClassWiseTotal();

        dataArray=new ArrayList<Data>();


        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boysone = pvBoys1.getValue();
                boystwo = pvBoys2.getValue();
                boysthree = pvBoys3.getValue();
                boysfour = pvBoys4.getValue();
                boysfive = pvBoys5.getValue();
                girlsone = pvGirls1.getValue();
                girlstwo = pvGirls2.getValue();
                girlsThree = pvGirls3.getValue();
                girlsFour = pvGirls4.getValue();
                girlsFive = pvGirls5.getValue();
                validate();

            }
        });
        //mView = new CatLoadingView();
        /*pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Fragment currentFragment = getFragmentManager().findFragmentByTag("HomeFragment");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();

            }
        });*/






        return rootView ;
    }



    private void showClassWiseTotal() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Util.BASE_URL+Util.total_student_info+schoolid, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String classId =  jsonObject.getString("ts_class_id");
                        String totalBoy = jsonObject.getString("total_boys");
                        String totalGirl = jsonObject.getString("total_girls");

                        if (classId.equals("1"))
                        {


                            total_male_one.setText(totalBoy.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            total_female_one.setText(totalGirl.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            prefs.edit().putString(SaveItem.one_boys,totalBoy).apply();
                            prefs.edit().putString(SaveItem.one_girls,totalGirl).apply();
                        }

                        if (classId.equals("2"))
                        {

                            total_male_two.setText(totalBoy.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            total_female_two.setText(totalGirl.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            prefs.edit().putString(SaveItem.two_boys,totalBoy).apply();
                            prefs.edit().putString(SaveItem.two_girls,totalGirl).apply();
                        }

                        if(classId.equals("3"))
                        {

                            total_male_three.setText(totalBoy.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            total_female_three.setText(totalGirl.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            prefs.edit().putString(SaveItem.three_boys,totalBoy).apply();
                            prefs.edit().putString(SaveItem.three_girls,totalGirl).apply();
                        }

                        if(classId.equals("4"))
                        {

                            total_male_four.setText(totalBoy.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            total_female_four.setText(totalGirl.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            prefs.edit().putString(SaveItem.four_boys,totalBoy).apply();
                            prefs.edit().putString(SaveItem.four_girls,totalGirl).apply();
                        }

                        if(classId.equals("5"))
                        {

                            total_male_five.setText(totalBoy.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            total_female_five.setText(totalGirl.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
                            prefs.edit().putString(SaveItem.five_boys,totalBoy).apply();
                            prefs.edit().putString(SaveItem.five_girls,totalGirl).apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Volley", error.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }


    private void validate() {
         Boolean done = false;


        if (boysone.isEmpty() || girlsone.isEmpty() /*|| boysone.equals("0") || boysone.equals("00") || boysone.equals("000")
                || girlsone.equals("0") || girlsone.equals("00") || girlsone.equals("000")*/) {

            Toasty.info(getContext(), "প্রথম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
            done = true;

        }

        if (boystwo.isEmpty() || girlstwo.isEmpty() /*|| boystwo.equals("0") || boystwo.equals("00") || boystwo.equals("000")
                || girlstwo.equals("0") || girlstwo.equals("00") || girlstwo.equals("000")*/) {

            Toasty.info(getContext(), "দ্বিতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
            done = true;

        }

        if (boysthree.isEmpty() || girlsThree.isEmpty() /*|| boysthree.equals("0") || boysthree.equals("00") || boysthree.equals("000")
                || girlsThree.equals("0") || girlsThree.equals("00") || girlsThree.equals("000")*/) {


            Toasty.info(getContext(), "তৃতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
            done = true;

        }

        if (boysfour.isEmpty() || girlsFour.isEmpty() /*|| boysfour.equals("0") || boysfour.equals("00") || boysfour.equals("000")
                || girlsFour.equals("0") || girlsFour.equals("00") || girlsFour.equals("000")*/) {

            Toasty.info(getContext(), "চতুর্থ শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
            done = true;

        }

        if (boysfive.isEmpty() || girlsFive.isEmpty() /*|| boysfive.equals("0") || boysfive.equals("00") || boysfive.equals("000")
                || girlsFive.equals("0") || girlsFive.equals("00") || girlsFive.equals("000")*/) {

            Toasty.info(getContext(), "পঞ্চম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
            done = true;

        }
        if (boysone.isEmpty() && girlsone.isEmpty() && boysfive.isEmpty() && girlsFive.isEmpty() && boystwo.isEmpty() && girlstwo.isEmpty()
                && boysthree.isEmpty() && girlsThree.isEmpty() && boysfour.isEmpty() && girlsFour.isEmpty()) {

            Toasty.info(getContext(), "ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
            done = true;

        }
        if (boysone.length() > 0 && boystwo.length() > 0 && boysthree.length() > 0 && boysfour.length() > 0 && boysfive.length() > 0
                && girlsone.length() > 0 && girlstwo.length() > 0 && girlsThree.length() > 0 && girlsFour.length() > 0 && girlsFive.length() > 0)
        {
            one_boy = Integer.parseInt(prefs.getString(SaveItem.one_boys, ""));
            one_girl = Integer.parseInt(prefs.getString(SaveItem.one_girls, ""));

            if (Integer.parseInt(boysone) > one_boy || Integer.parseInt(girlsone) > one_girl) {


                Toasty.info(getContext(), "প্রথম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা মোট সংখ্যার অধিক", Toast.LENGTH_LONG).show();
                done = true;

            }
            two_boy = Integer.parseInt(prefs.getString(SaveItem.two_boys, ""));
            two_girl = Integer.parseInt(prefs.getString(SaveItem.two_girls, ""));
            if (Integer.parseInt(boystwo) > two_boy || Integer.parseInt(girlstwo) > two_girl) {
                Toasty.info(getContext(), "দ্বিতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা মোট সংখ্যার অধিক", Toast.LENGTH_LONG).show();
                done = true;

            }
            three_boy = Integer.parseInt(prefs.getString(SaveItem.three_boys, ""));
            three_girl = Integer.parseInt(prefs.getString(SaveItem.three_girls, ""));
            if (Integer.parseInt(boysthree) > three_boy || Integer.parseInt(girlsThree) > three_girl) {

                Toasty.info(getContext(), "তৃতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা মোট সংখ্যার অধিক", Toast.LENGTH_LONG).show();
                done = true;
            }
            four_boy = Integer.parseInt(prefs.getString(SaveItem.four_boys, ""));
            four_girl = Integer.parseInt(prefs.getString(SaveItem.four_girls, ""));
            if (Integer.parseInt(boysfour) > four_boy || Integer.parseInt(girlsFour) > four_girl) {
                Toasty.info(getContext(), "চতুর্থ শ্রেণীর ছাত্র-ছাত্রী সংখ্যা মোট সংখ্যার অধিক", Toast.LENGTH_LONG).show();
                done = true;

            }
            five_boy = Integer.parseInt(prefs.getString(SaveItem.five_boys, ""));
            five_girl = Integer.parseInt(prefs.getString(SaveItem.five_girls, ""));
            if (Integer.parseInt(boysfive) > five_boy || Integer.parseInt(girlsFive) > five_girl) {
                Toasty.info(getContext(), "পঞ্চম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা মোট সংখ্যার অধিক", Toast.LENGTH_LONG).show();
                done = true;

            }

        }
        if(!done)
        {


            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setTitle(R.string.confirm)
                    .setMessage("প্রথম শ্রেণী:\t"
                            + "ছেলে:\t" + pvBoys1.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                            + "মেয়ে:\t" + pvGirls1.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                            "দ্বিতীয় শ্রেণী:\t"
                            + "ছেলে:\t" + pvBoys2.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                            + "মেয়ে:\t" + pvGirls2.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                            "তৃতীয় শ্রেণী:\t"
                            + "ছেলে:\t" + pvBoys3.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                            + "মেয়ে:\t" + pvGirls3.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                            "চতুর্থ শ্রেণী:\t"
                            + "ছেলে:\t" + pvBoys4.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                            + "মেয়ে:\t" + pvGirls4.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                            "পঞ্চম শ্রেণী:\t"
                            + "ছেলে:\t" + pvBoys5.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                            + "মেয়ে:\t" + pvGirls5.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n")


                    .setPositiveButton("নিশ্চিত", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            //Toast.makeText(getContext(),attendance_date,Toast.LENGTH_LONG).show();
                            submitvalidation();

                            dialog.dismiss();
                        }
                    }).
                    setNegativeButton("না", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    }).create().show();

        }

    }

    private void submitvalidation()

    {
        //mView.show(homeFragment.getSupportFragmentManager(), "Validating...");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.BASE_URL+Util.attendance_validation+schoolid+"/"+attendance_date,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        if(response.equals("true"))
                        {
                            //mView.dismiss();
                            Toasty.warning(getContext(),"উপস্থিতি পূর্বেই দেয়া হয়েছে",Toast.LENGTH_LONG).show();
                        }
                        else{
                            todayattendance();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }


    private void todayattendance() {
        dataArray.add(new Data(schoolid,"1",boysone,girlsone));
        dataArray.add(new Data(schoolid,"2",boystwo,girlstwo));
        dataArray.add(new Data(schoolid,"3",boysthree,girlsThree));
        dataArray.add(new Data(schoolid,"4",boysfour,girlsFour));
        dataArray.add(new Data(schoolid,"5",boysfive,girlsFive));

        JSONObject dataObj = new JSONObject();
        try {


            JSONArray cartItemsArray = new JSONArray();
            JSONObject cartItemsObjedct;
            for (int i = 0; i < dataArray.size(); i++) {
                cartItemsObjedct = new JSONObject();
                cartItemsObjedct.putOpt("school_id", dataArray.get(i)
                        .getSchool_id());
                cartItemsObjedct.putOpt("class_id", dataArray.get(i).getClass_id());
                cartItemsObjedct.putOpt("present_male", dataArray.get(i).getTotal_male());
                cartItemsObjedct.putOpt("present_female", dataArray.get(i).getTotal_female());
                cartItemsArray.put(cartItemsObjedct);
            }

            dataObj.put("attendanceinfo", cartItemsArray);
        } catch (JSONException e) {
            e.printStackTrace();

        }

        String yourData = dataObj.toString();
        Log.d("data",yourData);
        StringEntity entity = null;
        try {
            entity = new StringEntity(yourData);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch(Exception e) {

        }

        String url=Util.BASE_URL+Util.attendance_store;

        new AsyncHttpClient().post(null,url,entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
                if(res.equals("true"))
                {


                    Toast.makeText(getContext(), "উপস্থিতি দেওয়া হয়েছে", Toast.LENGTH_SHORT).show();
                    mView.dismiss();
                    Intent mainAct = new Intent(getContext(), MainActivity.class);
                    startActivity(mainAct);
                    getActivity().finish();

                }
                else{
                    Toast.makeText(getContext(), "যান্ত্রিক গোলযোগ", Toast.LENGTH_SHORT).show();
                    Intent mainAct = new Intent(getContext(), MainActivity.class);
                    startActivity(mainAct);
                    getActivity().finish();

                }

                /*try {
                    String res = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(object);

                    Log.d("attendacne", jsonObject.toString());
                } catch (JSONException e) {
                    //Toast.makeText(AddInformationActivity.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    /*public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof Pinview)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        *//*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         *//*
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }*/








    // Method for sending TotalBoys and Girls Quantity




}
