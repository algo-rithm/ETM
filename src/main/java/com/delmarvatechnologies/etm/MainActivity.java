package com.delmarvatechnologies.etm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.delmarvatechnologies.etm.singletons.NetworkRequestQueue;
import com.delmarvatechnologies.etm.ui.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final int SIGNIN_REQ_CODE = 888;
    public static final String PREF_AUTH_TOKEN = "pref_token";
    public static final String PREF_USER_NAME = "pref_user_name";
    public static final String PREF_USER_EMAIL = "pref_user_email";

    private static final String STUDENTBANK_DETAIL_FRAGMENT = "sb_detail_frag";

    private boolean isTwoPane;
    private String TOKEN;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_main);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Check to load DETAIL view
//        if (findViewById(R.id.student_bank_detail_container) != null) {
//            isTwoPane = true;
//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.student_bank_detail_container, new StudentBankDetailFragment(), STUDENTBANK_DETAIL_FRAGMENT).commit();
//            }
//        } else {
//            isTwoPane = false;
//        }


//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//        if (firebaseUser == null) {
//            startActivity(new Intent(this, SignInActivity.class));
//        }

        TOKEN = prefs.getString(PREF_AUTH_TOKEN, "");
        if (TOKEN == null || TOKEN.isEmpty()) {
            Intent loginIntent = new Intent(this, SignInActivity.class);
            startActivityForResult(loginIntent, SIGNIN_REQ_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, i
            nt resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGNIN_REQ_CODE) {
            if (resultCode == RESULT_OK) {

            }
        }
    }
}
