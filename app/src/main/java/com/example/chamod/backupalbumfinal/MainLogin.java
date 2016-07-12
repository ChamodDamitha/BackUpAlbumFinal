package com.example.chamod.backupalbumfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Handlers.AccountHandler;


public class MainLogin extends ActionBarActivity {

    private AccountHandler accountHandler;
    private TextView loggedUserTxt;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        accountHandler=AccountHandler.getInstance();
        accountHandler.setMainLogin(this);


        Button btnCreateAccount=(Button)findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //switching activity //
                        Intent newAccountIntent = new Intent(MainLogin.this,NewAccountActivity.class);
                        startActivity(newAccountIntent);
                    }
                }
        );

        final EditText txtUsername=(EditText)findViewById(R.id.txtUserName);
        final EditText txtPassword=(EditText)findViewById(R.id.txtPassword);
        Button btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isLoginSuccess;
                        if(txtUsername.getText().length()==0 || txtPassword.getText().length()==0)
                            isLoginSuccess=false;
                        else
                            isLoginSuccess= accountHandler.login(txtUsername.getText().toString(),txtPassword.getText().toString());
                        if(isLoginSuccess)
                        {
                            Intent newAccountIntent = new Intent(MainLogin.this,LoggedAccountActivity.class);
                            startActivity(newAccountIntent);
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainLogin.this);
                            builder.setMessage("Login Failed...!");
                            builder.setTitle("Alert");
                            builder.create();
                            builder.show();
                        }
                    }
                }
        );

        //check for last logged user
        if(accountHandler.checkForLoggedUser())
        {
            loggedUserTxt=(TextView)findViewById(R.id.lblLoggedUsername);
            loggedUserTxt.setText(accountHandler.getLoggedUser().getUserName());

            btnContinue=(Button)findViewById(R.id.btnContinue);
            btnContinue.setEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_logOut:
                accountHandler.logOut();
                startActivity(new Intent(this,MainLogin.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void continueLogin(View view)
    {
        Intent newAccountIntent = new Intent(MainLogin.this,LoggedAccountActivity.class);
        startActivity(newAccountIntent);
    }
}
