package com.momen.primaryschoolteachers_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.momen.primaryschoolteachers_app.R;
import com.momen.primaryschoolteachers_app.api.SaveItem;
import com.momen.primaryschoolteachers_app.api.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private Button button;
    String school_id,school_name_bn,school_eiin_no,school_teacher_name,school_teacher_mobile,school_upazila_id;
    private static final String TAG = "App";
    EditText etLoginUser , etLoginPassword ;
    String mobile_no,password;
    SharedPreferences prefs;
    private String sharedPrefFile =
            "com.olivine.primaryschoolteachers_app";
    RelativeLayout loginLayout;
    private static final String IS_LOGIN = "IsLoggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        school_id = prefs.getString("school_id","");
       /* if(!school_id.isEmpty())
        {
            checkTotalStudent(school_id);
            Intent mainpage = new Intent(this, MainActivity.class);
            startActivity(mainpage);
            finish();

        }*/
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            checkTotalStudent(school_id);
            Intent mainpage = new Intent(this, MainActivity.class);
            startActivity(mainpage);
            finish();
        }

        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button_login);
        etLoginUser = findViewById(R.id.etLoginUser);
        etLoginPassword = findViewById(R.id.etLoginPass);
        loginLayout = findViewById(R.id.loginLayout);
        setupUI(loginLayout);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean done = false;
                mobile_no = etLoginUser.getText().toString();
                password = etLoginPassword.getText().toString();
                if(mobile_no.isEmpty())
                {
                    etLoginUser.setError("Please fill up");
                    done = true;

                }
                if(password.isEmpty())
                {
                    etLoginPassword.setError("Please fill up");
                    done = true;
                }
                if(mobile_no.length()<11)
                {
                    etLoginUser.setError("Invalid mobile number");
                    done = true;
                }

                if(!done)
                {
                    login();
                }

            }
        });
    }

    private void login() {
        //button.setMode(ActionProcessButton.Mode.PROGRESS);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.BASE_URL+Util.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Invalid Credentials"))
                        {
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            //button.setProgress(100);
                        }
                        else{



                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            school_id = jsonObject.getString(SaveItem.school);
                            school_name_bn = jsonObject.getString(SaveItem.school_bangla);
                            school_eiin_no = jsonObject.getString(SaveItem.school_eiin);
                            school_teacher_name = jsonObject.getString(SaveItem.teacher_name);
                            school_teacher_mobile = jsonObject.getString(SaveItem.teacher_mob);
                            //button.setProgress(75);





                            checkTotalStudent(school_id);
                            //prefs.edit().putString("total_student", "").apply();

                            //Toast.makeText(LoginActivity.this,school_id,Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }

                        Log.d(TAG, "res: " + response);
                        //



                       /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("mobile_no",mobile_no);
                params.put("password",password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void checkTotalStudent(String schoolid) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.BASE_URL+Util.total_student_check+schoolid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        prefs.edit().putString(SaveItem.total,response) .apply();
                        //button.setProgress(100);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            // Apply activity transition
                            getWindow().setExitTransition(new Explode());
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                            finish();
                        } else {
                            // Swap without transition
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
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

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public boolean isLoggedIn(){
        return prefs.getBoolean(IS_LOGIN, false);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor sharedprefedit  = prefs.edit();
        sharedprefedit.putString(SaveItem.school,school_id);
        sharedprefedit.putString(SaveItem.school_bangla,school_name_bn);
        sharedprefedit.putString(SaveItem.school_eiin, school_eiin_no);
        sharedprefedit.putString(SaveItem.teacher_name, school_teacher_name);
        sharedprefedit.putString(SaveItem.teacher_mob, school_teacher_mobile);
        sharedprefedit.putBoolean(IS_LOGIN, true);
        sharedprefedit.apply();
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
