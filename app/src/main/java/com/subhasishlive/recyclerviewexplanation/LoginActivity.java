package com.subhasishlive.recyclerviewexplanation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    // get the shared instance of the FirebaseAuth object:
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        // mAuth.getCurrentUser checks if the user is already logged in...
        // If so, then the chat activity opens directly...
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainChatActivity.class));
//            finish();
//        }
        // otherwise open login activity
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();
    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.subhasishlive.recyclerviewexplanation.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return; // return false and get out
        Toast.makeText(this,"Login in progress !!!", Toast.LENGTH_SHORT).show();
        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){ // if the task is not successful,
                            if (password.length() < 5) {
                                mPasswordView.setError(getString(R.string.minimum_password));
                            } else {
                                showErrorDialog("Registration attempt failed !!!");
                            }
                        }else{
                            Intent intent = new Intent(LoginActivity.this, RollDiceActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });





    }

    // TODO: Show error on screen with an alert dialog
    private void showErrorDialog(String message){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        new AlertDialog.Builder(this).setMessage(R.string.auth_failed)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok,null).show();
    }


}