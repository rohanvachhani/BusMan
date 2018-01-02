package com.rohan.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends AppCompatActivity {

    private EditText e_email, e_pass;
    private Button b_sign_in, b_sign_up, b_forget_pass;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private ArrayList<String> r_users;

    //for differnt font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        //define the list of the users who can register...!
        r_users = new ArrayList<>();
        r_users.add("rohan.vachhani5@gmail.com");
        r_users.add("dobariyagaurang@gmail.com");
        r_users.add("piyushthummar772@gmail.com");
        r_users.add("iamnikunjvaghasiya@gmail.com");
        r_users.add("rakholiyasaurav531@gmail.com");
        r_users.add("jaiminvaghasiya123@gmail.com");
        r_users.add("dtejas8980@gmail.com");

        b_sign_in = findViewById(R.id.btn_sign_in);
        b_sign_up = findViewById(R.id.btn_sign_up);
        e_email = findViewById(R.id.email);
        e_pass = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        b_forget_pass = findViewById(R.id.btn_reset_password);

        b_sign_in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();

            }
        });

        b_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //first check if user is allow to register or not????
                if (!(r_users.contains(e_email.getText().toString().trim()))) {
                    Toast.makeText(getApplicationContext(), "Not allowed...", Toast.LENGTH_SHORT).show();
                    return;
                }

                String mail_id = e_email.getText().toString().trim();
                String passwd = e_pass.getText().toString().trim();

                if (TextUtils.isEmpty(mail_id)) {
                    Toast.makeText(getApplicationContext(), "Please, Enter your email address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwd)) {
                    Toast.makeText(getApplicationContext(), "Please, Enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwd.length() < 6) {
                    Toast.makeText(getApplicationContext(), "password length must graer than 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //to hide keyboard
                InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                EditText editText = (EditText) findViewById(R.id.password);
                inputMgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                //end code of hide key board

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(mail_id, passwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

    }

    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
