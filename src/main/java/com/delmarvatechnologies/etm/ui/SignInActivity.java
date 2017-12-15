package com.delmarvatechnologies.etm.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.delmarvatechnologies.etm.MainActivity;
import com.delmarvatechnologies.etm.R;
import com.delmarvatechnologies.etm.singletons.NetworkRequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rithm on 11/11/2017.
 */

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final String URL_LOGIN = "https://app.educationalclassroomsystems.com/api/v1/login";

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private EditText mEcsEmail;
    private EditText mEcsPassword;
    private FirebaseAuth mFirebaseAuth;
    private String mCustomToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();
        setContentView(R.layout.signin_layout);

        //mFirebaseAuth = FirebaseAuth.getInstance();

        mEcsEmail = (EditText) findViewById(R.id.email);
        mEcsPassword = (EditText) findViewById(R.id.password);
        Button sign_in_bttn = (Button) findViewById(R.id.btnLogin);
        sign_in_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToken();
            }
        });
    }

    private void getToken() {
        //TESTING CODE
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authorizing..");
        progressDialog.show();

        StringRequest requestToken = new StringRequest(
                Request.Method.POST,
                URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String token = jsonResponse.getString("access_token");

                            jsonResponse = jsonResponse.getJSONObject("user");
                            String name = jsonResponse.getString("name");
                            String email = jsonResponse.getString("email");

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Welcome " + name + "!",
                                    Toast.LENGTH_SHORT).show();

                            //Log.d(TAG, "------------> \n" + token);
                            //mCustomToken = token;
                            //startSignIn(token);
                            prefsEditor.putString(MainActivity.PREF_USER_NAME, name);
                            prefsEditor.putString(MainActivity.PREF_USER_EMAIL, email);
                            prefsEditor.putString(MainActivity.PREF_AUTH_TOKEN, token);
                            prefsEditor.commit();

                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Password or Email is incorrect.",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

                    protected Map<String,String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("email", mEcsEmail.getText().toString());
                        params.put("password", mEcsPassword.getText().toString());
                        return params;
                    }
        };
        NetworkRequestQueue.getInstance().addToRequestQueue(requestToken);
    }

    private void startSignIn(String token) {
        mFirebaseAuth.signInWithCustomToken(token)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "sinInWithCustomToken:success");
                            //FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        }
                    }
                });
    }
}
