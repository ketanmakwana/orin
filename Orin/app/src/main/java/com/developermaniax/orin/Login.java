package com.developermaniax.orin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    Button btnLogin;


    EditText edtUserName;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(view)){
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean isEmpty(View view) {
        if(edtUserName.getText().toString().isEmpty() && edtUserName.getText().toString() != "" || edtPassword.getText().toString().isEmpty()
                && edtPassword.getText().toString() != ""){
            if(edtUserName.getText().toString().isEmpty() && edtUserName.getText().toString() != ""){
                edtUserName.setError("Please enter user name");
                Snackbar.make(view, "Please enter user name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if(edtPassword.getText().toString().isEmpty()
                    && edtPassword.getText().toString() != ""){
                edtPassword.setError("Please enter password");
                Snackbar.make(view, "Please enter passwordl", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return false;
        }else{
            return true;

        }
    }

    public void DoLogin(View v) {
//        Intent intent = new Intent(Login.this,MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    public void DoSignUP(View v) {
        Intent intent = new Intent(Login.this, SignUP.class);
        startActivity(intent);
    }

}
