package com.shivenderkumar.demologinsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    MaterialButton btn_login, btn_signup;
    TextInputEditText editText_email, editText_pswd;
    ProgressBar progressbar;

    public static FirebaseAuth firebaseAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String email = user.getEmail();
            System.out.println("SSSSSSSS MAIN ACTIVITY USER EMAIL : "+email);

            //User is Logged in
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {

            btn_login = findViewById(R.id.btn_login);
            btn_signup = findViewById(R.id.btn_signup);
            editText_email = findViewById(R.id.edittext_email);
            editText_pswd = findViewById(R.id.edittext_pswd);
            progressbar = findViewById(R.id.progressbar);

            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (haveNetworkConnection()) {
                        signup();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No Network Connection",
                                Toast.LENGTH_LONG)
                                .show();

                    }

                }
            });

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (haveNetworkConnection()) {
                        login();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No Network Connection",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private void signup() {

        String email = editText_email.getText().toString();
        String password = editText_pswd.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (isValid(email) == false) {
            Toast.makeText(getApplicationContext(),
                    "Invalid email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        //sign up
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Registration successful!",
                            Toast.LENGTH_LONG)
                            .show();

                    // hide the progress bar
                    progressbar.setVisibility(View.GONE);

                    // if the user created intent to login activity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    // Registration failed
                    Toast.makeText(
                            getApplicationContext(),
                            task.getException().getMessage().toString(),
                            Toast.LENGTH_LONG)
                            .show();

                    // hide the progress bar
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
        //

    }

    private void login() {

        // Take the value of two edit texts in Strings
        String email, password;
        email = editText_email.getText().toString();
        password = editText_pswd.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (isValid(email) == false) {
            Toast.makeText(getApplicationContext(),
                    "Invalid email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // signin existing user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(MainActivity.this,
                                            HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            task.getException().getMessage().toString(),
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);
                                }
                            }
                        });
    }

}
