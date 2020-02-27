package com.momen.primaryschoolteachers_app.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.momen.primaryschoolteachers_app.Model.Data;
import com.momen.primaryschoolteachers_app.R;
import com.momen.primaryschoolteachers_app.api.SaveItem;
import com.momen.primaryschoolteachers_app.api.Util;
import com.goodiebag.pinview.Pinview;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import es.dmoral.toasty.Toasty;

public class AddInformationActivity extends AppCompatActivity {
    Pinview pvBoys1, pvGirls1, pvBoys2, pvGirls2, pvBoys3, pvGirls3, pvBoys4, pvGirls4, pvBoys5, pvGirls5;
    Button btnDetail;
    String schoolid;
    Gson gson;
    RelativeLayout relativeLayout;
    SharedPreferences prefs;
    List<Data> dataArray;
    ArrayList<HashMap<String, String>> cartArray;
    private String sharedPrefFile =
            "com.olivine.primaryschoolteachers_app";

    String boysone,boystwo,boysthree,boysfour,boysfive,girlsone,girlstwo,girlsThree,girlsFour,girlsFive;
    TextView tvSchoolName, textView2Id,tvMasterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);
        relativeLayout = findViewById(R.id.addInfoLayout);
        setupUI(relativeLayout);
        if (!haveNetwork()){
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "No Internet", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    });
            snackbar.setActionTextColor(Color.RED);

            snackbar.show();

        }
        tvSchoolName = findViewById(R.id.tvSchoolName);
        textView2Id = findViewById(R.id.textView2Id);
        tvMasterName = findViewById(R.id.tvMasterName);


        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Toolbar mToolbar = findViewById(R.id.tbHead);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_info);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        schoolid = prefs.getString("school_id", "defaultValue");
        tvSchoolName.setText(prefs.getString(SaveItem.school_bangla,""));
        tvMasterName.setText(prefs.getString(SaveItem.teacher_name,""));
        textView2Id.setText(prefs.getString(SaveItem.school_eiin,""));

        pvBoys1 = findViewById(R.id.pvBoys1);
        pvBoys2 = findViewById(R.id.pvBoys2);
        pvBoys3 = findViewById(R.id.pvBoys3);
        pvBoys4 = findViewById(R.id.pvBoys4);
        pvBoys5 = findViewById(R.id.pvBoys5);
        pvGirls1 = findViewById(R.id.pvGirls1);
        pvGirls2 = findViewById(R.id.pvGirls2);
        pvGirls3 = findViewById(R.id.pvGirls3);
        pvGirls4 = findViewById(R.id.pvGirls4);
        pvGirls5 = findViewById(R.id.pvGirls5);
        dataArray = new ArrayList<Data>();
        cartArray = new ArrayList<HashMap<String, String>>();

        gson = new Gson();


        btnDetail = findViewById(R.id.details);


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



    }

    private void validate() {
        if (boysone.isEmpty() || girlsone.isEmpty() || boysone.equals("0") || boysone.equals("00") || boysone.equals("000")
                || girlsone.equals("0") || girlsone.equals("00") || girlsone.equals("000") ) {

            Toasty.info(AddInformationActivity.this, "প্রথম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();

        }

        if (boystwo.isEmpty() || girlstwo.isEmpty() || boystwo.equals("0") || boystwo.equals("00") || boystwo.equals("000")
                || girlstwo.equals("0") || girlstwo.equals("00") || girlstwo.equals("000")) {

            Toasty.info(AddInformationActivity.this, "দ্বিতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();

        }

        if (boysthree.isEmpty() || girlsThree.isEmpty() || boysthree.equals("0") || boysthree.equals("00") || boysthree.equals("000")
                || girlsThree.equals("0") || girlsThree.equals("00") || girlsThree.equals("000")) {

            Toasty.info(AddInformationActivity.this, "তৃতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();

        }

        if (boysfour.isEmpty() || girlsFour.isEmpty() || boysfour.equals("0") || boysfour.equals("00") || boysfour.equals("000")
                || girlsFour.equals("0") || girlsFour.equals("00") || girlsFour.equals("000")) {

            Toasty.info(AddInformationActivity.this, "চতুর্থ শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();

        }

        if (boysfive.isEmpty() || girlsFive.isEmpty() || boysfive.equals("0") || boysfive.equals("00") || boysfive.equals("000")
                || girlsFive.equals("0") || girlsFive.equals("00") || girlsFive.equals("000")) {

            Toasty.info(AddInformationActivity.this, "পঞ্চম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();

        }
        if(boysone.length()>0 &&boystwo.length()>0 && boysthree.length()>0 &&boysfour.length()>0 &&boysfive.length()>0
                &&girlsone.length()>0 &&girlstwo.length()>0 &&girlsThree.length()>0 &&girlsFour.length()>0 &&girlsFive.length()>0)
        {
            new AlertDialog.Builder(this).

                    setTitle(R.string.confirm)
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


                    .setCancelable (false)

                    .setPositiveButton("নিশ্চিত", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            getValue();

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



    private void getValue() {

        // Get Value And Convert to Integer

        dataArray.add(new Data(schoolid, "1", boysone, girlsone));
        dataArray.add(new Data(schoolid, "2", boystwo, girlstwo));
        dataArray.add(new Data(schoolid, "3", boysthree, girlsThree));
        dataArray.add(new Data(schoolid, "4", boysfour, girlsFour));
        dataArray.add(new Data(schoolid, "5", boysfive, girlsFive));
        Log.d("dataarray:", String.valueOf(dataArray.size()));

        JSONObject dataObj = new JSONObject();
        try {


            JSONArray cartItemsArray = new JSONArray();
            JSONObject cartItemsObjedct;
            for (int i = 0; i < dataArray.size(); i++) {
                cartItemsObjedct = new JSONObject();
                cartItemsObjedct.putOpt("school_id", dataArray.get(i)
                        .getSchool_id());
                cartItemsObjedct.putOpt("class_id", dataArray.get(i).getClass_id());
                cartItemsObjedct.putOpt("total_male", dataArray.get(i).getTotal_male());
                cartItemsObjedct.putOpt("total_female", dataArray.get(i).getTotal_female());
                cartItemsArray.put(cartItemsObjedct);
            }

            dataObj.put("schoolinfo", cartItemsArray);



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
//Exception
        }

        String url= Util.BASE_URL+ Util.total_student_store;

        new AsyncHttpClient().post(null,url,entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {


                String res = new String(responseBody);
                Log.d("total_student_add", res);
                if(res.equals("true"))
                {
                    prefs.edit().putString("total_student",res) .apply();
                    Intent main = new Intent(AddInformationActivity.this,MainActivity.class);
                    startActivity(main);
                    finish();

                }
                else{

                    Toast toast = new Toast(AddInformationActivity.this);
                    toast.makeText(AddInformationActivity.this,"পূর্বেই দেয়া হয়েছে",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    Intent main = new Intent(AddInformationActivity.this,MainActivity.class);
                    startActivity(main);
                    finish();

                }



            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof Pinview)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AddInformationActivity.this);
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
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }







}
