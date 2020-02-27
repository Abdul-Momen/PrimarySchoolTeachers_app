package com.momen.primaryschoolteachers_app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.momen.primaryschoolteachers_app.Activity.AddInformationActivity;
import com.momen.primaryschoolteachers_app.Activity.LoginActivity;
import com.momen.primaryschoolteachers_app.R;
import com.momen.primaryschoolteachers_app.api.SaveItem;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    String headMasterName,schoolname,eiinno, phoneno, emailid, totalstudent;
    Button addinfo;
    TextView tvprofileName,tvdesignation,tvschoolname,tveiin,tvphone,tvemail;
    SharedPreferences prefs;
    private String sharedPrefFile =
            "com.olivine.primaryschoolteachers_app";
    ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_profile_fargment, container, false);
        prefs = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
// then you use
        headMasterName = prefs.getString(SaveItem.teacher_name,"defaultValue");
        schoolname = prefs.getString(SaveItem.school_bangla,"defaultValue");
        eiinno = prefs.getString(SaveItem.school_eiin,"defaultValue");
        phoneno = prefs.getString(SaveItem.teacher_mob,"defaultValue");
        totalstudent = prefs.getString("total_student","defaultvalue");

        //Toast.makeText(getContext(),totalstudent,Toast.LENGTH_LONG).show();
        tvprofileName = rootView.findViewById(R.id.profileNameId);
        tvprofileName.setText(headMasterName);
        tvschoolname = rootView.findViewById(R.id.tvSchoolName);
        tvschoolname.setText(schoolname);
        tveiin = rootView.findViewById(R.id.eiinno);
        tveiin.setText(eiinno.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
        tvphone = rootView.findViewById(R.id.phnNoId);
        tvphone.setText(phoneno.replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯"));
        tvemail = rootView.findViewById(R.id.emailId);
        logout = rootView.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("LOGOUT", true);
                startActivity(intent);
                getActivity().finish();


            }
        });


        addinfo = rootView.findViewById(R.id.addinfo);
        if(totalstudent.equals("true"))
        {
            addinfo.setVisibility(View.GONE);
        }
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(getContext(), AddInformationActivity.class);
                startActivity(info);
            }
        });
        return rootView ;

    }

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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    }
